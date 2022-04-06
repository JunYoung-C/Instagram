package toyproject.instragram;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.entity.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.stream.IntStream;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitData {

    private final InitPostService initPostService;

    @PostConstruct
    public void init() {
        initPostService.init();
    }

    @Component
    @Transactional
    static class InitPostService {

        @PersistenceContext
        EntityManager em;

        public void init() {
            Member member1 = new Member(
                    Privacy.create("1234", "01011111111"),
                    "doforme",
                    "최준영");
            Member member2 = new Member(
                    Privacy.create("1234", "test@naver.com"),
                    "chlwnsdud",
                    "김영준");

            em.persist(member1);
            em.persist(member2);


            Post post1 = new Post(member1, "안녕하세요~");
            post1.addPostFile(new PostFile(post1, "image", "test-image1", "png"));
            post1.addPostFile(new PostFile(post1, "image", "test-image2", "png"));
            post1.addPostFile(new PostFile(post1, "image", "test-image3", "png"));
            em.persist(post1);

            Post post2 = new Post(member2, "반갑습니다~");
            post2.addPostFile(new PostFile(post2, "image", "test-image4", "png"));
            em.persist(post2);

            Comment comment1 = new Comment(post1, member1, "멋있어요!");
            Comment comment2 = new Comment(post1, member2, "여기 어디에요?");
            Comment comment3 = new Comment(post1, member2, "저도 갈래요!");
            em.persist(comment1);
            em.persist(comment2);
            em.persist(comment3);

            Reply reply1 = new Reply(comment2, member1, "여기는 북극입니다!!");
            Reply reply2 = new Reply(comment2, member2, "저런 곳 가보는게 제 버킷리스트에요. 부러워요ㅠㅠ");
            Reply reply3 = new Reply(comment2, member1, "다음에 갈 떄 같이 가요!!");
            Reply reply4 = new Reply(comment2, member2, "좋아요!");
            em.persist(reply1);
            em.persist(reply2);
            em.persist(reply3);
            em.persist(reply4);

            em.flush();
            em.clear();
        }
    }
}
