package toyproject.instragram.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.FileDto;
import toyproject.instragram.entity.Member;
import toyproject.instragram.entity.Post;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

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

    @DisplayName("게시물 최대 10건 조회")
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
        Slice<Post> slice = postService.getPostSlice(0);
        List<Post> posts = slice.getContent();

        //then
        assertThat(posts).hasSize(10);
    }

    @DisplayName("게시물 한건 조회")
    @Test
    void getPost() {
        //given
        Member member = new Member(null, "junyoung", "이름1");
        em.persist(member);

        List<FileDto> fileDtos = List.of(
                new FileDto("uploadFileName", "storeFileName", "png"));
        Long postId = postService.addPost(new PostDto(member.getId(), fileDtos, "안녕하세요"));

        em.flush();
        em.clear();

        //when
        Post findPost = postService.getPost(postId).orElse(null);

        //then
        assertThat(findPost.getId()).isEqualTo(postId);
    }

    @DisplayName("게시물 한건 조회 - 실패")
    @Test
    void getPost_fail() {
        //given
        //when
        Optional<Post> findPost = postService.getPost(0L);

        //then
        assertThat(findPost).isEmpty();
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