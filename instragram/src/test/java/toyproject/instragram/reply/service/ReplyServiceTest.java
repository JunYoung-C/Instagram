package toyproject.instragram.reply.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import toyproject.instragram.comment.entity.Comment;
import toyproject.instragram.comment.repository.CommentRepository;
import toyproject.instragram.member.entity.Member;
import toyproject.instragram.member.entity.Privacy;
import toyproject.instragram.member.repository.MemberRepository;
import toyproject.instragram.post.entity.Post;
import toyproject.instragram.reply.entity.Reply;
import toyproject.instragram.reply.repository.ReplyRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static toyproject.instragram.common.exception.api.ApiExceptionType.*;

@ExtendWith(MockitoExtension.class)
class ReplyServiceTest {

    @InjectMocks
    ReplyService replyService;

    @Mock
    ReplyRepository replyRepository;

    @Mock
    CommentRepository commentRepository;

    @Mock
    MemberRepository memberRepository;

    @Nested
    @DisplayName("답글 등록")
    class addReplyTest {
        @DisplayName("성공")
        @Test
        void success() {
            //given
            Member member = new Member(
                    Privacy.create("1234", "01011111111"),
                    "nickname",
                    "name");

            Comment comment = new Comment(new Post(member, "게시물"), member, "댓글");

            when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));
            when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

            ReplyDto replyDto = new ReplyDto(1L, 2L, "답글");

            //when
            //then
            assertThatNoException().isThrownBy(() -> replyService.addReply(replyDto));
        }

        @DisplayName("실패 - 회원 세션 만료")
        @Test
        void failByMember() {
            //given
            ReplyDto replyDto = new ReplyDto(1L, 2L, "답글");

            when(commentRepository.findById(anyLong()))
                    .thenReturn(Optional.of(new Comment(null, null, "댓글")));

            //when
            //then
            assertThatThrownBy(() -> replyService.addReply(replyDto))
                    .isExactlyInstanceOf(EXPIRED_SESSION.getException().getClass());
        }

        @DisplayName("실패 - 존재하지 않는 댓글")
        @Test
        void failByComment() {
            //given
            ReplyDto replyDto = new ReplyDto(1L, 2L, "답글");

            //when
            //then
            assertThatThrownBy(() -> replyService.addReply(replyDto))
                    .isExactlyInstanceOf(NOT_FOUND_COMMENT.getException().getClass());
        }
    }

    @DisplayName("답글 목록 조회")
    @Test
    void getReplySlice() {
        //given
        when(replyRepository.getRepliesByCommentId(anyLong(), any(Pageable.class)))
                .thenReturn(new SliceImpl<>(new ArrayList<>()));

        //when
        //then
        assertThatNoException().isThrownBy(() ->
                replyService.getReplySlice(1L, PageRequest.of(0, 1)));
    }

    @Nested
    @DisplayName("답글 삭제")
    class deleteReplyTest {
        @DisplayName("성공")
        @Test
        void success() {
            //given
            when(replyRepository.existsById(1L)).thenReturn(true);

            //when
            //then
            assertThatNoException().isThrownBy(() -> replyService.deleteReply(1L));
        }

        @DisplayName("실패 - 존재하지 않는 답글")
        @Test
        void fail() {
            //given
            //when
            //then
            assertThatThrownBy(() -> replyService.deleteReply(1L))
                    .isExactlyInstanceOf(NOT_FOUND_REPLY.getException().getClass());
        }
    }

    @DisplayName("답글 개수 조회")
    @Test
    void getReplyCount() {
        //given
        when(replyRepository.countRepliesByCommentId(anyLong())).thenReturn(anyLong());

        //when
        Long replyCount = replyService.getReplyCount(1L);

        //then
        assertThat(replyCount).isExactlyInstanceOf(Long.class);
    }

    @Nested
    @DisplayName("답글 한건 조회")
    class getReplyTest {
        @DisplayName("성공")
        @Test
        void success() {
            //given
            Reply reply = new Reply(null, null, "답글");

            when(replyRepository.findById(anyLong()))
                    .thenReturn(Optional.of(reply));

            //when
            Reply findReply = replyService.getReply(1L);

            //then
            assertThat(reply).isEqualTo(findReply);
        }

        @DisplayName("실패 - 존재하지 않는 답글")
        @Test
        void fail() {
            //given
            //when
            //then
            assertThatThrownBy(() -> replyService.getReply(1L))
                    .isExactlyInstanceOf(NOT_FOUND_REPLY.getException().getClass());
        }
    }
}