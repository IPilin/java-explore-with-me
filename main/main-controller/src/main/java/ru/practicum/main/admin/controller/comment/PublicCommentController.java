package ru.practicum.main.admin.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.model.comment.Comment;
import ru.practicum.main.service.comment.CommentService;

import java.util.Collection;

@RestController
@RequestMapping("/events/{eventId}/comments")
@RequiredArgsConstructor
public class PublicCommentController {
    private final CommentService service;

    @GetMapping
    public Collection<Comment> getAll(@PathVariable Long eventId,
                                      @RequestParam(defaultValue = "0") Integer from,
                                      @RequestParam(defaultValue = "10") Integer size) {
        return service.getAllByEvent(eventId, from, size);
    }
}
