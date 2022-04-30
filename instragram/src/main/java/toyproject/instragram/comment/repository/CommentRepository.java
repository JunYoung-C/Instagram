package toyproject.instragram.comment.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.instragram.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Long countCommentsByPostId(Long postId);

    @EntityGraph(attributePaths = {"member"})
    Slice<Comment> getCommentsByPostIdOrderByIdDesc(Long postId, Pageable pageable);
}
