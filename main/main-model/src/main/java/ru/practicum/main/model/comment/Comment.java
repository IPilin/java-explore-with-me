package ru.practicum.main.model.comment;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.main.model.event.model.Event;
import ru.practicum.main.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "comments")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String content;

    @Column(nullable = false)
    LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    User author;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    Event event;

    @OneToMany(mappedBy = "parentId", orphanRemoval = true)
    List<Comment> comments;

    @Column(name = "parent_id")
    Long parentId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    CommentStatus status;

    public Comment(Comment comment, Integer count) {

    }
}
