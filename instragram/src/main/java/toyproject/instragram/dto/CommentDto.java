package toyproject.instragram.dto;

import lombok.Data;
import toyproject.instragram.entity.Comment;

@Data
public class CommentDto {

    private Long commentId;
    private MemberDto memberDto;
    private String text;
    private Long replyCount;

    public CommentDto(Comment comment, Long replyCount) {
        this.commentId = comment.getId();
        this.memberDto = new MemberDto(comment.getMember().getId(),
                comment.getMember().getProfile().getNickname(),
                comment.getMember().getProfile().getPhotoPath());
        this.text = comment.getText();
        this.replyCount = replyCount;
    }
}
