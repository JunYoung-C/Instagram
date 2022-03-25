package toyproject.instragram.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.instragram.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Long countCommentsByPostId(Long postId);

    @EntityGraph(attributePaths = {"member"})
    Slice<Comment> getCommentsByMemberIdAndPostId(Long memberId, Long postId, Pageable pageable);

    @EntityGraph(attributePaths = {"member"})
    Slice<Comment> getCommentsByPostIdOrderByCreatedDateDesc(Long postId, Pageable pageable);
}
