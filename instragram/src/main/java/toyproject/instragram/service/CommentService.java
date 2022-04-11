package toyproject.instragram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.entity.Comment;
import toyproject.instragram.repository.CommentRepository;
import toyproject.instragram.repository.MemberRepository;
import toyproject.instragram.repository.PostRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    private final int MAX_COMMENT_SIZE = 20;
    private final int MAX_OWNER_COMMENT_SIZE = 3;

    @Transactional
    public Long addComment(CommentDto commentDto) {
        Comment comment = new Comment(
                postRepository.findById(commentDto.getPostId()).orElse(null),
                memberRepository.findById(commentDto.getMemberId()).orElse(null),
                commentDto.getText());
        commentRepository.save(comment);
        return comment.getId();
    }

    public Slice<Comment> getCommentSlice(Long postId, int page) {
        return commentRepository
                .getCommentsByPostIdOrderByCreatedDateDesc(postId, PageRequest.of(page, MAX_COMMENT_SIZE));
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    // TODO 테스트 코드 작성
    public Long getCommentCount(Long postId) {
        return commentRepository.countCommentsByPostId(postId);
    }
}
