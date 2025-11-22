package ru.practicum.statsserver.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.statsdto.request.EndpointHitRequestDto;
import ru.practicum.statsserver.entity.EndpointHitEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class StatsMapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public EndpointHitEntity buildEntity(EndpointHitRequestDto dto) {
        return EndpointHitEntity.builder()
            .app(dto.getApp())
            .uri(dto.getUri())
            .ip(dto.getIp())
            .timestamp(LocalDateTime.parse(dto.getTimestamp(), FORMATTER))
            .build();
    }
}

