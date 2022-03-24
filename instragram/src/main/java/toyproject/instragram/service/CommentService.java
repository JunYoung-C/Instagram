package toyproject.instragram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.dto.CommentDto;
import toyproject.instragram.entity.Comment;
import toyproject.instragram.repository.CommentRepository;
import toyproject.instragram.repository.ReplyRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public Long addComment(Comment comment) {
        // TODO Comment 파라미터를 DTO로 변경 필요. post id가 포함되는지 그때 가서 생각
        commentRepository.save(comment);
        return comment.getId();
    }

    public Slice<CommentDto> getCommentDtoSlice(Long postId, Pageable pageable) {
        Slice<Comment> commentSlice = commentRepository.getCommentsByPostIdOrderByCreatedDateDesc(postId, pageable);
        return commentSlice.map(comment -> new CommentDto(comment, getReplyCount(comment)));
    }

    private Long getReplyCount(Comment comment) {
        return replyRepository.countRepliesByCommentId(comment.getId());
    }

    @Transactional
    public void modifyCommentText(Long commentId, String text) {
        Comment findComment = commentRepository.getById(commentId);
        findComment.changeText(text);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
