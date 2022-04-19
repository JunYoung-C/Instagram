package toyproject.instragram.comment.service;

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
import toyproject.instragram.post.repository.PostRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static toyproject.instragram.common.exception.api.ApiExceptionType.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    CommentService commentService;

    @Mock
    CommentRepository commentRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    PostRepository postRepository;

    @Nested
    @DisplayName("게시물 등록")
    class addCommentTest {
        @DisplayName("성공")
        @Test
        void success() {
            //given
            Member member = new Member(
                    Privacy.create("1234", "01011111111"),
                    "nickname",
                    "name");

            Post post = new Post(member, "게시물");

            when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
            when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

            CommentDto commentDto = new CommentDto(1L, 2L, "댓글");

            //when
            //then
            assertThatNoException().isThrownBy(() -> commentService.addComment(commentDto));
        }

        @DisplayName("회원 세션 만료로 인한 실패")
        @Test
        void failByMember() {
            //given
            CommentDto commentDto = new CommentDto(1L, 2L, "댓글");

            when(postRepository.findById(anyLong())).thenReturn(Optional.of(new Post(null, "게시물")));

            //when
            //then
            assertThatThrownBy(() -> commentService.addComment(commentDto))
                    .isExactlyInstanceOf(EXPIRED_SESSION.getException().getClass());
        }

        @DisplayName("게시물 검증 실패")
        @Test
        void failByPost() {
            //given
            Member member = new Member(
                    Privacy.create("1234", "01011111111"),
                    "nickname",
                    "name");

            CommentDto commentDto = new CommentDto(1L, 2L, "댓글");

            //when
            //then
            assertThatThrownBy(() -> commentService.addComment(commentDto))
                    .isExactlyInstanceOf(NOT_FOUND_POST.getException().getClass());
        }
    }

    @DisplayName("댓글 목록 조회")
    @Test
    void getCommentSlice() {
        //given
        when(commentRepository.getCommentsByPostIdOrderByCreatedDateDesc(anyLong(), any(Pageable.class)))
                .thenReturn(new SliceImpl<>(new ArrayList<>()));

        //when
        //then
        assertThatNoException().isThrownBy(() ->
                commentService.getCommentSlice(1L, PageRequest.of(0, 1)));
    }

    @Nested
    @DisplayName("댓글 삭제")
    class deleteCommentTest {
        @DisplayName("성공")
        @Test
        void success() {
            //given
            when(commentRepository.existsById(1L)).thenReturn(true);

            //when
            //then
            assertThatNoException().isThrownBy(() -> commentService.deleteComment(1L));
        }

        @DisplayName("실패 - 존재하지 않는 댓글")
        @Test
        void fail() {
            //given
            //when
            //then
            assertThatThrownBy(() -> commentService.deleteComment(1L))
                    .isExactlyInstanceOf(NOT_FOUND_COMMENT.getException().getClass());
        }
    }

    @DisplayName("댓글 개수 조회")
    @Test
    void getCommentCount() {
        //given
        when(commentRepository.countCommentsByPostId(anyLong())).thenReturn(anyLong());

        //when
        Long commentCount = commentService.getCommentCount(1L);

        //then
        assertThat(commentCount).isExactlyInstanceOf(Long.class);
    }

    @Nested
    @DisplayName("댓글 한건 조회")
    class getCommentTest {
        @DisplayName("성공")
        @Test
        void success() {
            //given
            Comment comment = new Comment(null, null, "댓글");

            when(commentRepository.findById(anyLong()))
                    .thenReturn(Optional.of(comment));

            //when
            Comment findComment = commentService.getComment(1L);

            //then
            assertThat(comment).isEqualTo(findComment);
        }

        @DisplayName("실패")
        @Test
        void fail() {
            //given
            //when
            //then
            assertThatThrownBy(() -> commentService.getComment(1L))
                    .isExactlyInstanceOf(NOT_FOUND_COMMENT.getException().getClass());
        }
    }
}