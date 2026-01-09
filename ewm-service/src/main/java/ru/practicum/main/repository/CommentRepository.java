package ru.practicum.main.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @EntityGraph(attributePaths = {"event", "author"})
    Page<CommentEntity> findAllByEvent_IdOrderByCreatedOnAsc(Long eventId, Pageable pageable);

    @EntityGraph(attributePaths = {"event", "author"})
    Page<CommentEntity> findAllByAuthor_IdOrderByCreatedOnDesc(Long authorId, Pageable pageable);

    @EntityGraph(attributePaths = {"event", "author"})
    Page<CommentEntity> findAllByAuthor_IdAndEvent_Id(Long authorId, Long eventId, Pageable pageable);
}