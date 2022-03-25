package toyproject.instragram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.dto.ReplyDto;
import toyproject.instragram.entity.Reply;
import toyproject.instragram.repository.ReplyRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Transactional
    public Long addReply(Reply reply) {
        // TODO Reply 파라미터를 DTO로 변경 필요. comment id가 포함되는지 그때 가서 생각
        replyRepository.save(reply);
        return reply.getId();
    }

    public Slice<ReplyDto> getReplyDtoSlice(Long commentId, Pageable pageable) {
        Slice<Reply> replySlice = replyRepository.getRepliesByCommentIdOrderByCreatedDateDesc(commentId, pageable);
        return replySlice.map(reply -> new ReplyDto(reply));
    }


    @Transactional
    public void modifyReplyText(Long replyId, String text) {
        Reply findReply = replyRepository.getById(replyId);
        findReply.changeText(text);
    }

    @Transactional
    public void deleteReply(Long replyId) {
        replyRepository.deleteById(replyId);
    }
}
