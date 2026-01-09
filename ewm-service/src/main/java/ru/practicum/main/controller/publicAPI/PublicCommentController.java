package ru.practicum.main.controller.publicAPI;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.response.CommentResponseDto;
import ru.practicum.main.facade.CommentFacade;

import java.util.List;

@RestController
@RequestMapping("/events/{eventId}/comments")
@RequiredArgsConstructor
public class PublicCommentController {

    private final CommentFacade facade;

    @GetMapping
    public List<CommentResponseDto> getComments(@PathVariable Long eventId,
                                                @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                @RequestParam(defaultValue = "10") @Positive int size) {
        return facade.getEventComments(eventId, from, size);
    }
}
