package toyproject.instragram.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.common.exception.api.httpstatusexception.ForbiddenException;
import toyproject.instragram.common.exception.api.httpstatusexception.NotFoundException;
import toyproject.instragram.common.file.FileDto;
import toyproject.instragram.member.entity.Member;
import toyproject.instragram.post.entity.Post;
import toyproject.instragram.post.service.PostDto;
import toyproject.instragram.post.service.PostService;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    EntityManager em;

    @DisplayName("게시물 등록")
    @Test
    void addPost() {
        //given
        Member member = new Member(null, "junyoung", "이름1");
        em.persist(member);

        //when
        List<FileDto> fileDtos = List.of(
                new FileDto("uploadFileName", "storeFileName", "png"));
        Long postId = postService.addPost(new PostDto(member.getId(), fileDtos, "안녕하세요"));

        em.flush();
        em.clear();

        //then
        Post findPost = em.find(Post.class, postId);
        assertThat(findPost).isNotNull();
    }

    @DisplayName("게시물 목록 조회")
    @Test
    void getPostSlice() {
        //given
        Member member = new Member(null, "junyoung", "이름");

        em.persist(member);

        List<FileDto> fileDtos = List.of(
                new FileDto("uploadFileName", "storeFileName", "png"));
        IntStream.range(0, 11)
                .forEach(i -> postService.addPost(new PostDto(member.getId(), fileDtos, "안녕하세요" + i)));

        em.flush();
        em.clear();

        //when
        int size = 10;
        Slice<Post> slice = postService.getPostSlice(PageRequest.of(0, size));
        List<Post> posts = slice.getContent();

        //then
        assertThat(posts).hasSize(size);
    }

    @DisplayName("본인이 올린 게시물 한건 조회 성공")
    @Test
    void getOwnerPostByPostId() {
        //given
        Member member = new Member(null, "junyoung", "이름1");
        em.persist(member);

        List<FileDto> fileDtos = List.of(
                new FileDto("uploadFileName", "storeFileName", "png"));
        Long postId = postService.addPost(new PostDto(member.getId(), fileDtos, "안녕하세요"));

        em.flush();
        em.clear();

        //when
        Post findPost = postService.getOwnerPostByPostId(postId, member.getId());

        //then
        assertThat(findPost.getId()).isEqualTo(postId);
        assertThat(findPost.getMember().getId()).isEqualTo(member.getId());
    }

    @DisplayName("본인이 올린 게시물 한건 조회 싱패 - 권한 부족")
    @Test
    void getOwnerPostByPostId_failByForbidden() {
        //given
        Member member = new Member(null, "junyoung", "이름1");
        em.persist(member);

        List<FileDto> fileDtos = List.of(
                new FileDto("uploadFileName", "storeFileName", "png"));
        Long postId = postService.addPost(new PostDto(member.getId(), fileDtos, "안녕하세요"));

        em.flush();
        em.clear();
        //when
        //then
        assertThatThrownBy(() -> postService.getOwnerPostByPostId(postId, member.getId() + 1))
                .isExactlyInstanceOf(ForbiddenException.class);
    }

    @DisplayName("본인이 올린 게시물 한건 조회 싱패 - 존재하지 않음")
    @Test
    void getOwnerPostByPostId_failByNotFound() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.getOwnerPostByPostId(0l, 0l))
                .isExactlyInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시물 글 수정")
    @Test
    void modifyPostText() {
        //given
        Member member = new Member(null, "junyoung", "이름1");
        em.persist(member);

        List<FileDto> fileDtos = List.of(
                new FileDto("uploadFileName", "storeFileName", "png"));
        Long postId = postService.addPost(new PostDto(member.getId(), fileDtos, "안녕하세요"));

        em.flush();
        em.clear();

        //when
        String modifiedText = "수정했어요";
        postService.modifyPostText(postId, modifiedText);

        //then
        Post findPost = em.find(Post.class, postId);
        assertThat(findPost.getText()).isEqualTo(modifiedText);
    }

    @DisplayName("게시물 삭제")
    @Test
    void deletePost() {
        //given
        Member member = new Member(null, "junyoung", "이름1");
        em.persist(member);

        List<FileDto> fileDtos = List.of(
                new FileDto("uploadFileName", "storeFileName", "png"));
        Long postId = postService.addPost(new PostDto(member.getId(), fileDtos, "안녕하세요"));

        em.flush();
        em.clear();

        //when
        postService.deletePost(postId);

        //then
        Post findPost = em.find(Post.class, postId);
        assertThat(findPost).isNull();

    }
}