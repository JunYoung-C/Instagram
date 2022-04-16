package toyproject.instragram.reply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.reply.entity.Reply;
import toyproject.instragram.comment.repository.CommentRepository;
import toyproject.instragram.member.repository.MemberRepository;
import toyproject.instragram.reply.repository.ReplyRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public Long addReply(ReplyDto replyDto) {
        Reply reply = new Reply(
                commentRepository.findById(replyDto.getCommentId()).orElse(null),
                memberRepository.findById(replyDto.getMemberId()).orElse(null),
                replyDto.getText());

        replyRepository.save(reply);
        return reply.getId();
    }

    public Slice<Reply> getReplySlice(Long commentId,  Pageable pageable) {
        return replyRepository
                .getRepliesByCommentIdOrderByCreatedDateDesc(commentId, pageable);
    }

    @Transactional
    public void deleteReply(Long replyId) {
        replyRepository.deleteById(replyId);
    }

    // TODO 테스트코드 작성
    public Long getReplyCount(Long commentId) {
        return replyRepository.countRepliesByCommentId(commentId);
    }
}
