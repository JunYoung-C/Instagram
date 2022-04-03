package toyproject.instragram.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import toyproject.instragram.AppConfig;
import toyproject.instragram.entity.Comment;
import toyproject.instragram.entity.Member;
import toyproject.instragram.entity.Post;
import toyproject.instragram.entity.Reply;

import javax.persistence.EntityManager;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Import(value = AppConfig.class)
@DataJpaTest
class ReplyRepositoryTest {

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    EntityManager em;

    @DisplayName("댓글에 달린 모든 답글 개수 확인")
    @Test
    void countRepliesByCommentId() {
        //given
        Member member1 = new Member(null, "nickname1", "이름1");
        Member member2 = new Member(null, "nickname2", "이름2");
        em.persist(member1);
        em.persist(member2);

        Post post = new Post(member1, null);
        em.persist(post);

        Comment comment = new Comment(post, member1, null);
        em.persist(comment);

        IntStream.range(0, 25)
                .forEach(i -> em.persist(new Reply(comment, i % 2 == 0 ? member1 : member2, String.valueOf(i))));

        em.flush();
        em.clear();

        //when
        Long replyCount = replyRepository.countRepliesByCommentId(comment.getId());

        //then
        assertThat(replyCount).isEqualTo(25);
    }

    @DisplayName("생성 날짜가 빠른 순으로 답글 조회")
    @Test
    void getRepliesByCommentIdOrderByCreatedDateDesc() {
        //given
        Member member1 = new Member(null, "nickname1", "이름1");
        Member member2 = new Member(null, "nickname2", "이름2");
        em.persist(member1);
        em.persist(member2);

        Post post = new Post(member1, null);
        em.persist(post);

        Comment comment = new Comment(post, member1, null);
        em.persist(comment);

        IntStream.range(0, 25)
                .forEach(i -> em.persist(new Reply(comment, i % 2 == 0 ? member1 : member2, String.valueOf(i))));

        em.flush();
        em.clear();

        //when
        Slice<Reply> firstReplySlice = replyRepository
                .getRepliesByCommentIdOrderByCreatedDateDesc(comment.getId(), PageRequest.of(0, 20));
        Slice<Reply> lastReplySlice = replyRepository
                .getRepliesByCommentIdOrderByCreatedDateDesc(comment.getId(), PageRequest.of(1, 20));

        //then
        assertThat(firstReplySlice.isFirst()).isTrue();
        assertThat(firstReplySlice.isLast()).isFalse();
        assertThat(firstReplySlice.getContent()).hasSize(20);
        assertThat(firstReplySlice.getContent())
                .isSortedAccordingTo((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate()));

        assertThat(lastReplySlice.isFirst()).isFalse();
        assertThat(lastReplySlice.isLast()).isTrue();
        assertThat(lastReplySlice.getContent()).hasSize(5);
        assertThat(lastReplySlice.getContent())
                .isSortedAccordingTo((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate()));
    }
}