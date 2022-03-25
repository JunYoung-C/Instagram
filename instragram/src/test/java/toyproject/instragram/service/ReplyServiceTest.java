package toyproject.instragram.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.dto.ReplyDto;
import toyproject.instragram.entity.*;
import toyproject.instragram.repository.ReplyRepository;

import javax.persistence.EntityManager;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ReplyServiceTest {

    @Autowired
    ReplyService replyService;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    EntityManager em;

    @DisplayName("답글 등록")
    @Test
    void addReply() {
        //given
        Member member = new Member(null, new Profile("myNickname1", "myName1", null));
        em.persist(member);

        Post post = new Post(member, null);
        em.persist(post);

        Comment comment = new Comment(post, member, null);
        em.persist(comment);

        Reply reply = new Reply(comment, member, "reply");

        //when
        Long replyId = replyService.addReply(reply);

        //then
        Reply findReply = replyRepository.findById(replyId).orElse(null);
        assertThat(findReply).isEqualTo(reply);
    }

    @DisplayName("답글 조회")
    @Test
    void getReplyDtoSlice() {
        //given
        Member member1 = new Member(null, new Profile("myNickname1", "myName1", null));
        Member member2 = new Member(null, new Profile("myNickname2", "myName2", null));
        em.persist(member1);
        em.persist(member2);

        Post post = new Post(member1, null);
        em.persist(post);

        Comment comment = new Comment(post, member1, null);
        em.persist(comment);

        IntStream.range(0, 5)
                .forEach((i) -> {
                    Reply reply = new Reply(comment, i % 2 == 0 ? member1 : member2, String.valueOf(i));
                    em.persist(reply);
                });

        em.flush();
        em.clear();

        //when
        Slice<ReplyDto> replyDtoSlice = replyService.getReplyDtoSlice(comment.getId(), PageRequest.of(0, 20));

        //then
        assertThat(replyDtoSlice.getContent()).hasSize(5);
        assertThat(replyDtoSlice.isFirst()).isTrue();
        assertThat(replyDtoSlice.isLast()).isTrue();
    }

    @DisplayName("답글 내용 수정")
    @Test
    void modifyReplyText() {
        //given
        Member member = new Member(null, new Profile("myNickname1", "myName1", null));
        em.persist(member);

        Post post = new Post(member, null);
        em.persist(post);

        Comment comment = new Comment(post, member, null);
        em.persist(comment);

        Reply reply = new Reply(comment, member, "안녕하세요");
        Long replyId = replyService.addReply(reply);

        em.flush();
        em.clear();

        //when
        String text = "수정했어요";
        replyService.modifyReplyText(replyId, text);

        em.flush();
        em.clear();

        //then
        Reply findReply = em.find(Reply.class, replyId);
        assertThat(findReply.getText()).isEqualTo(text);
    }

    @DisplayName("답글 삭제")
    @Test
    void deleteReply() {
        //given
        Member member = new Member(null, new Profile("myNickname1", "myName1", null));
        em.persist(member);

        Post post = new Post(member, null);
        em.persist(post);

        Comment comment = new Comment(post, member, null);
        em.persist(comment);

        Reply reply = new Reply(comment, member, "안녕하세요");
        Long replyId = replyService.addReply(reply);

        em.flush();
        em.clear();

        //when
        replyService.deleteReply(replyId);

        //then
        Reply findReply = em.find(Reply.class, replyId);
        assertThat(findReply).isNull();
    }
}