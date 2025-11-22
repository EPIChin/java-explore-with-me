package ru.practicum.main.controller.privateC;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.request.EventRequestDto;
import ru.practicum.main.dto.request.EventRequestStatusUpdateRequestDto;
import ru.practicum.main.dto.response.EventRequestResponseDto;
import ru.practicum.main.dto.response.EventRequestStatusUpdateResponseDto;
import ru.practicum.main.dto.response.EventResponseDto;
import ru.practicum.main.dto.update.EventUpdateDto;
import ru.practicum.main.facade.EventFacade;
import ru.practicum.main.facade.EventRequestFacade;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {
    private final EventFacade facade;
    private final EventRequestFacade eventRequestFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponseDto create(@PathVariable Long userId,
                                   @Valid @RequestBody EventRequestDto requestDto) {
        return facade.create(userId, requestDto);
    }

    @GetMapping
    public List<EventResponseDto> findAllWithLimit(@PathVariable Long userId,
                                                   @RequestParam(defaultValue = "0") Integer from,
                                                   @RequestParam(defaultValue = "10") Integer size) {
        return facade.findAllWithLimit(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventResponseDto findByUserIdAndEventId(@PathVariable Long userId,
                                                   @PathVariable Long eventId) {
        return facade.findByUserIdAndEventId(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventResponseDto update(@PathVariable(name = "userId") Long userId,
                                   @PathVariable(name = "eventId") Long eventId,
                                   @Valid @RequestBody EventUpdateDto dto) {
        return facade.updateEventByUser(userId, eventId, dto);
    }

    @GetMapping("/{eventId}/requests")
    public List<EventRequestResponseDto> findByInitiatorIdAndEventId(@PathVariable(name = "userId") Long userId,
                                                                     @PathVariable(name = "eventId") Long eventId) {
        return eventRequestFacade.findByInitiatorIdAndEventId(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResponseDto responseToRequests(@PathVariable("userId") Long userId,
                                                                  @PathVariable("eventId") Long eventId,
                                                                  @RequestBody EventRequestStatusUpdateRequestDto requestDto) {
        return eventRequestFacade.responseToRequests(requestDto.getRequestIds(), requestDto.getStatus(), eventId, userId);
    }
}
