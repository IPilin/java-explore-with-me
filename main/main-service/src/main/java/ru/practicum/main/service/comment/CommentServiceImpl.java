package ru.practicum.main.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.model.comment.CommentMapper;
import ru.practicum.main.model.comment.dto.CommentHistoryDto;
import ru.practicum.main.model.comment.dto.NewCommentDto;
import ru.practicum.main.model.comment.model.Comment;
import ru.practicum.main.model.comment.model.CommentStatus;
import ru.practicum.main.model.event.model.EventState;
import ru.practicum.main.model.exception.ForbiddenException;
import ru.practicum.main.model.exception.NotFoundException;
import ru.practicum.main.repository.comment.CommentHistoryRepository;
import ru.practicum.main.repository.comment.CommentRepository;
import ru.practicum.main.service.event.EventService;
import ru.practicum.main.service.user.UserService;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;
    private final CommentHistoryRepository historyRepository;
    private final UserService userService;
    private final EventService eventService;
    private final CommentMapper mapper;

    @Transactional
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

    @Transactional
    @Override
    public Comment update(Long userId, Long commentId, NewCommentDto newCommentDto) {
        var user = userService.get(userId);
        var comment = repository.findByIdAndAuthorIs(commentId, user)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id=%d not found.", commentId)));
        comment.setContent(newCommentDto.getContent());
        comment.setStatus(CommentStatus.CHANGED);
        return repository.save(comment);
    }

    @Transactional
    @Override
    public void remove(Long userId, Long commentId) {
        var user = userService.get(userId);
        var comment = repository.findByIdAndAuthorIs(commentId, user)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id=%d not found.", commentId)));
        comment.setStatus(CommentStatus.DELETED);
        comment.setContent("");
        repository.save(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Comment> getAllByUser(Long userId, Integer from, Integer size) {
        var user = userService.get(userId);
        return repository.findAllByAuthorIs(user, PageRequest.of(from / size, size));
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Comment> getAllByEvent(Long eventId, Boolean withChild, Integer from, Integer size) {
        var event = eventService.find(eventId);
        if (event.getState() != EventState.PUBLISHED) {
            throw new ForbiddenException("Event isn't public.");
        }
        return withChild ?
                repository.findAllByEventIsAndParentIdIsNull(event, PageRequest.of(from / size, size)) :
                repository.findAllByParentIdIsNullAndEventIs(event, PageRequest.of(from / size, size));
    }

    @Transactional(readOnly = true)
    @Override
    public CommentHistoryDto getHistory(Long commentId) {
        var comment = repository.findById(commentId)
                        .orElseThrow(() -> new NotFoundException(String.format("Comment with id=%d not found.", commentId)));
        var history = historyRepository.findAllByCommentIdIs(commentId);

        return new CommentHistoryDto(mapper.toShortDto(comment), history);
    }
}
