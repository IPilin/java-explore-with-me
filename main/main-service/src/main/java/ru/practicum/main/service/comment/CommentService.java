package ru.practicum.main.service.comment;

import ru.practicum.main.model.comment.Comment;
import ru.practicum.main.model.comment.NewCommentDto;

import java.util.Collection;

public interface CommentService {
    Comment create(Long userId, Long eventId, NewCommentDto newCommentDto);

    Comment update(Long userId, Long commentId, NewCommentDto newCommentDto);

    Collection<Comment> getAllByUser(Long userId, Integer from, Integer size);

    Collection<Comment> getAllByEvent(Long eventId, Integer from, Integer size);
}
