package ru.practicum.main.model.comment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {
    Long id;
    String content;
    String authorName;
    Long eventId;
    List<Comment> comments;
    Long parentId;
    CommentStatus commentStatus;
    Integer commentsCount;

    public CommentDto(Comment comment, Integer commentsCount) {
        this.id = comment.getId();
        this.commentsCount = commentsCount;
    }
}
