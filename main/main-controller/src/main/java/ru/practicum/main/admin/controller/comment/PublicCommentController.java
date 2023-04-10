package ru.practicum.main.admin.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.model.comment.CommentListMapper;
import ru.practicum.main.service.comment.CommentService;

import java.util.Collection;

@RestController
@RequestMapping("/events/{eventId}/comments")
@RequiredArgsConstructor
public class PublicCommentController {
    private final CommentService service;
    private final CommentListMapper listMapper;

    @GetMapping
    public Collection<?> getAll(@PathVariable Long eventId,
                                @RequestParam(defaultValue = "false") Boolean withChild,
                                @RequestParam(defaultValue = "0") Integer from,
                                @RequestParam(defaultValue = "10") Integer size) {
        return withChild ?
                listMapper.toDto(service.getAllByEvent(eventId, true, from, size)) :
                listMapper.toShortDto(service.getAllByEvent(eventId, false, from, size));
    }
}
