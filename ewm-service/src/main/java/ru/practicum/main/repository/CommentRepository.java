package ru.practicum.main.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Page<CommentEntity> findAllByEvent_IdOrderByCreatedOnAsc(Long eventId, Pageable pageable);

    Page<CommentEntity> findAllByAuthor_IdOrderByCreatedOnDesc(Long authorId, Pageable pageable);

    Page<CommentEntity> findAllByAuthor_IdAndEvent_Id(Long authorId, Long eventId, Pageable pageable);
}
