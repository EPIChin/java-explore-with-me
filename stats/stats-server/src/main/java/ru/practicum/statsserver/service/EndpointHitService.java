package ru.practicum.statsserver.service;

import ru.practicum.statsdto.request.EndpointHitRequestDto;
import ru.practicum.statsdto.response.ViewStatsResponseDto;

import java.util.List;

public interface EndpointHitService {
    void hit(EndpointHitRequestDto dto);

    List<ViewStatsResponseDto> getStats(String start, String end, List<String> uris, boolean unique);
}

