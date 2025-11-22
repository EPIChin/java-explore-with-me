package ru.practicum.main.service;

import ru.practicum.main.common.Status;
import ru.practicum.main.entity.EventRequestEntity;

import java.util.List;

public interface EventRequestService {
    List<EventRequestEntity> findAllByUserId(Long userId);

    EventRequestEntity create(Long userId, Long eventId);

    EventRequestEntity cancel(Long userId, Long requestId);

    EventRequestEntity findByUserIdAndRequestId(Long userId, Long eventId);

    List<EventRequestEntity> findByInitiatorIdAndEventId(Long userId, Long eventId);

    List<EventRequestEntity> findAllByEventIdAndStatus(Long eventId, Status status);

    void responseToRequests(List<Long> requestIds,
                            Status status,
                            Long eventId,
                            Long initiatorId);
}
