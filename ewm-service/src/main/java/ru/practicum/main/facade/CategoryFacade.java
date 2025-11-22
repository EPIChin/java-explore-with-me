package ru.practicum.main.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.main.dto.request.CategoryRequestDto;
import ru.practicum.main.dto.response.CategoryResponseDto;
import ru.practicum.main.dto.update.CategoryUpdateDto;
import ru.practicum.main.entity.CategoryEntity;
import ru.practicum.main.mapper.CategoryMapper;
import ru.practicum.main.service.CategoryService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryFacade {

    private final CategoryService service;
    private final CategoryMapper mapper;

    public CategoryResponseDto create(CategoryRequestDto dto) {
        CategoryEntity entity = mapper.buildEntity(dto);
        return mapper.buildDto(service.createCategory(entity));
    }

    public CategoryResponseDto update(Long id, CategoryUpdateDto dto) {
        CategoryEntity update = mapper.buildEntity(dto);
        return mapper.buildDto(service.updateCategory(id, update));
    }

    public void delete(Long id) {
        service.deleteCategory(id);
    }

    public List<CategoryResponseDto> getAll(int from, int size) {
        return mapper.buildDtoList(service.getCategories(from, size));
    }

    public CategoryResponseDto getOne(Long id) {
        return mapper.buildDto(service.getCategory(id));
    }
}
