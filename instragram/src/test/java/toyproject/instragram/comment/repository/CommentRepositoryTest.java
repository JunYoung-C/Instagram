package toyproject.instragram.comment.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import toyproject.instragram.comment.repository.CommentRepository;
import toyproject.instragram.common.AppConfig;
import toyproject.instragram.comment.entity.Comment;
import toyproject.instragram.member.entity.Member;
import toyproject.instragram.post.entity.Post;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

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
        Member member1 = new Member(null, "nickname1", "이름1");
        Member member2 = new Member(null, "nickname2", "이름2");
        em.persist(member1);
        em.persist(member2);

        Post post = new Post(member1, null);
        em.persist(post);

        IntStream.range(0, 5)
                .forEach(i -> em.persist(new Comment(post, i % 2 == 0 ? member1 : member2, null)));

        em.flush();
        em.clear();

        //when
        Long commentCount = commentRepository.countCommentsByPostId(post.getId());

        //then
        assertThat(commentCount).isEqualTo(5);
    }

    @DisplayName("최근에 생성된 순서로 댓글 조회")
    @Test
    void getCommentsByPostIdOrderByCreatedDateDesc() {
        //given
        Member member1 = new Member(null, "nickname1", "이름1");
        Member member2 = new Member(null, "nickname2", "이름2");
        em.persist(member1);
        em.persist(member2);

        Post post = new Post(member1, null);
        em.persist(post);

        IntStream.range(0, 65)
                .forEach(i -> em.persist(new Comment(post, i % 2 == 0 ? member1 : member2, String.valueOf(i))));

        em.flush();
        em.clear();

        //when
        Slice<Comment> firstCommentSlice = commentRepository
                .getCommentsByPostIdOrderByIdDesc(post.getId(), PageRequest.of(0, 20));
        Slice<Comment> lastCommentSlice = commentRepository
                .getCommentsByPostIdOrderByIdDesc(post.getId(), PageRequest.of(3, 20));

        //then
        List<Comment> findFirstComments = firstCommentSlice.getContent();
        List<Comment> findLastComments = lastCommentSlice.getContent();
        assertThat(findFirstComments).hasSize(20);
        assertThat(findLastComments).hasSize(5);
        assertThat(findFirstComments)
                .isSortedAccordingTo((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate()));
    }
}