package ru.practicum.main.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.main.common.State;
import ru.practicum.main.entity.CommentEntity;
import ru.practicum.main.entity.EventEntity;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.repository.CommentRepository;
import ru.practicum.main.service.CommentService;
import ru.practicum.main.service.EventService;
import ru.practicum.main.service.UserService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final UserService userService;
    private final EventService eventService;

    @Override
    public CommentEntity addComment(Long userId, Long eventId, CommentEntity comment) {
        // Проверяем, что событие существует и опубликовано
        EventEntity event = eventService.findByIdAndState(eventId, State.PUBLISHED);

        comment.setAuthor(userService.getById(userId));
        comment.setEvent(event);

        return repository.save(comment);
    }

    @Override
    public CommentEntity updateComment(Long userId, Long commentId, String newText) {
        CommentEntity entity = repository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id=" + commentId + " не найден"));

        if (!Objects.equals(entity.getAuthor().getId(), userId)) {
            throw new ConflictException("Пользователь с id=" + userId + " не может редактировать чужой комментарий");
        }

        // Проверяем, что событие комментария существует и опубликовано
        eventService.findByIdAndState(entity.getEvent().getId(), State.PUBLISHED);

        if (newText != null && !newText.isBlank()) {
            entity.setText(newText);
        }

        return repository.save(entity);
    }

    @Override
    public void deleteComment(Long userId, Long commentId) {
        CommentEntity entity = repository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id=" + commentId + " не найден"));

        if (!Objects.equals(entity.getAuthor().getId(), userId)) {
            throw new ConflictException("Пользователь с id=" + userId + " не может удалить чужой комментарий");
        }

        // Проверяем, что событие комментария существует и опубликовано
        eventService.findByIdAndState(entity.getEvent().getId(), State.PUBLISHED);

        repository.delete(entity);
    }

    @Override
    public void deleteCommentByAdmin(Long commentId) {
        CommentEntity entity = repository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id=" + commentId + " не найден"));

        // Для админа тоже проверяем состояние события
        eventService.findByIdAndState(entity.getEvent().getId(), State.PUBLISHED);

        repository.delete(entity);
    }

    @Override
    public CommentEntity getById(Long commentId) {
        CommentEntity entity = repository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id=" + commentId + " не найден"));

        // Проверяем, что событие опубликовано
        eventService.findByIdAndState(entity.getEvent().getId(), State.PUBLISHED);

        return entity;
    }

    @Override
    public List<CommentEntity> searchComments(Long userId, Long eventId, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size, Sort.by("createdOn").descending());

        if (eventId != null) {
            // Проверяем, что событие существует и опубликовано
            eventService.findByIdAndState(eventId, State.PUBLISHED);
        }

        if (userId != null && eventId != null) {
            return repository.findAllByAuthor_IdAndEvent_Id(userId, eventId, pageable).getContent();
        }
        if (userId != null) {
            return repository.findAllByAuthor_IdOrderByCreatedOnDesc(userId, pageable).getContent();
        }
        if (eventId != null) {
            return repository.findAllByEvent_IdOrderByCreatedOnAsc(eventId, pageable).getContent();
        }

        return repository.findAll(pageable).getContent();
    }

    @Override
    public List<CommentEntity> getCommentsForEvent(Long eventId, int from, int size) {
        // Проверяем, что событие существует и опубликовано
        eventService.findByIdAndState(eventId, State.PUBLISHED);

        Pageable pageable = PageRequest.of(from / size, size, Sort.by("createdOn").ascending());
        return repository.findAllByEvent_IdOrderByCreatedOnAsc(eventId, pageable).getContent();
    }

    @Override
    public List<CommentEntity> getCommentsByUser(Long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size, Sort.by("createdOn").descending());
        return repository.findAllByAuthor_IdOrderByCreatedOnDesc(userId, pageable).getContent();
    }
}