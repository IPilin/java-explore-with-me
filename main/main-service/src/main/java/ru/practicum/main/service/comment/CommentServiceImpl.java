package ru.practicum.main.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.main.model.comment.Comment;
import ru.practicum.main.model.comment.CommentStatus;
import ru.practicum.main.model.comment.NewCommentDto;
import ru.practicum.main.model.event.model.EventState;
import ru.practicum.main.model.exception.ForbiddenException;
import ru.practicum.main.model.exception.NotFoundException;
import ru.practicum.main.repository.comment.CommentRepository;
import ru.practicum.main.service.event.EventService;
import ru.practicum.main.service.user.UserService;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;
    private final UserService userService;
    private final EventService eventService;

    @Override
    public Comment create(Long userId, Long eventId, NewCommentDto newCommentDto) {
        var user = userService.get(userId);
        var event = eventService.find(eventId);
        if (event.getState() != EventState.PUBLISHED) {
            throw new ForbiddenException("Event isn't public.");
        }

        var commentBuilder = Comment.builder()
                .author(user)
                .content(newCommentDto.getContent())
                .created(LocalDateTime.now())
                .status(CommentStatus.PUBLIC)
                .event(event);
        if (newCommentDto.getParentId() != null && repository.existsById(newCommentDto.getParentId())) {
            commentBuilder.parentId(newCommentDto.getParentId());
        }
        return repository.save(commentBuilder.build());
    }

    @Override
    public Comment update(Long userId, Long commentId, NewCommentDto newCommentDto) {
        var user = userService.get(userId);
        var comment = repository.findByIdAndAuthorIs(commentId, user)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id=%d not found.", commentId)));
        comment.setContent(newCommentDto.getContent());
        return repository.save(comment);
    }

    @Override
    public Collection<Comment> getAllByUser(Long userId, Integer from, Integer size) {
        var user = userService.get(userId);
        return repository.findAllByAuthorIs(user, PageRequest.of(from / size, size));
    }

    @Override
    public Collection<Comment> getAllByEvent(Long eventId, Integer from, Integer size) {
        var event = eventService.find(eventId);
        if (event.getState() != EventState.PUBLISHED) {
            throw new ForbiddenException("Event isn't public.");
        }
        return repository.findAllByEventIsAndParentIdIsNull(event, PageRequest.of(from / size, size));
    }
}
