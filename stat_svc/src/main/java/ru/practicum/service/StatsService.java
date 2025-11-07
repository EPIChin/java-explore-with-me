package ru.practicum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.EndpointHitMapper;
import ru.practicum.dto.EndpointStats;
import ru.practicum.dto.RequestParamDto;
import ru.practicum.repository.StatsJpaRepository;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatsService {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final StatsJpaRepository statsJpaRepository;

    @Autowired
    public StatsService(StatsJpaRepository statsJpaRepository) {
        this.statsJpaRepository = statsJpaRepository;
    }

    public void saveHit(EndpointHitDto hitDto) {
        statsJpaRepository.save(EndpointHitMapper.toHit(hitDto)); //преобразуем DTO в сущность и сохраняем в базу
    }


    public List<EndpointHitDto> getAllHits() {
        return statsJpaRepository.findAll().stream()
                .map(EndpointHitMapper::toDto)
                .collect(Collectors.toList());
    }


    public List<EndpointStats> getStats(RequestParamDto requestParamDto) {

        String startDecoded = URLDecoder.decode(requestParamDto.getStart(), StandardCharsets.UTF_8);
        String endDecoded = URLDecoder.decode(requestParamDto.getEnd(), StandardCharsets.UTF_8);

        LocalDateTime start = LocalDateTime.parse(startDecoded, TIME_FORMAT);
        LocalDateTime end = LocalDateTime.parse(endDecoded, TIME_FORMAT);

        String[] uris = requestParamDto.getUris();
        if (requestParamDto.isUnique()) {
            if (uris == null) {
                return statsJpaRepository.getStatsUnique(start, end);
            } else {
                return statsJpaRepository.getStatsUniqueWithUris(start, end, List.of(uris));
            }
        } else { // !unique
            if (uris == null) {
                return statsJpaRepository.getStatsNotUnique(start, end);
            } else {
                return statsJpaRepository.getStatsNotUniqueWithUris(start, end, List.of(uris));
            }
        }
    }
}