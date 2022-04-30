package toyproject.instragram.reply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.comment.entity.Comment;
import toyproject.instragram.member.entity.Member;
import toyproject.instragram.reply.entity.Reply;
import toyproject.instragram.comment.repository.CommentRepository;
import toyproject.instragram.member.repository.MemberRepository;
import toyproject.instragram.reply.repository.ReplyRepository;

import static toyproject.instragram.common.exception.api.ApiExceptionType.*;

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
                getValidatedComment(replyDto.getCommentId()),
                getValidatedMember(replyDto.getMemberId()),
                replyDto.getText());
        replyRepository.save(reply);
        return reply.getId();
    }

    private Member getValidatedMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(EXPIRED_SESSION::getException);
    }

    private Comment getValidatedComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(NOT_FOUND_COMMENT::getException);
    }

    public Slice<Reply> getReplySlice(Long commentId, Pageable pageable) {
        return replyRepository.getRepliesByCommentId(commentId, pageable);
    }

    @Transactional
    public void deleteReply(Long replyId) {
        if (!replyRepository.existsById(replyId)) {
            throw NOT_FOUND_REPLY.getException();
        }

        replyRepository.deleteById(replyId);
    }

    public Long getReplyCount(Long commentId) {
        return replyRepository.countRepliesByCommentId(commentId);
    }

    public Reply getReply(Long replyId) {
        return replyRepository.findById(replyId)
                .orElseThrow(NOT_FOUND_REPLY::getException);
    }
}
