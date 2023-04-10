package ru.practicum.main.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.model.comment.model.CommentHistory;

import java.util.List;

public interface CommentHistoryRepository extends JpaRepository<CommentHistory, Long> {
    List<CommentHistory> findAllByCommentIdIs(Long commentId);
}
