package ru.practicum.main.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.main.dto.request.CategoryRequestDto;
import ru.practicum.main.dto.response.CategoryResponseDto;
import ru.practicum.main.dto.update.CategoryUpdateDto;
import ru.practicum.main.entity.CategoryEntity;

import java.util.List;

@Component
public class CategoryMapper {

    public CategoryEntity buildEntity(CategoryRequestDto dto) {
        return CategoryEntity.builder()
            .name(dto.getName())
            .build();
    }

    public CategoryEntity buildEntity(CategoryUpdateDto dto) {
        return CategoryEntity.builder()
            .name(dto.getName())
            .build();
    }

    public CategoryResponseDto buildDto(CategoryEntity entity) {
        return CategoryResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .build();
    }

    public List<CategoryResponseDto> buildDtoList(List<CategoryEntity> entities) {
        return entities.stream().map(this::buildDto).toList();
    }
}
