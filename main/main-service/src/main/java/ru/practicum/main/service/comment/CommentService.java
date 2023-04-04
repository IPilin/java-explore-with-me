package ru.practicum.main.service.comment;

import ru.practicum.main.model.comment.dto.CommentHistoryDto;
import ru.practicum.main.model.comment.dto.NewCommentDto;
import ru.practicum.main.model.comment.model.Comment;

import java.util.Collection;

public interface CommentService {
    Comment create(Long userId, Long eventId, NewCommentDto newCommentDto);

    Comment update(Long userId, Long commentId, NewCommentDto newCommentDto);

    void remove(Long userId, Long commentId);

    Collection<Comment> getAllByUser(Long userId, Integer from, Integer size);

    Collection<Comment> getAllByEvent(Long eventId, Boolean withChild, Integer from, Integer size);

    CommentHistoryDto getHistory(Long commentId);
}
