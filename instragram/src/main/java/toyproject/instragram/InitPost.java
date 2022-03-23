package toyproject.instragram;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.entity.Comment;
import toyproject.instragram.entity.Member;
import toyproject.instragram.entity.Post;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
            toyproject.instragram.entity.Profile profile1 = new toyproject.instragram.entity.Profile("nickname1", "이름1", null);
            Member member1 = new Member(null, profile1);
            em.persist(member1);

            toyproject.instragram.entity.Profile profile2 = new toyproject.instragram.entity.Profile("nickname2", "이름2", null);
            Member member2 = new Member(null, profile2);
            em.persist(member2);

            for (int i = 0; i < 100; i++) {
                Member member = i % 2 == 0 ? member1 : member2;
                Post post = new Post(member, null);
                for (int j = 0; j < i; j++) {
                    post.addComment(new Comment(post, "안녕하세요" + i + j));
                }
                em.persist(post);
            }
        }
    }
}
