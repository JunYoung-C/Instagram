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
import toyproject.instragram.entity.Reply;
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
        Member member = new Member(null, "nickname", "이름");
        em.persist(member);

        Post post = new Post(member, null);
        em.persist(post);

        Comment comment = new Comment(post, member, null);
        em.persist(comment);

        //when
        Long replyId = replyService.addReply(new ReplyDto(comment.getId(), member.getId(), "안녕하세요"));

        em.flush();
        em.clear();

        //then
        Reply findReply = replyRepository.findById(replyId).orElse(null);
        assertThat(findReply).isNotNull();
    }

    @DisplayName("답글 최대 20건 조회")
    @Test
    void getReplySlice() {
        //given
        Member member1 = new Member(null, "nickname1", "이름1");
        Member member2 = new Member(null, "nickname2", "이름2");
        em.persist(member1);
        em.persist(member2);

        Post post = new Post(member1, null);
        em.persist(post);

        Comment comment = new Comment(post, member1, null);
        em.persist(comment);

        IntStream.range(0, 21)
                .forEach(i -> em.persist(new Reply(comment, i % 2 == 0 ? member1 : member2, String.valueOf(i))));

        em.flush();
        em.clear();

        //when
        int page = 0;
        Slice<Reply> replySlice = replyService.getReplySlice(comment.getId(), page);

        //then
        assertThat(replySlice.getContent()).hasSize(20);
        assertThat(replySlice.isFirst()).isTrue();
        assertThat(replySlice.isLast()).isFalse();
        assertThat(replySlice.hasNext()).isTrue();
    }

    @DisplayName("답글 내용 수정")
    @Test
    void modifyReplyText() {
        //given
        Member member = new Member(null, "nickname", "이름");
        em.persist(member);

        Post post = new Post(member, null);
        em.persist(post);

        Comment comment = new Comment(post, member, null);
        em.persist(comment);

        Long replyId = replyService.addReply(new ReplyDto(comment.getId(), member.getId(), "안녕하세요"));

        em.flush();
        em.clear();

        //when
        String modifiedText = "수정했어요";
        replyService.modifyReplyText(replyId, modifiedText);

        em.flush();
        em.clear();

        //then
        Reply findReply = replyRepository.findById(replyId).orElse(null);
        assertThat(findReply.getText()).isEqualTo(modifiedText);
    }

    @DisplayName("답글 삭제")
    @Test
    void deleteReply() {
        //given
        Member member = new Member(null, "nickname", "이름");
        em.persist(member);

        Post post = new Post(member, null);
        em.persist(post);

        Comment comment = new Comment(post, member, null);
        em.persist(comment);

        Long replyId = replyService.addReply(new ReplyDto(comment.getId(), member.getId(), "안녕하세요"));

        em.flush();
        em.clear();

        //when
        replyService.deleteReply(replyId);

        //then
        Reply findReply = replyRepository.findById(replyId).orElse(null);
        assertThat(findReply).isNull();
    }
}