package toyproject.instragram.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import toyproject.instragram.AppConfig;
import toyproject.instragram.entity.*;

import javax.persistence.EntityManager;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Import(value = AppConfig.class)
@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    EntityManager em;

    @DisplayName("게시물에 달린 모든 댓글 개수 확인")
    @Test
    void countCommentsByPostId() {
        //given
        Member member1 = new Member(null, new Profile("myNickname1", "myName1", null));
        Member member2 = new Member(null, new Profile("myNickname2", "myName2", null));
        em.persist(member1);
        em.persist(member2);

        Post post = new Post(member1, null);
        em.persist(post);

        Comment comment1 = new Comment(post, member1, null);
        Comment comment2 = new Comment(post, member2, null);
        Comment comment3 = new Comment(post, member1, null);
        Comment comment4 = new Comment(post, member1, null);
        Comment comment5 = new Comment(post, member2, null);

        post.addComment(comment1);
        post.addComment(comment2);
        post.addComment(comment3);
        post.addComment(comment4);
        post.addComment(comment5);

        em.persist(comment1);
        em.persist(comment2);
        em.persist(comment3);
        em.persist(comment4);
        em.persist(comment5);

        em.flush();
        em.clear();

        //when
        Long commentCount = commentRepository.countCommentsByPostId(post.getId());

        //then
        assertThat(commentCount).isEqualTo(5);
    }

    @DisplayName("게시물을 올린 회원이 단 댓글 확인")
    @Test
    void getCommentsByMemberIdAndPostId() {
        //given
        Member member1 = new Member(null, new Profile("myNickname1", "myName1", null));
        Member member2 = new Member(null, new Profile("myNickname2", "myName2", null));
        em.persist(member1);
        em.persist(member2);

        Post post = new Post(member1, null);
        Comment comment1 = new Comment(post, member1, "1");
        Comment comment2 = new Comment(post, member2, "2");
        Comment comment3 = new Comment(post, member1, "3");
        Comment comment4 = new Comment(post, member1, "4");
        Comment comment5 = new Comment(post, member2, "5");

        post.addComment(comment1);
        post.addComment(comment2);
        post.addComment(comment3);
        post.addComment(comment4);
        post.addComment(comment5);

        em.persist(post);
        em.persist(comment1);
        em.persist(comment2);
        em.persist(comment3);
        em.persist(comment4);
        em.persist(comment5);

        em.flush();
        em.clear();

        //when

        Slice<Comment> commentSlice = commentRepository
                .getCommentsByMemberIdAndPostId(post.getMember().getId(), post.getId(), PageRequest.of(0, 3));

        //then
        List<Comment> comments = commentSlice.getContent();
        assertThat(comments).hasSize(3);
        assertThat(comments).extracting("text").containsExactly("1", "3", "4");

    }


    @DisplayName("생성 날짜가 빠른 순으로 20개의 댓글 조회")
    @Test
    void getCommentsByPostIdOrderByCreatedDateDesc() {
        //given
        Member member1 = new Member(null, new Profile("myNickname1", "myName1", null));
        Member member2 = new Member(null, new Profile("myNickname2", "myName2", null));
        em.persist(member1);
        em.persist(member2);

        Post post = new Post(member1, null);
        em.persist(post); // cascade.all

        IntStream.range(0, 65)
                .forEach(i -> {
                    Comment comment = new Comment(post, i % 2 == 0 ? member1 : member2, String.valueOf(i));
                    post.addComment(comment);
                    em.persist(comment);
                });


        em.flush();
        em.clear();
        //when
        Slice<Comment> commentFirstSlice = commentRepository
                .getCommentsByPostIdOrderByCreatedDateDesc(post.getId(), PageRequest.of(0, 20));
        Slice<Comment> commentLastSlice = commentRepository
                .getCommentsByPostIdOrderByCreatedDateDesc(post.getId(), PageRequest.of(3, 20));

        //then
        List<Comment> findFirstComments = commentFirstSlice.getContent();
        List<Comment> findLastComments = commentLastSlice.getContent();
        assertThat(findFirstComments).hasSize(20);
        assertThat(findLastComments).hasSize(5);
        assertThat(findFirstComments)
                .isSortedAccordingTo((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate()));
    }
}