package toyproject.instragram;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.comment.entity.Comment;
import toyproject.instragram.member.entity.Member;
import toyproject.instragram.member.entity.MemberImage;
import toyproject.instragram.member.entity.Privacy;
import toyproject.instragram.post.entity.Post;
import toyproject.instragram.post.entity.PostFile;
import toyproject.instragram.reply.entity.Reply;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

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
            List<Member> testMembers = createMembers();
            List<Post> testPosts = createPosts(testMembers);
            List<Comment> testComments = createComments(testMembers, testPosts);
            createReplies(testMembers, testPosts, testComments);

            em.flush();
            em.clear();
        }

        private List<Member> createMembers() {
            List<Member> members = new ArrayList<>();

            Member member1 = new Member(
                    Privacy.create("1234", "01011111111"),
                    "doforme",
                    "김철수");
            member1.changeProfileImage(new MemberImage("man", "man", "jpg"));

            Member member2 = new Member(
                    Privacy.create("1234", "jinjin@naver.com"),
                    "jinjin",
                    "이영희");
            member2.changeProfileImage(new MemberImage("woman", "woman", "jpg"));

            Member member3 = new Member(
                    Privacy.create("1234", "easy123@kakao.com"),
                    "easy",
                    "최훈");

            Member member4 = new Member(
                    Privacy.create("1234", "rawfoo55@daum.net"),
                    "rawfoo",
                    "박웅태");

            Member member5 = new Member(
                    Privacy.create("1234", "01023456789"),
                    "bangi",
                    "이혜성");

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(member4);
            em.persist(member5);
            members.add(member1);
            members.add(member2);
            members.add(member3);
            members.add(member4);
            members.add(member5);

            return members;
        }

        private List<Post> createPosts(List<Member> members) {
            List<Post> posts = new ArrayList<>();
            int memberCount = members.size();
            for (int i = 0; i < 20; i++) {
                Post post = new Post(members.get(i % memberCount), "테스트 게시물" + i);
                post.addPostFile(new PostFile("02", "02", "png"));
                post.addPostFile(new PostFile("03", "03", "png"));
                post.addPostFile(new PostFile("01", "01", "png"));
                em.persist(post);
                posts.add(post);
            }

            Post post1 = new Post(members.get(0), "어? 이책 뭐지. 왜 왔지. 어디서 왔지.\n" +
                    "이상하네.. 진짜 왜 왔지?\n" +
                    "고개를 연신 갸우뚱거리며 혼잣말만 열마디. 아마도 신청했던 모양이다.\n" +
                    "뒤돌면 잊어버리는 요즘..\n" +
                    "뭐 어쩔것이냐...고 큰소리쳐보지만 사실 환장하겠음.");
            post1.addPostFile(new PostFile("books", "books", "jpg"));
            post1.addPostFile(new PostFile("open-book", "open-book", "jpg"));
            em.persist(post1);
            posts.add(post1);

            Post post2 = new Post(members.get(1), "제가 기르는 고양이에요!");
            post2.addPostFile(new PostFile("cat2", "cat2", "jpg"));
            post2.addPostFile(new PostFile("cat1", "cat1", "jpg"));
            em.persist(post2);
            posts.add(post2);

            Post post3 = new Post(members.get(2), "좀 더 높은 곳에서 세상을 바라보고 싶다.");
            post3.addPostFile(new PostFile("city", "city", "jpg"));
            em.persist(post3);
            posts.add(post3);

            Post post4 = new Post(members.get(3), "거리두기 끝! 사람들과 함꼐 놀아보자~!");
            post4.addPostFile(new PostFile("concert", "concert", "jpg"));
            em.persist(post4);
            posts.add(post4);

            Post post5 = new Post(members.get(4), "세상 곳곳에 숨어있는 보석같은 광경들");
            post5.addPostFile(new PostFile("rock", "rock", "jpg"));
            post5.addPostFile(new PostFile("tree", "tree", "jpg"));
            post5.addPostFile(new PostFile("people", "people", "jpg"));
            post5.addPostFile(new PostFile("moon", "moon", "jpg"));
            post5.addPostFile(new PostFile("flowers", "flowers", "jpg"));
            em.persist(post5);
            posts.add(post5);

            return posts;
        }

        private List<Comment> createComments(List<Member> members, List<Post> posts) {
            List<Comment> comments = new ArrayList<>();
            int memberCount = members.size();
            int postCount = posts.size();

            Comment comment1 =
                    new Comment(posts.get(postCount - 4), members.get(2), "너무 귀여워요ㅠㅠㅠㅠㅠ");
            Comment comment2 =
                    new Comment(posts.get(postCount - 4), members.get(4), "심쿵...");
            Comment comment3 =
                    new Comment(posts.get(postCount - 1), members.get(0), "와... 여기 어디에요?");
            Comment comment4 =
                    new Comment(posts.get(postCount - 1), members.get(1), "다음에는 저도 데려가주세요ㅠㅠ");
            Comment comment5 =
                    new Comment(posts.get(postCount - 1), members.get(3), "저런 곳 가보는게 제 버킷 리스트에요!");

            for (int i = 0; i < 40; i++) {
                Comment comment = new Comment(
                        posts.get(postCount - 2), members.get(i % memberCount), "테스트 댓글" + i);
                em.persist(comment);
                comments.add(comment);
            }

            em.persist(comment1);
            em.persist(comment2);
            em.persist(comment3);
            em.persist(comment4);
            em.persist(comment5);
            comments.add(comment1);
            comments.add(comment2);
            comments.add(comment3);
            comments.add(comment4);
            comments.add(comment5);

            return comments;
        }

        private List<Reply> createReplies(List<Member> members, List<Post> posts, List<Comment> comments) {
            List<Reply> replies = new ArrayList<>();
            int memberCount = members.size();
            int postCount = posts.size();
            int commentCount = comments.size();

            for (int i = 0; i < 40; i++) {
                em.persist(
                        new Reply(comments.get(commentCount - 1), members.get(i % memberCount), "테스트 답글" + i));
            }
            for (int i = 0; i < 4; i++) {
                em.persist(
                        new Reply(comments.get(commentCount - 2), members.get(i % memberCount), "테스트 답글" + i));
            }
            Reply reply1 =
                    new Reply(comments.get(commentCount - 5), members.get(1), "감사합니다ㅎㅎㅎ 제 아들 딸이에요");
            Reply reply2 =
                    new Reply(comments.get(commentCount - 5), members.get(2), "ㅋㅋㅋㅋ 고양이 이름이 뭐예요?");
            Reply reply3 =
                    new Reply(comments.get(commentCount - 5), members.get(1), "나비와 링링이요!");
            Reply reply4 =
                    new Reply(comments.get(commentCount - 4), members.get(1), "저도 매일 겪고 있답니다..");

            em.persist(reply1);
            em.persist(reply2);
            em.persist(reply3);
            em.persist(reply4);
            replies.add(reply1);
            replies.add(reply2);
            replies.add(reply3);
            replies.add(reply4);

            return replies;
        }
    }
}
