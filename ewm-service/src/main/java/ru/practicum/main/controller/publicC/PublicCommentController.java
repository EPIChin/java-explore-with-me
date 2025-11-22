package ru.practicum.main.controller.publicC;

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
                                                @RequestParam(defaultValue = "0") int from,
                                                @RequestParam(defaultValue = "10") int size) {
        return facade.getEventComments(eventId, from, size);
    }
}
