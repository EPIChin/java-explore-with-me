package ru.practicum.main.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.main.dto.request.EventRequestDto;
import ru.practicum.main.dto.response.EventResponseDto;
import ru.practicum.main.dto.response.EventResponseShortDto;
import ru.practicum.main.entity.EventEntity;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;

    public EventEntity buildEntity(EventRequestDto dto) {
        EventEntity.EventEntityBuilder builder = EventEntity.builder()
            .annotation(dto.getAnnotation())
            .categoryId(dto.getCategory())
            .description(dto.getDescription())
            .eventDate(dto.getEventDate())
            .location(dto.getLocation())
            .paid(dto.getPaid() != null && dto.getPaid())
            .title(dto.getTitle());

        if (dto.getParticipantLimit() != null) {
            builder.participantLimit(dto.getParticipantLimit());
        }
        if (dto.getRequestModeration() != null) {
            builder.requestModeration(dto.getRequestModeration());
        }

        return builder.build();
    }


    public EventResponseDto buildDto(EventEntity eventEntity) {
        return EventResponseDto.builder()
            .id(eventEntity.getId())
            .state(eventEntity.getState())
            .eventDate(eventEntity.getEventDate())
            .paid(eventEntity.getPaid())
            .requestModeration(eventEntity.getRequestModeration())
            .description(eventEntity.getDescription())
            .category(categoryMapper.buildDto(eventEntity.getCategory()))
            .confirmedRequests(eventEntity.getConfirmedRequests())
            .createdOn(eventEntity.getCreatedOn())
            .initiator(userMapper.buildShortDto(eventEntity.getInitiator()))
            .title(eventEntity.getTitle())
            .publishedOn(eventEntity.getPublishedOn())
            .annotation(eventEntity.getAnnotation())
            .location(eventEntity.getLocation())
            .participantLimit(eventEntity.getParticipantLimit())
            .build();
    }

    public EventResponseShortDto buildShortDto(EventEntity eventEntity) {
        return EventResponseShortDto.builder()
            .id(eventEntity.getId())
            .title(eventEntity.getTitle())
            .annotation(eventEntity.getAnnotation())
            .confirmedRequests(eventEntity.getConfirmedRequests())
            .eventDate(eventEntity.getEventDate())
            .paid(eventEntity.getPaid())
            .category(categoryMapper.buildDto(eventEntity.getCategory()))
            .initiator(userMapper.buildShortDto(eventEntity.getInitiator()))
            .build();
    }

    public List<EventResponseDto> buildDtoList(List<EventEntity> entities) {
        return entities.stream().map(this::buildDto).toList();
    }

    public List<EventResponseShortDto> buildShortDtoList(List<EventEntity> entities) {
        return entities.stream().map(this::buildShortDto).toList();
    }
}
