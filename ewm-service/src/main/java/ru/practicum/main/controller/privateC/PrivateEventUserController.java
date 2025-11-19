package ru.practicum.main.controller.privateC;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.response.EventRequestResponseDto;
import ru.practicum.main.facade.EventRequestFacade;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateEventUserController {
    private final EventRequestFacade facade;

    @GetMapping
    public List<EventRequestResponseDto> findAllByUserId(@PathVariable(name = "userId") Long userId) {
        return facade.findAllByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventRequestResponseDto create(@PathVariable(name = "userId") Long userId,
                                          @RequestParam(name = "eventId") Long eventId) {
        return facade.create(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestResponseDto cancel(@PathVariable(name = "userId") Long userId,
                                          @PathVariable(name = "requestId") Long requestId) {
        return facade.cancel(userId, requestId);
    }
}
