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
    @Query(value = "SELECT * FROM events WHERE initiator_id = :userId OFFSET :offset LIMIT :limit", nativeQuery = true)
    Collection<EventEntity> findAllWithLimit(
            @Param("userId") Long userId,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit);

    Optional<EventEntity> findByInitiatorIdAndId(
            @Param("initiatorId") Long initiatorId,
            @Param("id") Long id);

    @Query("SELECT e FROM EventEntity e " +
            "WHERE (:userIds IS NULL OR e.initiator.id IN :userIds) " +
            "  AND (:states IS NULL OR e.state IN :states) " +
            "  AND (:categoryIds IS NULL OR e.category.id IN :categoryIds) " +
            "  AND (:isRangeStartNull = TRUE OR e.eventDate >= :rangeStart) " +
            "  AND (:isRangeEndNull = TRUE OR e.eventDate <= :rangeEnd)")
    Page<EventEntity> searchEvents(
            @Param("userIds") List<Long> userIds,
            @Param("states") List<State> states,
            @Param("categoryIds") List<Long> categoryIds,
            @Param("isRangeStartNull") boolean isRangeStartNull,
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("isRangeEndNull") boolean isRangeEndNull,
            @Param("rangeEnd") LocalDateTime rangeEnd,
            Pageable pageable);


    @Query("SELECT e FROM EventEntity e " +
            "WHERE e.state = :publishedState " +
            "  AND (:text IS NULL OR ( " +
            "       LOWER(e.annotation) LIKE :textPattern " +
            "       OR LOWER(e.description) LIKE :textPattern)) " +
            "  AND (:categories IS NULL OR e.category.id IN :categories) " +
            "  AND (:paid IS NULL OR e.paid = :paid) " +
            "  AND (:isRangeStartNull = TRUE OR e.eventDate >= :rangeStart) " +
            "  AND (:isRangeEndNull = TRUE OR e.eventDate <= :rangeEnd) " +
            "  AND (:onlyAvailable = FALSE " +
            "       OR e.participantLimit IS NULL " +
            "       OR e.participantLimit = 0 " +
            "       OR e.participantLimit > ( " +
            "           SELECT COUNT(r) FROM EventRequestEntity r " +
            "           WHERE r.event.id = e.id AND r.status = :confirmedStatus " +
            "       ))")
    Page<EventEntity> findPublicEvents(
            @Param("publishedState") State publishedState,
            @Param("text") String text,
            @Param("textPattern") String textPattern,
            @Param("categories") List<Long> categories,
            @Param("paid") Boolean paid,
            @Param("isRangeStartNull") boolean isRangeStartNull,
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("isRangeEndNull") boolean isRangeEndNull,
            @Param("rangeEnd") LocalDateTime rangeEnd,
            @Param("onlyAvailable") boolean onlyAvailable,
            @Param("confirmedStatus") Status confirmedStatus,
            Pageable pageable);

    Collection<EventEntity> findByIdIn(
            @Param("ids") Collection<Long> ids);

    Optional<EventEntity> findByIdAndState(
            @Param("id") Long id,
            @Param("state") State state);

    boolean existsByCategoryId(
            @Param("categoryId") Long categoryId);
}