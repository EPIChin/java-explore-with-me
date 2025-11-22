package ru.practicum.main.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.main.dto.request.CompilationRequestDto;
import ru.practicum.main.dto.response.CompilationResponseDto;
import ru.practicum.main.dto.update.CompilationUpdateDto;
import ru.practicum.main.entity.CompilationEntity;
import ru.practicum.main.service.EventService;

import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CompilationMapper {

    private final EventMapper eventMapper;
    private final EventService eventService;


    public CompilationEntity buildEntity(CompilationRequestDto dto) {
        CompilationEntity entity = new CompilationEntity();

        entity.setTitle(dto.getTitle());
        entity.setPinned(dto.getPinned() != null ? dto.getPinned() : false);

        if (dto.getEvents() != null && !dto.getEvents().isEmpty()) {
            entity.setEvents(new HashSet<>(eventService.findByIds(dto.getEvents())));
        } else {
            entity.setEvents(new HashSet<>());
        }

        return entity;
    }

    public CompilationEntity buildEntity(CompilationUpdateDto dto) {
        CompilationEntity entity = new CompilationEntity();

        entity.setTitle(dto.getTitle());

        entity.setPinned(dto.getPinned());

        if (dto.getEvents() != null) {
            if (dto.getEvents().isEmpty()) {
                entity.setEvents(new HashSet<>());
            } else {
                entity.setEvents(new HashSet<>(eventService.findByIds(dto.getEvents())));
            }
        }

        return entity;
    }

    public CompilationResponseDto buildDto(CompilationEntity entity) {
        return CompilationResponseDto.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .pinned(entity.getPinned())
            .events(eventMapper.buildShortDtoList(entity.getEvents().stream().toList()))
            .build();
    }

    public List<CompilationResponseDto> builDtoList(List<CompilationEntity> entities) {
        return entities.stream().map(this::buildDto).toList();
    }
}
