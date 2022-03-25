package toyproject.instragram.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.instragram.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Long countRepliesByCommentId(Long commentId);

    Slice<Reply> getRepliesByCommentIdOrderByCreatedDateDesc(Long commentId, Pageable pageable);
}
