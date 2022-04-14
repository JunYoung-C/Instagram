package toyproject.instragram.reply.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.instragram.reply.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Long countRepliesByCommentId(Long commentId);

    @EntityGraph(attributePaths = {"member"})
    Slice<Reply> getRepliesByCommentIdOrderByCreatedDateDesc(Long commentId, Pageable pageable);
}
