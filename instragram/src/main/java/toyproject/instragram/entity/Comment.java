package toyproject.instragram.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String text;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE) // 댓글 삭제시 관련 답글 모두 삭제
    List<Reply> replies = new ArrayList<>();

    public Comment(Post post, Member member, String text) {
        this.post = post;
        this.member = member;
        this.text = text;
    }

    public void changePost(Post post) {
        this.post = post;
    }

    // 연관관계 편의 메소드
    public void addReply(Reply reply) {
        replies.add(reply);
        reply.changeComment(this);
    }
}
