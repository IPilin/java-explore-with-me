package ru.practicum.main.model.comment.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.main.model.comment.model.CommentStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {
    Long id;
    String content;
    LocalDateTime created;
    String author;
    Long event;
    List<CommentDto> comments;
    Long parentId;
    CommentStatus status;
}
