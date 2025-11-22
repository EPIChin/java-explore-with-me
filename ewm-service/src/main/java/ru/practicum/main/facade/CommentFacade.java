package ru.practicum.main.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.main.dto.request.CommentRequestDto;
import ru.practicum.main.dto.response.CommentResponseDto;
import ru.practicum.main.entity.CommentEntity;
import ru.practicum.main.mapper.CommentMapper;
import ru.practicum.main.service.CommentService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentFacade {
    private final CommentService service;
    private final CommentMapper mapper;

    public CommentResponseDto addComment(Long userId, Long eventId, CommentRequestDto dto) {
        CommentEntity entity = mapper.buildEntity(dto);
        return mapper.buildDto(service.addComment(userId, eventId, entity));
    }

    public CommentResponseDto updateComment(Long userId, Long commentId, CommentRequestDto dto) {
        CommentEntity entity = service.updateComment(userId, commentId, dto.getText());
        return mapper.buildDto(entity);
    }

    public void deleteComment(Long userId, Long commentId) {
        service.deleteComment(userId, commentId);
    }

    public List<CommentResponseDto> getUserComments(Long userId, int from, int size) {
        return mapper.buildDtoList(service.getCommentsByUser(userId, from, size));
    }

    public List<CommentResponseDto> getEventComments(Long eventId, int from, int size) {
        return mapper.buildDtoList(service.getCommentsForEvent(eventId, from, size));
    }

    public void deleteCommentByAdmin(Long commentId) {
        service.deleteCommentByAdmin(commentId);
    }

    public CommentResponseDto getCommentById(Long commentId) {
        return mapper.buildDto(service.getById(commentId));
    }

    public List<CommentResponseDto> searchComments(Long userId, Long eventId, int from, int size) {
        return mapper.buildDtoList(service.searchComments(userId, eventId, from, size));
    }
}
