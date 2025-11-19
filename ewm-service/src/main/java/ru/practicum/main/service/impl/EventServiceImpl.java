package ru.practicum.main.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.main.common.State;
import ru.practicum.main.common.StateAction;
import ru.practicum.main.common.Status;
import ru.practicum.main.dto.update.EventUpdateDto;
import ru.practicum.main.entity.EventEntity;
import ru.practicum.main.exception.BadRequestException;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.repository.EventRepository;
import ru.practicum.main.service.CategoryService;
import ru.practicum.main.service.EventService;
import ru.practicum.main.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.main.common.Constants.FORMATTER;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository repository;
    private final UserService userService;
    private final CategoryService categoryService;

    @Override
    public EventEntity create(Long userId, EventEntity eventEntity) {
        validateEventDateAtLeastTwoHoursAhead(eventEntity.getEventDate());
        eventEntity.setInitiator(userService.getById(userId));
        eventEntity.setCategory(categoryService.getById(eventEntity.getCategoryId()));
        return repository.save(eventEntity);
    }

    @Override
    public List<EventEntity> findAllByUserId(Long userId, Integer from, Integer size) {
        return (List<EventEntity>) repository.findAllWithLimit(userId, from, size);
    }

    @Override
    public EventEntity findByUserIdAndEventId(Long userId, Long eventId) {
        return repository.findByInitiatorIdAndId(userId, eventId)
            .orElseThrow(() -> new NotFoundException(
                "Событие с id=" + eventId + " не найдено для пользователя с id=" + userId));
    }

    @Override
    public EventEntity updateEventByUser(Long userId, Long eventId, EventUpdateDto dto) {
        EventEntity entity = findByIdAndInitiatorId(eventId, userId);

        if (entity.getState() != State.PENDING && entity.getState() != State.CANCELED) {
            throw new ConflictException(
                "Изменить можно только события в статусах PENDING или CANCELED. Текущий статус: " + entity.getState());
        }

        applyCommonUpdates(entity, dto);
        updateEventDateForUser(entity, dto);
        applyUserStateAction(entity, dto.getStateAction());

        return repository.save(entity);
    }


    @Override
    public List<EventEntity> searchEvents(List<Long> userIds,
                                          List<State> states,
                                          List<Long> categoryIds,
                                          LocalDateTime rangeStart,
                                          LocalDateTime rangeEnd,
                                          Integer from,
                                          Integer size) {

        userIds = normalizeIdsFilter(userIds);
        if (states != null && states.isEmpty()) {
            states = null;
        }
        categoryIds = normalizeIdsFilter(categoryIds);

        Pageable pageable = PageRequest.of(from / size, size);
        Page<EventEntity> page =
            repository.searchEvents(userIds, states, categoryIds, rangeStart, rangeEnd, pageable);
        return page.getContent();
    }

    @Override
    public EventEntity updateEventByAdmin(Long eventId, EventUpdateDto dto) {
        EventEntity entity = getById(eventId);

        applyCommonUpdates(entity, dto);
        LocalDateTime newEventDate = updateEventDateForAdmin(entity, dto);
        applyAdminStateAction(entity, dto.getStateAction(), newEventDate);

        return repository.save(entity);
    }

    @Override
    public List<EventEntity> findPublicEvents(String text,
                                              List<Long> categories,
                                              Boolean paid,
                                              LocalDateTime rangeStart,
                                              LocalDateTime rangeEnd,
                                              Boolean onlyAvailable,
                                              String sort,
                                              int from,
                                              int size) {

        validateDateRange(rangeStart, rangeEnd);

        LocalDateTime now = LocalDateTime.now();
        if (rangeStart == null) {
            rangeStart = now;
        }

        String textPattern = buildTextPattern(text);
        Sort sortSpec = resolveSort(sort);

        Pageable pageable = PageRequest.of(from / size, size, sortSpec);

        return repository.findPublicEvents(
                State.PUBLISHED,
                text,
                textPattern,
                (categories == null || categories.isEmpty()) ? null : categories,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                Status.CONFIRMED,
                pageable
            )
            .getContent();
    }

    @Override
    public EventEntity getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Событие с id=" + id + " не найдено"));
    }

    @Override
    public EventEntity findByIdAndInitiatorId(Long eventId, Long userId) {
        return repository.findByInitiatorIdAndId(userId, eventId)
            .orElseThrow(() -> new NotFoundException(
                "Событие с id=" + eventId + " и userId=" + userId + " не найдено"));
    }

    @Override
    public List<EventEntity> findByIds(List<Long> ids) {
        return (List<EventEntity>) repository.findByIdIn(ids);
    }

    @Override
    public EventEntity findByIdAndState(Long id, State state) {
        return repository.findByIdAndState(id, state)
            .orElseThrow(() -> new NotFoundException("Событие с id=" + id + " не найдено"));
    }

    private void validateEventDateAtLeastTwoHoursAhead(LocalDateTime eventDate) {
        if (eventDate == null) {
            return;
        }
        if (eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new BadRequestException(
                "Дата и время события должны быть не ранее, чем через 2 часа от текущего момента"
            );
        }
    }

    private void validateDateRange(LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        if (rangeStart != null && rangeEnd != null && rangeEnd.isBefore(rangeStart)) {
            throw new BadRequestException("rangeEnd must be after rangeStart");
        }
    }

    private LocalDateTime updateEventDateForAdmin(EventEntity entity, EventUpdateDto dto) {
        if (dto.getEventDate() == null || dto.getEventDate().isBlank()) {
            return null;
        }

        LocalDateTime newEventDate = LocalDateTime.parse(dto.getEventDate(), FORMATTER);

        if (newEventDate.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Дата события не может быть в прошлом");
        }

        entity.setEventDate(newEventDate);
        return newEventDate;
    }

    private void updateEventDateForUser(EventEntity entity, EventUpdateDto dto) {
        if (dto.getEventDate() == null || dto.getEventDate().isBlank()) {
            return;
        }

        LocalDateTime newEventDate = LocalDateTime.parse(dto.getEventDate(), FORMATTER);

        if (newEventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new BadRequestException(
                "Дата и время события должны быть не ранее, чем через 2 часа от текущего момента"
            );
        }

        entity.setEventDate(newEventDate);
    }

    private void applyAdminStateAction(EventEntity entity,
                                       StateAction stateAction,
                                       LocalDateTime newEventDate) {
        if (stateAction == null) {
            return;
        }

        switch (stateAction) {
            case PUBLISH_EVENT -> handlePublishByAdmin(entity, newEventDate);
            case REJECT_EVENT -> handleRejectByAdmin(entity);
            default -> throw new ConflictException("Неизвестное состояние действия: " + stateAction);
        }
    }

    private void handlePublishByAdmin(EventEntity entity, LocalDateTime newEventDate) {
        if (entity.getState() != State.PENDING) {
            throw new ConflictException("Событие может быть опубликовано только из состояния PENDING");
        }

        LocalDateTime publishTime = LocalDateTime.now();
        LocalDateTime dateToCheck = newEventDate != null ? newEventDate : entity.getEventDate();

        if (dateToCheck.isBefore(publishTime.plusHours(1))) {
            throw new ConflictException(
                "Дата начала события должна быть не ранее, чем через час после публикации");
        }

        entity.setState(State.PUBLISHED);
        entity.setPublishedOn(publishTime);
    }

    private void handleRejectByAdmin(EventEntity entity) {
        if (entity.getState() == State.PUBLISHED) {
            throw new ConflictException("Невозможно отклонить уже опубликованное событие");
        }
        entity.setState(State.CANCELED);
        entity.setPublishedOn(null);
    }

    private void applyUserStateAction(EventEntity entity, StateAction stateAction) {
        if (stateAction == null) {
            return;
        }

        switch (stateAction) {
            case CANCEL_REVIEW -> {
                if (entity.getState() == State.PUBLISHED) {
                    throw new ConflictException("Невозможно отклонить событие, поскольку оно уже опубликовано");
                }
                entity.setState(State.CANCELED);
                entity.setPublishedOn(null);
            }
            case SEND_TO_REVIEW -> entity.setState(State.PENDING);
            default -> throw new ConflictException("Неизвестное состояние действия: " + stateAction);
        }
    }

    private void applyCommonUpdates(EventEntity entity, EventUpdateDto dto) {
        if (hasText(dto.getAnnotation())) {
            entity.setAnnotation(dto.getAnnotation());
        }
        if (hasText(dto.getDescription())) {
            entity.setDescription(dto.getDescription());
        }
        if (hasText(dto.getTitle())) {
            entity.setTitle(dto.getTitle());
        }
        if (dto.getPaid() != null) {
            entity.setPaid(dto.getPaid());
        }
        if (dto.getParticipantLimit() != null) {
            entity.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getRequestModeration() != null) {
            entity.setRequestModeration(dto.getRequestModeration());
        }
        if (dto.getLocation() != null) {
            entity.setLocation(dto.getLocation());
        }
        if (dto.getCategory() != null) {
            entity.setCategory(categoryService.getById(dto.getCategory()));
        }
    }

    private List<Long> normalizeIdsFilter(List<Long> ids) {
        if (ids != null && ids.size() == 1 && ids.getFirst() == 0) {
            return null;
        }
        return ids;
    }

    private String buildTextPattern(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }
        return "%" + text.toLowerCase() + "%";
    }

    private Sort resolveSort(String sort) {
        if (sort == null) {
            return Sort.by("eventDate").ascending();
        }

        return switch (sort) {
            case "EVENT_DATE" -> Sort.by("eventDate").ascending();
            case "VIEWS" -> Sort.by("views").descending();
            default -> Sort.by("eventDate").ascending();
        };
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}