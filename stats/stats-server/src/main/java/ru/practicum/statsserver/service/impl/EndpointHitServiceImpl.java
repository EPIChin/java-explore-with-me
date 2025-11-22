package ru.practicum.statsserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.statsdto.request.EndpointHitRequestDto;
import ru.practicum.statsdto.response.ViewStatsResponseDto;
import ru.practicum.statsserver.mapper.StatsMapper;
import ru.practicum.statsserver.repository.EndpointHitRepository;
import ru.practicum.statsserver.service.EndpointHitService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EndpointHitServiceImpl implements EndpointHitService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final EndpointHitRepository repository;
    private final StatsMapper mapper;

    @Override
    public void hit(EndpointHitRequestDto dto) {
        repository.save(mapper.buildEntity(dto));
    }

    @Override
    public List<ViewStatsResponseDto> getStats(String start, String end, List<String> uris, boolean unique) {
        LocalDateTime startDateTime = LocalDateTime.parse(start, DATE_TIME_FORMATTER);
        LocalDateTime endDateTime = LocalDateTime.parse(end, DATE_TIME_FORMATTER);

        if (endDateTime.isBefore(startDateTime) || endDateTime.equals(startDateTime)) {
            throw new IllegalArgumentException("End не может быть до или равен Start");
        }

        if (unique) {
            return repository.findStatsUnique(startDateTime, endDateTime, (uris == null || uris.isEmpty()) ? null : uris);
        } else {
            return repository.findStats(startDateTime, endDateTime, (uris == null || uris.isEmpty()) ? null : uris);
        }
    }
}

