package ru.practicum.statsserver.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statsdto.request.EndpointHitRequestDto;
import ru.practicum.statsdto.response.ViewStatsResponseDto;
import ru.practicum.statsserver.service.EndpointHitService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatsController {
    private final EndpointHitService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void hit(@RequestBody @Valid EndpointHitRequestDto dto) {
        service.hit(dto);
    }

    @GetMapping("/stats")
    public List<ViewStatsResponseDto> stats(@RequestParam @NotBlank String start,
                                            @RequestParam @NotBlank String end,
                                            @RequestParam(required = false) List<String> uris,
                                            @RequestParam(defaultValue = "false") boolean unique) {
        return service.getStats(start, end, uris, unique);
    }
}

