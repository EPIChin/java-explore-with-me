package ru.practicum.main.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.main.dto.request.CommentRequestDto;
import ru.practicum.main.dto.response.CommentResponseDto;
import ru.practicum.main.entity.CommentEntity;

import java.util.List;

@Component
public class CommentMapper {

    public CommentEntity buildEntity(CommentRequestDto dto) {
        return CommentEntity.builder()
            .text(dto.getText())
            .build();
    }

    public CommentResponseDto buildDto(CommentEntity entity) {
        return CommentResponseDto.builder()
            .id(entity.getId())
            .eventId(entity.getEvent().getId())
            .authorId(entity.getAuthor().getId())
            .authorName(entity.getAuthor().getName())
            .text(entity.getText())
            .createdOn(entity.getCreatedOn())
            .build();
    }

    public List<CommentResponseDto> buildDtoList(List<CommentEntity> entities) {
        return entities.stream()
            .map(this::buildDto)
            .toList();
    }
}
