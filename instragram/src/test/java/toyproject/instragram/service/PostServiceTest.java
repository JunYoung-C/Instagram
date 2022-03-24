package toyproject.instragram.service;

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
import toyproject.instragram.repository.dto.PostDto;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
        Member member1 = new Member(null, new Profile("myNickname1", "myName1", null));
        Member member2 = new Member(null, new Profile("myNickname2", "myName2", null));
        em.persist(member1);
        em.persist(member2);

        Post post = new Post(member1, null);
        Comment comment1 = new Comment(post, member1, null);
        Comment comment2 = new Comment(post, member2, null);
        Comment comment3 = new Comment(post, member1, null);
        Comment comment4 = new Comment(post, member1, null);
        Comment comment5 = new Comment(post, member2, null);
        Comment comment6 = new Comment(post, member1, null);
        Comment comment7 = new Comment(post, member1, null);

        post.addComment(comment1);
        post.addComment(comment2);
        post.addComment(comment3);
        post.addComment(comment4);
        post.addComment(comment5);
        post.addComment(comment6);
        post.addComment(comment7);

        em.persist(post); // cascade.all

        em.flush();
        em.clear();

        //when
        Slice<PostDto> slice = postService.getPostDtoSlice(PageRequest.of(0, 10));
        List<PostDto> postDtos = slice.getContent();

        //then
        assertThat(postDtos).hasSize(1);
        assertThat(postDtos.get(0).getPostId()).isEqualTo(post.getId());
        assertThat(postDtos.get(0).getTotalComments()).isEqualTo(7);
        assertThat(postDtos.get(0).getMyCommentDtoList()).as("게시물 조회시 조회되는 게시자의 댓글은 최대 3개이다.")
                .hasSize(3);
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