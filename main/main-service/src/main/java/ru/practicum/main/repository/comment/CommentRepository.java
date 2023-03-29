package ru.practicum.main.repository.comment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.model.comment.Comment;
import ru.practicum.main.model.event.model.Event;
import ru.practicum.main.model.user.User;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByAuthorIs(User author, Pageable pageable);

    @EntityGraph(attributePaths = { "comments" })
    List<Comment> findAllByEventIsAndParentIdIsNull(Event event, Pageable pageable);

    Optional<Comment> findByIdAndAuthorIs(Long commentId, User author);
}
