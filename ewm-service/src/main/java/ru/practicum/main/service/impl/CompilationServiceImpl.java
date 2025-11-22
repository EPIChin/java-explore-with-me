package ru.practicum.main.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main.entity.CompilationEntity;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.repository.CompilationRepository;
import ru.practicum.main.service.CompilationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository repository;

    @Override
    public CompilationEntity createCompilation(CompilationEntity compilationEntity) {
        if (compilationEntity.getPinned() == null) {
            compilationEntity.setPinned(false);
        }
        return repository.save(compilationEntity);
    }

    @Override
    public CompilationEntity updateCompilation(Long id, CompilationEntity update) {
        CompilationEntity existing = getCompilationOrThrow(id);
        applyUpdates(existing, update);
        return repository.save(existing);
    }

    @Override
    public void deleteCompilation(Long id) {
        CompilationEntity existing = getCompilationOrThrow(id);
        repository.delete(existing);
    }

    @Override
    public List<CompilationEntity> getCompilations(Boolean pinned, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);

        return pinned == null
            ? repository.findAll(pageable).getContent()
            : repository.findAllByPinned(pinned, pageable).getContent();
    }

    @Override
    public CompilationEntity getCompilation(Long id) {
        return getCompilationOrThrow(id);
    }

    private CompilationEntity getCompilationOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Подборка с id=" + id + " не найдена"));
    }

    private void applyUpdates(CompilationEntity target, CompilationEntity source) {
        if (hasText(source.getTitle())) {
            target.setTitle(source.getTitle());
        }
        if (source.getPinned() != null) {
            target.setPinned(source.getPinned());
        }
        if (source.getEvents() != null) {
            target.setEvents(source.getEvents());
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}