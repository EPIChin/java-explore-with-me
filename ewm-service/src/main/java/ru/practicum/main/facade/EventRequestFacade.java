package ru.practicum.main.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.main.common.Status;
import ru.practicum.main.dto.response.EventRequestResponseDto;
import ru.practicum.main.dto.response.EventRequestStatusUpdateResponseDto;
import ru.practicum.main.mapper.EventRequestMapper;
import ru.practicum.main.service.EventRequestService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventRequestFacade {
    private final EventRequestService service;
    private final EventRequestMapper mapper;

    public List<EventRequestResponseDto> findAllByUserId(Long userId) {
        return mapper.buildDtoList(service.findAllByUserId(userId));
    }

    public EventRequestResponseDto create(Long userId, Long eventId) {
        return mapper.buildDto(service.create(userId, eventId));
    }

    public EventRequestResponseDto cancel(Long userId, Long requestId) {
        return mapper.buildDto(service.cancel(userId, requestId));
    }

    public List<EventRequestResponseDto> findByInitiatorIdAndEventId(Long userId, Long eventId) {
        return mapper.buildDtoList(service.findByInitiatorIdAndEventId(userId, eventId));
    }

    public EventRequestStatusUpdateResponseDto responseToRequests(List<Long> requestIds, Status status, Long eventId, Long initiatorId) {
        service.responseToRequests(requestIds, status, eventId, initiatorId);
        List<EventRequestResponseDto> confirmed = mapper.buildDtoList(service.findAllByEventIdAndStatus(eventId, Status.CONFIRMED));
        List<EventRequestResponseDto> rejected = mapper.buildDtoList(service.findAllByEventIdAndStatus(eventId, Status.REJECTED));

        return EventRequestStatusUpdateResponseDto.builder()
            .confirmedRequests(confirmed)
            .rejectedRequests(rejected)
            .build();
    }
}
