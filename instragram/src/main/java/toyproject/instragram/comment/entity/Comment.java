package toyproject.instragram.comment.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.instragram.common.entity.BaseEntity;
import toyproject.instragram.member.entity.Member;
import toyproject.instragram.post.entity.Post;
import toyproject.instragram.reply.entity.Reply;

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

    @OneToMany(mappedBy = "comment", orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();

    public Comment(Post post, Member member, String text) {
        this.post = post;
        this.member = member;
        this.text = text;
    }
}
