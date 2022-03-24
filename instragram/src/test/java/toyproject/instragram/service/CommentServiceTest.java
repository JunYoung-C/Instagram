package toyproject.instragram.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.dto.CommentDto;
import toyproject.instragram.dto.MemberDto;
import toyproject.instragram.entity.*;
import toyproject.instragram.repository.CommentRepository;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
        Member member = new Member(null, new Profile("myNickname1", "myName1", null));
        em.persist(member);

        Post post = new Post(member, null);
        em.persist(post);

        Comment comment = new Comment(post, member, null);

        //when
        Long commentId = commentService.addComment(comment);

        //then
        Comment findComment = commentRepository.findById(commentId).orElse(null);
        assertThat(findComment).isEqualTo(comment);
    }

    @DisplayName("댓글 조회")
    @Test
    void getCommentDtoSlice() {
        //given
        Member member1 = new Member(null, new Profile("myNickname1", "myName1", null));
        Member member2 = new Member(null, new Profile("myNickname2", "myName2", null));
        em.persist(member1);
        em.persist(member2);

        Post post = new Post(member1, null);
        em.persist(post);

        Comment comment1 = new Comment(post, member1, null);
        Comment comment2 = new Comment(post, member2, null);
        post.addComment(comment1);
        post.addComment(comment2);
        em.persist(comment1);
        em.persist(comment2);

        em.flush();
        em.clear();

        //when
        Slice<CommentDto> commentDtoSlice = commentService.getCommentDtoSlice(post.getId(), PageRequest.of(0, 20));

        //then
        assertThat(commentDtoSlice.getContent()).hasSize(2);
        assertThat(commentDtoSlice.isFirst()).isTrue();
        assertThat(commentDtoSlice.isLast()).isTrue();
    }

    @DisplayName("댓글 내용 수정")
    @Test
    void modifyCommentText() {
        //given
        Member member = new Member(null, new Profile("myNickname1", "myName1", null));
        em.persist(member);

        Post post = new Post(member, null);
        em.persist(post);

        Comment comment = new Comment(post, member, "안녕하세요");
        Long commentId = commentService.addComment(comment);

        em.flush();
        em.clear();

        //when
        String text = "수정했어요";
        commentService.modifyCommentText(commentId, text);

        em.flush();
        em.clear();

        //then
        Comment findComment = em.find(Comment.class, commentId);
        assertThat(findComment.getText()).isEqualTo(text);
    }

    @DisplayName("댓글 삭제")
    @Test
    void deleteComment() {
        //given
        Member member = new Member(null, new Profile("myNickname1", "myName1", null));
        em.persist(member);

        Post post = new Post(member, null);
        em.persist(post);

        Comment comment = new Comment(post, member, "안녕하세요");
        Long commentId = commentService.addComment(comment);

        em.flush();
        em.clear();

        //when
        commentService.deleteComment(commentId);

        //then
        Comment findComment = em.find(Comment.class, commentId);
        assertThat(findComment).isNull();
    }
}