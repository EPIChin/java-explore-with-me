package ru.practicum.main.controller.adminC;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.response.CommentResponseDto;
import ru.practicum.main.facade.CommentFacade;

import java.util.List;

@RestController
@RequestMapping("/admin/comments")
@RequiredArgsConstructor
public class AdminCommentController {

    private final CommentFacade facade;

    @GetMapping("/{commentId}")
    public CommentResponseDto getComment(@PathVariable Long commentId) {
        return facade.getCommentById(commentId);
    }

    @GetMapping
    public List<CommentResponseDto> searchComments(@RequestParam(required = false) Long userId,
                                                   @RequestParam(required = false) Long eventId,
                                                   @RequestParam(defaultValue = "0") int from,
                                                   @RequestParam(defaultValue = "10") int size) {
        return facade.searchComments(userId, eventId, from, size);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long commentId) {
        facade.deleteCommentByAdmin(commentId);
    }
}
