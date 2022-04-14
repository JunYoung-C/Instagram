package toyproject.instragram.reply.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.instragram.comment.entity.Comment;
import toyproject.instragram.common.entity.BaseEntity;
import toyproject.instragram.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "reply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String text;

    public Reply(Comment comment, Member member, String text) {
        this.comment = comment;
        this.member = member;
        this.text = text;
    }
}
