package toyproject.instragram.reply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.comment.entity.Comment;
import toyproject.instragram.reply.entity.Reply;
import toyproject.instragram.comment.repository.CommentRepository;
import toyproject.instragram.member.repository.MemberRepository;
import toyproject.instragram.reply.repository.ReplyRepository;

import static toyproject.instragram.common.exception.api.ApiExceptionType.NOT_FOUND_COMMENT;
import static toyproject.instragram.common.exception.api.ApiExceptionType.NOT_FOUND_REPLY;

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

    public Slice<Reply> getReplySlice(Long commentId, Pageable pageable) {
        return replyRepository
                .getRepliesByCommentIdOrderByCreatedDateDesc(commentId, pageable);
    }

    @Transactional
    public void deleteReply(Long replyId) {
        if (!replyRepository.existsById(replyId)) {
            throw NOT_FOUND_REPLY.getException();
        }

        replyRepository.deleteById(replyId);
    }

    // TODO 테스트코드 작성
    public Long getReplyCount(Long commentId) {
        return replyRepository.countRepliesByCommentId(commentId);
    }

    // TODO 테스트 코드 작성
    public Reply getReply(Long replyId) {
        return replyRepository.findById(replyId)
                .orElseThrow(NOT_FOUND_REPLY::getException);
    }
}
