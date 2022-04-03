package toyproject.instragram.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import toyproject.instragram.AppConfig;
import toyproject.instragram.entity.*;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Import(value = AppConfig.class)
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    PostRepository postRepository;

    @DisplayName("먼저 생성된 날짜 순으로 게시물 10개씩 조회")
    @Test
    void getPostsByOrderByCreatedDateDesc() {
        //given
        MemberImage memberImage1 = new MemberImage("file1", "encodedFile1", "png");
        Member member1 = new Member(null, "junyoung", "이름1");
        member1.addProfileImage(memberImage1);
        em.persist(member1);

        MemberImage memberImage2 = new MemberImage("file2", "encodedFile2", "png");
        Member member2 = new Member(null, "jun_young", "이름2");
        member2.addProfileImage(memberImage2);
        em.persist(member2);

        int commentCnt = 3;
        for (int i = 0; i < 25; i++) {
            Member member = i % 2 == 0 ? member1 : member2;
            Post post = new Post(member, null);
            post.addPostFile(new PostFile(post, "postFile" + i, "encodedPostFile" + i, ".png"));

            for (int j = 0; j < commentCnt; j++) {
                new Comment(post, member, "안녕하세요" + i + j);
            }

            postRepository.save(post);
        }

        em.flush();
        em.clear();

        //when
        int size = 10;
        Slice<Post> slice = postRepository.getPostsByOrderByCreatedDateDesc(PageRequest.of(0, size));

        //then
        List<Post> posts = slice.getContent();
        assertThat(slice.isFirst()).isTrue();
        assertThat(slice.isLast()).isFalse();
        assertThat(slice.hasNext()).isTrue();
        assertThat(posts.size()).isEqualTo(size);
        assertThat(posts).isSortedAccordingTo((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate()));
        IntStream.range(0, size).forEach((i) -> {
            assertThat(posts.get(i).getPostFiles()).hasSize(1);
        });
    }

    @DisplayName("조회된 게시물이 없는 경우")
    @Test
    public void getPostsByOrderByCreatedDateDesc_empty() {
        //given

        //when
        // 첫번째 페이지 선택
        Slice<Post> slice = postRepository.getPostsByOrderByCreatedDateDesc(PageRequest.of(0, 10));

        //then
        assertThat(slice.getContent()).hasSize(0);
        assertThat(slice.isFirst()).isTrue();
        assertThat(slice.isLast()).isTrue();
        assertThat(slice.hasNext()).isFalse();
    }
}