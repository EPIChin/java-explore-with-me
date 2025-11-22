package ru.practicum.main.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.main.common.Status;
import ru.practicum.main.entity.EventRequestEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EventRequestRepository extends JpaRepository<EventRequestEntity, Long> {
    Collection<EventRequestEntity> findAllByRequesterId(Long requesterId);

    Optional<EventRequestEntity> findByRequester_IdAndId(Long requesterId, Long id);

    @Query(value = """
        select er.*\s
        from event_requests er
        join events e on e.id = er.event_id
        where e.initiator_id = :initiatorId
          and e.id = :eventId
       \s""", nativeQuery = true)
    Collection<EventRequestEntity> findByInitiatorIdAndEventId(@Param("initiatorId") Long initiatorId,
                                                               @Param("eventId") Long eventId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("""
        update EventRequestEntity e
        set e.status = :status
        where e.id in :requestIds
          and e.event.id = :eventId
          and e.event.initiator.id = :initiatorId
        """)
    void responseToRequests(@Param("requestIds") List<Long> requestIds,
                            @Param("status") Status status,
                            @Param("eventId") Long eventId,
                            @Param("initiatorId") Long initiatorId);

    Collection<EventRequestEntity> findAllByEventIdAndStatus(Long id, Status status);

    boolean existsByRequester_IdAndEvent_Id(Long requesterId, Long eventId);

    @Modifying
    @Transactional
    @Query("update EventEntity e set e.confirmedRequests = e.confirmedRequests + 1 where e.id = :eventId")
    void incrementConfirmedRequests(@Param("eventId") Long eventId);
}
