package ru.practicum.main.model.comment;

import org.mapstruct.Mapper;
import ru.practicum.main.model.comment.dto.CommentDto;
import ru.practicum.main.model.comment.dto.CommentShortDto;
import ru.practicum.main.model.comment.model.Comment;
import ru.practicum.main.model.event.model.Event;
import ru.practicum.main.model.user.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentShortDto toShortDto(Comment comment);

    CommentDto toDto(Comment comment);

    default String map(User author) {
        return author.getName();
    }

    default Long map(Event event) {
        return event.getId();
    }

    default Integer map(List<Comment> comments) {
        return comments == null ? 0 : comments.size();
    }
}
