package toyproject.instragram.dto;

import lombok.Data;
import toyproject.instragram.entity.Comment;
import toyproject.instragram.entity.Reply;

@Data
public class ReplyDto {

    private Long replyId;
    private MemberDto memberDto;
    private String text;

    public ReplyDto(Reply reply) {
        this.replyId = reply.getId();
        this.memberDto = new MemberDto(reply.getMember().getId(),
                reply.getMember().getProfile().getNickname(),
                reply.getMember().getProfile().getPhotoPath());
        this.text = reply.getText();
    }


}
