package ru.practicum.main.controller.publicC;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.response.EventResponseDto;
import ru.practicum.main.dto.response.EventResponseShortDto;
import ru.practicum.main.facade.EventFacade;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class PublicEventController {

    private final EventFacade facade;

    @GetMapping
    public List<EventResponseShortDto> getEvents(@RequestParam(required = false) String text,
                                                 @RequestParam(required = false) List<Long> categories,
                                                 @RequestParam(required = false) Boolean paid,
                                                 @RequestParam(required = false)
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                                 @RequestParam(required = false)
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                                 @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                                 @RequestParam(required = false) String sort,
                                                 @RequestParam(defaultValue = "0") Integer from,
                                                 @RequestParam(defaultValue = "10") Integer size,
                                                 HttpServletRequest request) {

        return facade.getPublicEvents(
            text, categories, paid, rangeStart, rangeEnd,
            onlyAvailable, sort, from, size, request
        );
    }

    @GetMapping("/{id}")
    public EventResponseDto findByIdAndState(@PathVariable Long id,
                                             HttpServletRequest request) {
        return facade.getPublicEventById(id, request);
    }
}
