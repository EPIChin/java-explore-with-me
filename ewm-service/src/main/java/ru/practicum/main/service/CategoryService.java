package ru.practicum.main.service;

import ru.practicum.main.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {

    CategoryEntity createCategory(CategoryEntity entity);

    CategoryEntity updateCategory(Long id, CategoryEntity update);

    void deleteCategory(Long id);

    List<CategoryEntity> getCategories(int from, int size);

    CategoryEntity getCategory(Long id);

    CategoryEntity getById(Long id);
}
