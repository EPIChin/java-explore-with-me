package ru.practicum.main.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.main.entity.CategoryEntity;
import ru.practicum.main.exception.AlreadyExists;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.repository.CategoryRepository;
import ru.practicum.main.repository.EventRepository;
import ru.practicum.main.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final EventRepository eventRepository;

    @Override
    public CategoryEntity createCategory(CategoryEntity entity) {
        validateNameUniqueOnCreate(entity.getName());
        return repository.save(entity);
    }

    @Override
    public CategoryEntity updateCategory(Long id, CategoryEntity update) {
        CategoryEntity existing = getCategoryOrThrow(id);

        if (hasText(update.getName())) {
            validateNameUniqueOnUpdate(id, update.getName());
            existing.setName(update.getName());
        }

        return repository.save(existing);
    }

    @Override
    public void deleteCategory(Long id) {
        CategoryEntity existing = getCategoryOrThrow(id);
        validateCategoryNotUsed(existing.getId());
        repository.deleteById(id);
    }

    @Override
    public List<CategoryEntity> getCategories(int from, int size) {
        return repository.findAll(PageRequest.of(from / size, size)).getContent();
    }

    @Override
    public CategoryEntity getCategory(Long id) {
        return getCategoryOrThrow(id);
    }

    @Override
    public CategoryEntity getById(Long id) {
        return getCategoryOrThrow(id);
    }

    private CategoryEntity getCategoryOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Категория с id=" + id + " не найдена"));
    }

    private void validateNameUniqueOnCreate(String name) {
        if (!hasText(name)) {
            return;
        }
        if (repository.existsByName(name)) {
            throw new AlreadyExists("Категория с именем '" + name + "' уже существует");
        }
    }

    private void validateNameUniqueOnUpdate(Long id, String newName) {
        if (!hasText(newName)) {
            return;
        }
        if (repository.existsByNameAndIdNot(newName, id)) {
            throw new ConflictException("Категория с именем '" + newName + "' уже существует");
        }
    }

    private void validateCategoryNotUsed(Long categoryId) {
        if (eventRepository.existsByCategoryId(categoryId)) {
            throw new ConflictException("Категория с id=" + categoryId
                + " содержит события и не может быть удалена");
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
