package ru.practicum.main.controller.privateC;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.request.CommentRequestDto;
import ru.practicum.main.dto.response.CommentResponseDto;
import ru.practicum.main.facade.CommentFacade;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}")
@RequiredArgsConstructor
public class PrivateCommentController {

    private final CommentFacade facade;

    @PostMapping("/events/{eventId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto addComment(@PathVariable Long userId,
                                         @PathVariable Long eventId,
                                         @Valid @RequestBody CommentRequestDto dto) {
        return facade.addComment(userId, eventId, dto);
    }

    @PatchMapping("/comments/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long userId,
                                            @PathVariable Long commentId,
                                            @Valid @RequestBody CommentRequestDto dto) {
        return facade.updateComment(userId, commentId, dto);
    }

    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long userId,
                              @PathVariable Long commentId) {
        facade.deleteComment(userId, commentId);
    }

    @GetMapping("/comments")
    public List<CommentResponseDto> getUserComments(@PathVariable Long userId,
                                                    @RequestParam(defaultValue = "0") int from,
                                                    @RequestParam(defaultValue = "10") int size) {
        return facade.getUserComments(userId, from, size);
    }
}
