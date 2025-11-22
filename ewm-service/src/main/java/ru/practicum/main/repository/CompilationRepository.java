package ru.practicum.main.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.entity.CompilationEntity;

public interface CompilationRepository extends JpaRepository<CompilationEntity, Long> {
    Page<CompilationEntity> findAllByPinned(Boolean pinned, Pageable pageable);
}
