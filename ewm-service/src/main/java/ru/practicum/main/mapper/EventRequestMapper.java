package ru.practicum.main.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.main.dto.response.EventRequestResponseDto;
import ru.practicum.main.entity.EventRequestEntity;

import java.util.List;

@Component
public class EventRequestMapper {
    public EventRequestResponseDto buildDto(EventRequestEntity entity) {
        return EventRequestResponseDto.builder()
            .id(entity.getId())
            .event(entity.getEvent().getId())
            .requester(entity.getRequester().getId())
            .created(entity.getCreated())
            .status(entity.getStatus())
            .build();
    }

    public List<EventRequestResponseDto> buildDtoList(List<EventRequestEntity> entities) {
        return entities.stream().map(this::buildDto).toList();
    }
}
