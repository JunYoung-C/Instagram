package toyproject.instragram.comment.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CommentSaveForm {

    @NotNull
    private Long postId;

    @NotBlank(message = "댓글을 입력해주세요.")
    @Size(max = 2200, message = "글자 길이는 2200자를 넘을 수 없습니다.")
    private String text;
}
