package toyproject.instragram.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.comment.entity.Comment;
import toyproject.instragram.comment.repository.CommentRepository;
import toyproject.instragram.member.repository.MemberRepository;
import toyproject.instragram.post.repository.PostRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    private final int MAX_COMMENT_SIZE = 20;

    @Transactional
    public Long addComment(CommentDto commentDto) {
        Comment comment = new Comment(
                postRepository.findById(commentDto.getPostId()).orElse(null),
                memberRepository.findById(commentDto.getMemberId()).orElse(null),
                commentDto.getText());
        commentRepository.save(comment);
        return comment.getId();
    }

    public Slice<Comment> getCommentSlice(Long postId, Pageable pageable) {
        return commentRepository
                .getCommentsByPostIdOrderByCreatedDateDesc(postId, pageable);
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
