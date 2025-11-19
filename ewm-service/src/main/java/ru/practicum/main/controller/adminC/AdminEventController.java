package ru.practicum.main.controller.adminC;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.common.State;
import ru.practicum.main.dto.response.EventResponseDto;
import ru.practicum.main.dto.update.EventUpdateDto;
import ru.practicum.main.facade.EventFacade;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class AdminEventController {
    private final EventFacade facade;

    @GetMapping
    public List<EventResponseDto> searchEvents(
        @RequestParam(required = false) List<Long> users,
        @RequestParam(required = false) List<State> states,
        @RequestParam(required = false) List<Long> categories,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
        @RequestParam(name = "from", defaultValue = "0") Integer from,
        @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return facade.searchEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventResponseDto update(@PathVariable(name = "eventId") Long eventId,
                                   @Valid @RequestBody EventUpdateDto dto) {
        return facade.updateEventByAdmin(eventId, dto);
    }
}
