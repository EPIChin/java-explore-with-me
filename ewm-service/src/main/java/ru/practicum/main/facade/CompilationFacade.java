package ru.practicum.main.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.main.dto.request.CompilationRequestDto;
import ru.practicum.main.dto.response.CompilationResponseDto;
import ru.practicum.main.dto.update.CompilationUpdateDto;
import ru.practicum.main.entity.CompilationEntity;
import ru.practicum.main.mapper.CompilationMapper;
import ru.practicum.main.service.CompilationService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompilationFacade {

    private final CompilationService service;
    private final CompilationMapper mapper;

    public CompilationResponseDto createCompilation(CompilationRequestDto dto) {
        CompilationEntity entity = mapper.buildEntity(dto);
        return mapper.buildDto(service.createCompilation(entity));
    }

    public CompilationResponseDto updateCompilation(Long id, CompilationUpdateDto dto) {
        CompilationEntity updateEntity = mapper.buildEntity(dto);
        return mapper.buildDto(service.updateCompilation(id, updateEntity));
    }

    public void deleteCompilation(Long id) {
        service.deleteCompilation(id);
    }

    public List<CompilationResponseDto> getCompilations(Boolean pinned, int from, int size) {
        return mapper.builDtoList(service.getCompilations(pinned, from, size));
    }

    public CompilationResponseDto getCompilation(Long id) {
        return mapper.buildDto(service.getCompilation(id));
    }
}
