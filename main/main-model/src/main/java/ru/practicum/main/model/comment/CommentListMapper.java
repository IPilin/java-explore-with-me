package ru.practicum.main.model.comment;

import org.mapstruct.Mapper;
import ru.practicum.main.model.comment.dto.CommentDto;
import ru.practicum.main.model.comment.dto.CommentShortDto;
import ru.practicum.main.model.comment.model.Comment;

import java.util.Collection;

@Mapper(componentModel = "spring", uses = CommentMapper.class)
public interface CommentListMapper {
    Collection<CommentShortDto> toShortDto(Collection<Comment> comments);

    Collection<CommentDto> toDto(Collection<Comment> comments);
}
