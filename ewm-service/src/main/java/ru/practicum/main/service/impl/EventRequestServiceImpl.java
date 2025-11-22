package ru.practicum.main.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.main.common.State;
import ru.practicum.main.common.Status;
import ru.practicum.main.entity.EventEntity;
import ru.practicum.main.entity.EventRequestEntity;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.repository.EventRequestRepository;
import ru.practicum.main.service.EventRequestService;
import ru.practicum.main.service.EventService;
import ru.practicum.main.service.UserService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EventRequestServiceImpl implements EventRequestService {
    private final EventRequestRepository repository;
    private final UserService userService;
    private final EventService eventService;

    @Override
    public List<EventRequestEntity> findAllByUserId(Long userId) {
        return (List<EventRequestEntity>) repository.findAllByRequesterId(userId);
    }

    @Override
    public EventRequestEntity create(Long userId, Long eventId) {
        EventEntity event = eventService.getById(eventId);

        validateCreateRequest(userId, event);

        boolean autoConfirm = shouldAutoConfirm(event);

        EventRequestEntity request = buildNewRequest(userId, event, autoConfirm);
        EventRequestEntity saved = repository.save(request);

        if (autoConfirm && hasParticipantLimit(event)) {
            incrementConfirmedRequests(event.getId());
        }

        return saved;
    }

    @Override
    public EventRequestEntity cancel(Long userId, Long requestId) {
        EventRequestEntity entity = findByUserIdAndRequestId(userId, requestId);
        entity.setStatus(Status.CANCELED);
        return repository.save(entity);
    }

    @Override
    public EventRequestEntity findByUserIdAndRequestId(Long userId, Long requestId) {
        return repository.findByRequester_IdAndId(userId, requestId)
            .orElseThrow(() -> new NotFoundException(
                "Запрос с userId=" + userId + " и requestId=" + requestId + " не найдено"));
    }

    @Override
    public List<EventRequestEntity> findByInitiatorIdAndEventId(Long userId, Long eventId) {
        return (List<EventRequestEntity>) repository.findByInitiatorIdAndEventId(userId, eventId);
    }

    @Override
    public List<EventRequestEntity> findAllByEventIdAndStatus(Long eventId, Status status) {
        return (List<EventRequestEntity>) repository.findAllByEventIdAndStatus(eventId, status);
    }

    @Override
    public void responseToRequests(List<Long> requestIds,
                                   Status status,
                                   Long eventId,
                                   Long initiatorId) {

        validateTargetStatus(status);

        EventEntity event = eventService.getById(eventId);

        if (!requiresModeration(event)) {
            repository.responseToRequests(requestIds, status, eventId, initiatorId);
            return;
        }

        List<EventRequestEntity> requests = repository.findAllById(requestIds);
        validateRequestsForBulkUpdate(requests, eventId, initiatorId);

        if (status == Status.CONFIRMED) {
            handleConfirmRequests(event, requests, requestIds, initiatorId);
        } else {
            handleRejectRequests(requestIds, eventId, initiatorId);
        }
    }

    private void validateCreateRequest(Long userId, EventEntity event) {
        validateDuplicatedRequest(userId, event.getId());
        validateInitiatorTryToRequest(userId, event.getInitiatorId());
        validateEventIsPublished(event);
        validateFreePlaces(event);
    }

    private EventRequestEntity buildNewRequest(Long userId, EventEntity event, boolean autoConfirm) {
        EventRequestEntity.EventRequestEntityBuilder builder = EventRequestEntity.builder()
            .requester(userService.getById(userId))
            .event(event);

        if (autoConfirm) {
            builder.status(Status.CONFIRMED);
        }

        return builder.build();
    }

    private boolean shouldAutoConfirm(EventEntity event) {
        return !event.getRequestModeration()
            || Objects.equals(event.getParticipantLimit(), 0L);
    }

    private boolean hasParticipantLimit(EventEntity event) {
        return !Objects.equals(event.getParticipantLimit(), 0L);
    }

    private void validateTargetStatus(Status status) {
        if (status != Status.CONFIRMED && status != Status.REJECTED) {
            throw new ConflictException("Можно изменить статус только на CONFIRMED или REJECTED");
        }
    }

    private boolean requiresModeration(EventEntity event) {
        return hasParticipantLimit(event) && event.getRequestModeration();
    }

    private void validateRequestsForBulkUpdate(List<EventRequestEntity> requests,
                                               Long eventId,
                                               Long initiatorId) {
        for (EventRequestEntity r : requests) {
            if (!Objects.equals(r.getEvent().getId(), eventId)
                || !Objects.equals(r.getEvent().getInitiator().getId(), initiatorId)) {
                throw new ConflictException("Заявка " + r.getId()
                    + " не относится к данному событию или инициатору");
            }
            if (r.getStatus() != Status.PENDING) {
                throw new ConflictException("Изменить можно только заявки в статусе PENDING");
            }
        }
    }

    private void handleConfirmRequests(EventEntity event,
                                       List<EventRequestEntity> requests,
                                       List<Long> requestIds,
                                       Long initiatorId) {

        long confirmedNow = event.getConfirmedRequests();
        long toConfirm = requests.size();
        long limit = event.getParticipantLimit();

        if (confirmedNow + toConfirm > limit) {
            throw new ConflictException("Подтверждение этих заявок превысит лимит участников");
        }

        repository.responseToRequests(requestIds, Status.CONFIRMED, event.getId(), initiatorId);
        for (int i = 0; i < toConfirm; i++) {
            incrementConfirmedRequests(event.getId());
        }

        if (confirmedNow + toConfirm == limit) {
            List<EventRequestEntity> pending =
                (List<EventRequestEntity>) repository.findAllByEventIdAndStatus(event.getId(), Status.PENDING);

            if (!pending.isEmpty()) {
                List<Long> pendingIds = pending.stream()
                    .map(EventRequestEntity::getId)
                    .toList();
                repository.responseToRequests(pendingIds, Status.REJECTED, event.getId(), initiatorId);
            }
        }
    }

    private void handleRejectRequests(List<Long> requestIds, Long eventId, Long initiatorId) {
        repository.responseToRequests(requestIds, Status.REJECTED, eventId, initiatorId);
    }

    private void validateDuplicatedRequest(Long requesterId, Long eventId) {
        if (repository.existsByRequester_IdAndEvent_Id(requesterId, eventId)) {
            throw new ConflictException("Этот пользователь уже кидал запрос userId=" + requesterId
                + " eventId=" + eventId);
        }
    }

    private void validateInitiatorTryToRequest(Long userId, Long initiatorId) {
        if (Objects.equals(userId, initiatorId)) {
            throw new ConflictException("Инициатор не может отправить запрос на своё событие userId=" + userId);
        }
    }

    private void validateEventIsPublished(EventEntity entity) {
        if (entity.getState() != State.PUBLISHED) {
            throw new ConflictException("Событие не опубликовано state=" + entity.getState());
        }
    }

    private void validateFreePlaces(EventEntity entity) {
        if (hasParticipantLimit(entity)
            && Objects.equals(entity.getConfirmedRequests(), entity.getParticipantLimit())) {
            throw new ConflictException("Свободных мест нет eventId=" + entity.getId());
        }
    }

    private void incrementConfirmedRequests(Long eventId) {
        repository.incrementConfirmedRequests(eventId);
    }
}