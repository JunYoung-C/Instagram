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
    public Long addComment(Long postId, Long memberId, String text) {
        Comment comment = new Comment(
                postRepository.findById(postId).orElse(null),
                memberRepository.findById(memberId).orElse(null),
                text);
        commentRepository.save(comment);
        return comment.getId();
    }

    public Slice<Comment> getCommentSlice(Long postId, int page) {
        return commentRepository
                .getCommentsByPostIdOrderByCreatedDateDesc(postId, PageRequest.of(page, MAX_COMMENT_SIZE));
    }

//    private Long getReplyCount(Comment comment) {
//        return replyRepository.countRepliesByCommentId(comment.getId());
//    }

    @Transactional
    public void modifyCommentText(Long commentId, String modifiedText) {
        Comment findComment = commentRepository.getById(commentId);
        findComment.changeText(modifiedText);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    // TODO 테스트 코드 작성
    public List<Comment> getOwnerComments(Long memberId, Long postId) {
        return commentRepository
                .getCommentsByMemberIdAndPostId(memberId, postId, PageRequest.of(0, MAX_OWNER_COMMENT_SIZE))
                .getContent();
    }

    // TODO 테스트 코드 작성
    public Long getCommentCount(Long postId) {
        return commentRepository.countCommentsByPostId(postId);
    }
}
