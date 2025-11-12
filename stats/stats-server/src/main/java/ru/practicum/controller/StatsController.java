package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.EndpointStats;
import ru.practicum.dto.RequestParamDto;
import ru.practicum.exceptions.BadRequestException;
import ru.practicum.service.StatsService;
import jakarta.validation.Valid;

import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;


@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequest(BadRequestException ex) {
        return Map.of("error", ex.getMessage());
    }

    @PostMapping("/hit")
    public ResponseEntity<String> postHit(@Valid @RequestBody EndpointHitDto hitDto) {
        log.info("Statistic service: сохранен запрос для эндпоинта {}", hitDto.getUri());
        statsService.saveHit(hitDto);
        return new ResponseEntity<>("Информация сохранена", HttpStatus.CREATED);
    }

    @GetMapping("/stats")
    public List<EndpointStats> getStats(@RequestParam(name = "start") String start,
                                        @RequestParam(name = "end") String end,
                                        @RequestParam(name = "uris", required = false) String[] uris,
                                        @RequestParam(name = "unique", defaultValue = "false") boolean unique) throws org.apache.coyote.BadRequestException {
        log.info("Statistic service: запрошена статистика для эндпоинтов {}", (Object) uris);
        RequestParamDto requestDto = new RequestParamDto(start, end, uris, unique);
        return statsService.getStats(requestDto);
    }
}