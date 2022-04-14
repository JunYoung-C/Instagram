package toyproject.instragram.comment.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import toyproject.instragram.post.controller.dto.InnerMemberResponse;
import toyproject.instragram.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponse {

    private Long commentId;
    private InnerMemberResponse member;
    private String text;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;
    private Long replyCount;

    public static CommentResponse from(Comment comment, Long replyCount) {
        return new CommentResponse(
                comment.getId(),
                InnerMemberResponse.from(comment.getMember()),
                comment.getText(),
                comment.getCreatedDate(),
                replyCount);
    }
}
