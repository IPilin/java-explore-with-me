package ru.practicum.main.admin.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.model.comment.dto.CommentHistoryDto;
import ru.practicum.main.service.comment.CommentService;

@RestController
@RequestMapping("/admin/comments")
@RequiredArgsConstructor
public class AdminCommentRepository {
    private final CommentService service;

    @GetMapping("/{commentId}/history")
    public CommentHistoryDto getHistory(@PathVariable Long commentId) {
        return service.getHistory(commentId);
    }
}
