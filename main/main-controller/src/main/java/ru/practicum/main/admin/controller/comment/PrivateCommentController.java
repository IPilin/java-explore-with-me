package ru.practicum.main.admin.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.model.comment.CommentMapper;
import ru.practicum.main.model.comment.dto.CommentShortDto;
import ru.practicum.main.model.comment.dto.NewCommentDto;
import ru.practicum.main.model.comment.model.Comment;
import ru.practicum.main.model.validation.OnCreate;
import ru.practicum.main.model.validation.OnUpdate;
import ru.practicum.main.service.comment.CommentService;

import java.util.Collection;

@RestController
@RequestMapping("/users/{userId}/comments")
@RequiredArgsConstructor
public class PrivateCommentController {
    private final CommentService service;
    private final CommentMapper mapper;

    @PostMapping("/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentShortDto create(@PathVariable Long userId,
                                  @PathVariable Long eventId,
                                  @RequestBody @Validated(OnCreate.class) NewCommentDto newCommentDto) {
        return mapper.toShortDto(service.create(userId, eventId, newCommentDto));
    }

    @GetMapping
    public Collection<Comment> getAll(@PathVariable Long userId,
                                      @RequestParam(defaultValue = "0") Integer from,
                                      @RequestParam(defaultValue = "10") Integer size) {
        return service.getAllByUser(userId, from, size);
    }

    @PatchMapping("/{commentId}")
    public CommentShortDto update(@PathVariable Long userId,
                          @PathVariable Long commentId,
                          @RequestBody @Validated(OnUpdate.class) NewCommentDto newCommentDto) {
        return mapper.toShortDto(service.update(userId, commentId, newCommentDto));
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId,
                       @PathVariable Long commentId) {
        service.remove(userId, commentId);
    }
}
