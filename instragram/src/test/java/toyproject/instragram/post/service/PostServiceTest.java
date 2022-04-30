package toyproject.instragram.post.service;

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
import toyproject.instragram.member.entity.Member;
import toyproject.instragram.member.entity.Privacy;
import toyproject.instragram.member.repository.MemberRepository;
import toyproject.instragram.post.entity.Post;
import toyproject.instragram.post.repository.PostRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static toyproject.instragram.common.exception.api.ApiExceptionType.EXPIRED_SESSION;
import static toyproject.instragram.common.exception.api.ApiExceptionType.NOT_FOUND_POST;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    PostService postService;

    @Mock
    PostRepository postRepository;

    @Mock
    MemberRepository memberRepository;

    @Nested
    @DisplayName("게시물 등록")
    class addPostTest {
        @DisplayName("성공")
        @Test
        void success() {
            //given
            Member member = new Member(
                    Privacy.create("1234", "01011111111"),
                    "nickname",
                    "name");

            when(memberRepository.findById(anyLong()))
                    .thenReturn(Optional.of(member));

            PostDto postDto = new PostDto(1L, new ArrayList<>(), "테스트");

            //when
            //then
            assertThatNoException().isThrownBy(() -> postService.addPost(postDto));
        }

        @DisplayName("실패 - 회원 세션 만료")
        @Test
        void fail() {
            //given
            PostDto postDto = new PostDto(1L, new ArrayList<>(), "테스트");

            //when
            //then
            assertThatThrownBy(() -> postService.addPost(postDto))
                    .isExactlyInstanceOf(EXPIRED_SESSION.getException().getClass());
        }
    }

    @DisplayName("게시물 목록 조회")
    @Test
    void getPostSlice() {
        //given
        when(postRepository.getPostsByOrderByIdDesc(any(Pageable.class)))
                .thenReturn(new SliceImpl<>(new ArrayList<>()));

        //when
        //then
        assertThatNoException().isThrownBy(() -> postService.getPostSlice(PageRequest.of(0, 1)));
    }

    @Nested
    @DisplayName("게시물 한건 조회")
    class getPostTest {
        @DisplayName("성공")
        @Test
        void success() {
            //given
            Post post = new Post(null, "게시물");
            when(postRepository.findById(anyLong()))
                    .thenReturn(Optional.of(post));

            //when
            Post findPost = postService.getPost(1L);

            //then
            assertThat(findPost).isEqualTo(post);
        }

        @DisplayName("실패 - 존재하지 않는 게시물")
        @Test
        void fail() {
            //given
            //when
            //then
            assertThatThrownBy(() -> postService.getPost(1L))
                    .isExactlyInstanceOf(NOT_FOUND_POST.getException().getClass());
        }
    }

    @Nested
    @DisplayName("게시물 글 수정")
    class modifyPostTextTest {
        @DisplayName("성공")
        @Test
        void success() {
            //given
            Post post = new Post(null, "테스트");
            when(postRepository.findById(anyLong()))
                    .thenReturn(Optional.of(post));

            //when
            String modifiedText = "수정";
            postService.modifyPostText(1L, modifiedText);

            //then
            assertThat(post.getText()).isEqualTo(modifiedText);
        }

        @DisplayName("실패 - 존재하지 않는 게시물")
        @Test
        void fail() {
            //given
            //when
            //then
            assertThatThrownBy(() -> postService.modifyPostText(1L, "수정"))
                    .isExactlyInstanceOf(NOT_FOUND_POST.getException().getClass());
        }
    }

    @Nested
    @DisplayName("게시물 삭제")
    class deletePostTest {
        @DisplayName("성공")
        @Test
        void success() {
            //given
            when(postRepository.findById(anyLong()))
                    .thenReturn(Optional.of(new Post(null, "테스트")));

            //when
            //then
            assertThatNoException().isThrownBy(() -> postService.deletePost(1L));
        }

        @DisplayName("실패 - 존재하지 않는 게시물")
        @Test
        void fail() {
            //given
            //when
            //then
            assertThatThrownBy(() -> postService.deletePost(1L))
                    .isExactlyInstanceOf(NOT_FOUND_POST.getException().getClass());
        }
    }
}