package ru.practicum.main.facade;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.practicum.main.common.State;
import ru.practicum.main.dto.request.EventRequestDto;
import ru.practicum.main.dto.response.EventResponseDto;
import ru.practicum.main.dto.response.EventResponseShortDto;
import ru.practicum.main.dto.update.EventUpdateDto;
import ru.practicum.main.entity.EventEntity;
import ru.practicum.main.mapper.EventMapper;
import ru.practicum.main.service.EventService;
import ru.practicum.statsclient.client.StatsClient;
import ru.practicum.statsdto.request.EndpointHitRequestDto;
import ru.practicum.statsdto.response.ViewStatsResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.practicum.main.common.Constants.FORMATTER;

@Component
@RequiredArgsConstructor
public class EventFacade {

    private final EventService service;
    private final EventMapper mapper;
    private final StatsClient statsClient;

    @Value("${spring.application.name}")
    private String appName;

    public EventResponseDto create(Long userId, EventRequestDto dto) {
        EventEntity eventEntity = mapper.buildEntity(dto);
        service.create(userId, eventEntity);
        return mapper.buildDto(eventEntity);
    }

    public List<EventResponseDto> findAllWithLimit(Long userId, Integer from, Integer size) {
        List<EventEntity> entities = service.findAllByUserId(userId, from, size);
        return mapper.buildDtoList(entities);
    }

    public EventResponseDto findByUserIdAndEventId(Long userId, Long eventId) {
        return mapper.buildDto(service.findByUserIdAndEventId(userId, eventId));
    }

    public List<EventResponseDto> searchEvents(List<Long> userIds,
                                               List<State> states,
                                               List<Long> categoryIds,
                                               LocalDateTime rangeStart,
                                               LocalDateTime rangeEnd,
                                               Integer from,
                                               Integer size) {
        return mapper.buildDtoList(
            service.searchEvents(userIds, states, categoryIds, rangeStart, rangeEnd, from, size)
        );
    }

    public EventResponseDto updateEventByAdmin(Long eventId, EventUpdateDto dto) {
        return mapper.buildDto(service.updateEventByAdmin(eventId, dto));
    }

    public EventResponseDto updateEventByUser(Long userId, Long eventId, EventUpdateDto dto) {
        return mapper.buildDto(service.updateEventByUser(userId, eventId, dto));
    }

    public List<EventResponseShortDto> getEvents(String text,
                                                 List<Long> categories,
                                                 Boolean paid,
                                                 LocalDateTime rangeStart,
                                                 LocalDateTime rangeEnd,
                                                 Boolean onlyAvailable,
                                                 String sort,
                                                 int from,
                                                 int size) {
        return mapper.buildShortDtoList(
            service.findPublicEvents(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size)
        );
    }

    public EventResponseDto findByIdAndState(Long id, State state) {
        return mapper.buildDto(service.findByIdAndState(id, state));
    }

    public List<EventResponseShortDto> getPublicEvents(String text,
                                                       List<Long> categories,
                                                       Boolean paid,
                                                       LocalDateTime rangeStart,
                                                       LocalDateTime rangeEnd,
                                                       Boolean onlyAvailable,
                                                       String sort,
                                                       int from,
                                                       int size,
                                                       HttpServletRequest request) {

        LocalDateTime now = LocalDateTime.now();

        statsClient.hit(EndpointHitRequestDto.builder()
            .app(appName)
            .uri(request.getRequestURI())
            .ip(extractClientIp(request))
            .timestamp(now.format(FORMATTER))
            .build());

        List<EventResponseShortDto> events = getEvents(
            text, categories, paid, rangeStart, rangeEnd,
            onlyAvailable, sort, from, size
        );

        if (events.isEmpty()) {
            return events;
        }

        List<String> uris = events.stream()
            .map(e -> "/events/" + e.getId())
            .toList();

        List<ViewStatsResponseDto> stats = statsClient.getStats(
            LocalDateTime.of(2000, 1, 1, 0, 0),
            now.plusMinutes(1),
            uris,
            true
        );

        Map<String, Long> viewsMap = stats.stream()
            .collect(Collectors.toMap(ViewStatsResponseDto::getUri, ViewStatsResponseDto::getHits));

        events.forEach(e -> {
            String uri = "/events/" + e.getId();
            e.setViews(viewsMap.getOrDefault(uri, 0L));
        });

        return events;
    }

    public EventResponseDto getPublicEventById(Long id, HttpServletRequest request) {
        LocalDateTime now = LocalDateTime.now();
        String uri = request.getRequestURI();
        String ip = extractClientIp(request);

        statsClient.hit(EndpointHitRequestDto.builder()
            .app(appName)
            .uri(uri)
            .ip(ip)
            .timestamp(now.format(FORMATTER))
            .build());

        EventResponseDto dto = findByIdAndState(id, State.PUBLISHED);

        List<ViewStatsResponseDto> stats = statsClient.getStats(
            LocalDateTime.of(2000, 1, 1, 0, 0),
            now.plusMinutes(1),
            List.of(uri),
            true
        );

        long views = stats.isEmpty() ? 0L : stats.getFirst().getHits();
        dto.setViews(views);

        return dto;
    }

    private String extractClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            int commaIndex = forwarded.indexOf(',');
            return commaIndex > 0 ? forwarded.substring(0, commaIndex).trim() : forwarded.trim();
        }
        return request.getRemoteAddr();
    }
}
