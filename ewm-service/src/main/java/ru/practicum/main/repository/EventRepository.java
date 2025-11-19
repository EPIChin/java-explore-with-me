package ru.practicum.main.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.main.common.State;
import ru.practicum.main.common.Status;
import ru.practicum.main.entity.EventEntity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    @Query(value = "select * from events where initiator_id = :userId offset :offset limit :limit", nativeQuery = true)
    Collection<EventEntity> findAllWithLimit(@Param("userId") Long userId,
                                             @Param("offset") Integer offset,
                                             @Param("limit") Integer limit);

    Optional<EventEntity> findByInitiatorIdAndId(Long initiatorId, Long id);

    @Query("""
        SELECT e FROM EventEntity e
        WHERE (:userIds IS NULL OR e.initiator.id IN :userIds)
          AND (:states IS NULL OR e.state IN :states)
          AND (:categoryIds IS NULL OR e.category.id IN :categoryIds)
          AND (cast(:rangeStart as localdatetime ) IS NULL OR e.eventDate >= :rangeStart)
          AND (cast(:rangeEnd as localdatetime ) IS NULL OR e.eventDate <= :rangeEnd)
        """)
    Page<EventEntity> searchEvents(
        @Param("userIds") List<Long> userIds,
        @Param("states") List<State> states,
        @Param("categoryIds") List<Long> categoryIds,
        @Param("rangeStart") LocalDateTime rangeStart,
        @Param("rangeEnd") LocalDateTime rangeEnd,
        Pageable pageable);

    @Query("""
        select e from EventEntity e
        where e.state = :publishedState
          and (:text is null or (
                 lower(e.annotation) like :textPattern
                 or lower(e.description) like :textPattern))
          and (:categories is null or e.category.id in :categories)
          and (:paid is null or e.paid = :paid)
          and (cast(:rangeStart as localdatetime ) is null or e.eventDate >= :rangeStart)
          and (cast(:rangeEnd as localdatetime ) is null or e.eventDate <= :rangeEnd)
          and (
               :onlyAvailable = false
               or e.participantLimit is null
               or e.participantLimit = 0
               or e.participantLimit > (
                    select count(r) from EventRequestEntity r
                    where r.event.id = e.id and r.status = :confirmedStatus
               )
          )
        """)
    Page<EventEntity> findPublicEvents(
        @Param("publishedState") State publishedState,
        @Param("text") String text,
        @Param("textPattern") String textPattern,
        @Param("categories") List<Long> categories,
        @Param("paid") Boolean paid,
        @Param("rangeStart") LocalDateTime rangeStart,
        @Param("rangeEnd") LocalDateTime rangeEnd,
        @Param("onlyAvailable") boolean onlyAvailable,
        @Param("confirmedStatus") Status confirmedStatus,
        Pageable pageable);

    Collection<EventEntity> findByIdIn(Collection<Long> ids);

    Optional<EventEntity> findByIdAndState(Long id, State state);

    boolean existsByCategoryId(Long categoryId);
}
