package ru.practicum.main.model.comment.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comments_history")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "comment_id")
    Long commentId;
    @Column(name = "modified_at")
    LocalDateTime modifiedAt;
    @Column(name = "old_content")
    String oldContent;
    @Column(name = "new_content")
    String newContent;
    @Column(name = "old_status")
    @Enumerated(EnumType.STRING)
    CommentStatus oldStatus;
    @Column(name = "new_status")
    @Enumerated(EnumType.STRING)
    CommentStatus newStatus;
}
