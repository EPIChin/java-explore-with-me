package ru.practicum.main.service;

import ru.practicum.main.entity.CommentEntity;

import java.util.List;

public interface CommentService {

    CommentEntity addComment(Long userId, Long eventId, CommentEntity comment);

    CommentEntity updateComment(Long userId, Long commentId, String newText);

    void deleteComment(Long userId, Long commentId);

    void deleteCommentByAdmin(Long commentId);

    CommentEntity getById(Long commentId);

    List<CommentEntity> searchComments(Long userId, Long eventId, int from, int size);

    List<CommentEntity> getCommentsForEvent(Long eventId, int from, int size);

    List<CommentEntity> getCommentsByUser(Long userId, int from, int size);
}
