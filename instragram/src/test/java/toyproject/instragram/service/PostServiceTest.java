package toyproject.instragram.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.entity.Comment;
import toyproject.instragram.entity.Member;
import toyproject.instragram.entity.Post;
import toyproject.instragram.entity.Profile;
import toyproject.instragram.repository.PostRepository;
import toyproject.instragram.repository.dto.PostDto;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
        Profile profile = new Profile("nickname", "이름", null);
        Member member = new Member(null, profile);
        em.persist(member);

        Post post = new Post(member, null);

        //when
        Long postId = postService.addPost(post);

        //then
        Post findPost = em.find(Post.class, postId);
        assertThat(findPost).isEqualTo(post);
    }

    @DisplayName("게시물 조회")
    @Test
    void getPosts() {
        //given
        Profile profile = new Profile("nickname", "이름", null);
        Member member = new Member(null, profile);
        em.persist(member);

        Post post = new Post(member, null);
        Long postId = postService.addPost(post);

        //when
        Slice<PostDto> slice = postService.getPosts(PageRequest.of(0, 10));
        List<PostDto> postDtos = slice.getContent();

        //then
        assertThat(postDtos).hasSize(1);
        assertThat(postDtos)
                .extracting("postId")
                .containsExactly(postId);
    }

    @DisplayName("게시물 삭제")
    @Test
    void deletePost() {
        //given
        Profile profile = new Profile("nickname", "이름", null);
        Member member = new Member(null, profile);
        em.persist(member);

        Post post = new Post(member, null);
        Long postId = postService.addPost(post);

        //when
        postService.deletePost(postId);

        //then
        Post findPost = em.find(Post.class, postId);
        assertThat(findPost).isNull();

    }
}