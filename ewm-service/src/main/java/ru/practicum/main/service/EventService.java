package ru.practicum.main.service;

import ru.practicum.main.common.State;
import ru.practicum.main.dto.update.EventUpdateDto;
import ru.practicum.main.entity.EventEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventEntity create(Long userId, EventEntity eventEntity);

    List<EventEntity> findAllByUserId(Long userId, Integer from, Integer size);

    EventEntity findByUserIdAndEventId(Long userId, Long eventId);

    List<EventEntity> searchEvents(List<Long> userIds,
                                   List<State> states,
                                   List<Long> categoryIds,
                                   LocalDateTime rangeStart,
                                   LocalDateTime rangeEnd,
                                   Integer from,
                                   Integer size);

    EventEntity updateEventByAdmin(Long eventIds, EventUpdateDto dto);

    EventEntity updateEventByUser(Long userId, Long eventIds, EventUpdateDto dto);

    EventEntity getById(Long id);

    EventEntity findByIdAndInitiatorId(Long eventId, Long userId);

    List<EventEntity> findPublicEvents(String text,
                                       List<Long> categories,
                                       Boolean paid,
                                       LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd,
                                       Boolean onlyAvailable,
                                       String sort,
                                       int from,
                                       int size);

    List<EventEntity> findByIds(List<Long> ids);

    EventEntity findByIdAndState(Long id, State state);
}
