package toyproject.instragram.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.instragram.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Long countCommentsByPostId(Long postId);

    Slice<Comment> getCommentsByMemberIdAndPostId(Long memberId, Long postId, Pageable pageable);
}
