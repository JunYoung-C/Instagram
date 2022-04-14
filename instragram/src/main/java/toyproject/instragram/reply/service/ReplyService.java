package toyproject.instragram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.entity.Reply;
import toyproject.instragram.repository.CommentRepository;
import toyproject.instragram.repository.MemberRepository;
import toyproject.instragram.repository.ReplyRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    private final int MAX_REPLY_SIZE = 20;

    @Transactional
    public Long addReply(ReplyDto replyDto) {
        Reply reply = new Reply(
                commentRepository.findById(replyDto.getCommentId()).orElse(null),
                memberRepository.findById(replyDto.getMemberId()).orElse(null),
                replyDto.getText());

        replyRepository.save(reply);
        return reply.getId();
    }

    public Slice<Reply> getReplySlice(Long commentId, int page) {
        return replyRepository
                .getRepliesByCommentIdOrderByCreatedDateDesc(commentId, PageRequest.of(page, MAX_REPLY_SIZE));
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
