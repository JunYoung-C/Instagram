package toyproject.instragram.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.entity.Comment;
import toyproject.instragram.entity.Member;
import toyproject.instragram.entity.Post;
import toyproject.instragram.repository.CommentRepository;

import javax.persistence.EntityManager;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    EntityManager em;

    @DisplayName("댓글 등록")
    @Test
    void addComment() {
        //given
        Member member =  new Member(null, "nickname", "이름");
        em.persist(member);

        Post post = new Post(member, null);
        em.persist(post);

        //when
        Long commentId = commentService.addComment(new CommentDto(post.getId(), member.getId(), "안녕하세요"));

        //then
        Comment findComment = commentRepository.findById(commentId).orElse(null);
        assertThat(findComment).isNotNull();
    }

    @DisplayName("댓글 최대 20건 조회")
    @Test
    void getCommentSlice() {
        //given
        Member member = new Member(null, "nickname", "이름");
        em.persist(member);

        Post post = new Post(member, null);
        em.persist(post);

        IntStream.range(0, 21)
                .forEach(i -> commentService.addComment(new CommentDto(post.getId(), member.getId(), "안녕하세요" + i)));

        em.flush();
        em.clear();

        //when
        int page = 0;
        Slice<Comment> commentSlice = commentService.getCommentSlice(post.getId(), page);

        //then
        assertThat(commentSlice.getContent()).hasSize(20);
    }

    @DisplayName("댓글 내용 수정")
    @Test
    void modifyCommentText() {
        //given
        Member member =  new Member(null, "nickname", "이름");
        em.persist(member);

        Post post = new Post(member, null);
        em.persist(post);

        Long commentId = commentService.addComment(new CommentDto(post.getId(), member.getId(), "안녕하세요"));

        em.flush();
        em.clear();

        //when
        String modifiedText = "수정했어요";
        commentService.modifyCommentText(commentId, modifiedText);

        //then
        Comment findComment = em.find(Comment.class, commentId);
        assertThat(findComment.getText()).isEqualTo(modifiedText);
    }

    @DisplayName("댓글 삭제")
    @Test
    void deleteComment() {
        //given
        Member member =  new Member(null, "nickname", "이름");
        em.persist(member);

        Post post = new Post(member, null);
        em.persist(post);

        Long commentId = commentService.addComment(new CommentDto(post.getId(), member.getId(), "안녕하세요"));

        em.flush();
        em.clear();

        //when
        commentService.deleteComment(commentId);

        //then
        Comment findComment = commentRepository.findById(commentId).orElse(null);
        assertThat(findComment).isNull();
    }
}