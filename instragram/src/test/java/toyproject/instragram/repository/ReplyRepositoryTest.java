package toyproject.instragram.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.instragram.AppConfig;
import toyproject.instragram.entity.*;

import javax.persistence.EntityManager;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
        Member member1 = new Member(null, new Profile("myNickname1", "myName1", null));
        Member member2 = new Member(null, new Profile("myNickname2", "myName2", null));
        em.persist(member1);
        em.persist(member2);

        Post post = new Post(member1, null);
        em.persist(post);

        Comment comment = new Comment(post, member1, null);
        em.persist(comment);

        IntStream.range(0, 25)
                .forEach((i) -> {
                    Reply reply = new Reply(comment, i % 2 == 0 ? member1 : member2, String.valueOf(i));
                    comment.addReply(reply);
                    em.persist(reply);
                });
        post.addComment(comment);


        em.flush();
        em.clear();

        //when
        Long replyCount = replyRepository.countRepliesByCommentId(comment.getId());

        //then
        assertThat(replyCount).isEqualTo(25);
    }
}