package toyproject.instragram;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.entity.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.stream.IntStream;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitPost {

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
            Privacy privacy = Privacy.create()
            Member member1 = new Member(null, "nickname1", "이름1");
            Member member2 = new Member(null, "nickname2", "이름2");
            em.persist(member1);
            em.persist(member2);

            Post post = new Post(member1, null);
            em.persist(post);

            Comment comment = new Comment(post, member1, null);
            em.persist(comment);

            IntStream.range(0, 25)
                    .forEach(i -> em.persist(new Reply(comment, i % 2 == 0 ? member1 : member2, String.valueOf(i))));

            em.flush();
            em.clear();
            for (int i = 0; i < 100; i++) {
                Member member = i % 2 == 0 ? member1 : member2;
                Post post = new Post(member, null);
                for (int j = 0; j < i; j++) {
                    post.addComment(new Comment(post, i % 2 == 0 ? member1 : member2, "안녕하세요" + i + j));
                }
                em.persist(post);
            }
        }
    }
}
