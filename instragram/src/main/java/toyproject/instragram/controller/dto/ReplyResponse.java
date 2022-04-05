package toyproject.instragram.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import toyproject.instragram.entity.Reply;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReplyResponse {

    private Long replyId;
    private InnerMemberResponse member;
    private String text;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    public static ReplyResponse from(Reply reply) {
        return new ReplyResponse(
                reply.getId(),
                InnerMemberResponse.from(reply.getMember()),
                reply.getText(),
                reply.getCreatedDate());
    }
}
