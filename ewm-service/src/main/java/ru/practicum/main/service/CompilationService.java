package ru.practicum.main.service;

import ru.practicum.main.entity.CompilationEntity;

import java.util.List;

public interface CompilationService {
    CompilationEntity createCompilation(CompilationEntity compilationEntity);

    CompilationEntity updateCompilation(Long id, CompilationEntity compilationEntity);

    void deleteCompilation(Long id);

    List<CompilationEntity> getCompilations(Boolean pinned, int from, int size);

    CompilationEntity getCompilation(Long id);
}
