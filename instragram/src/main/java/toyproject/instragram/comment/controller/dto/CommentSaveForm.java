package toyproject.instragram.comment.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentSaveForm {

    @NotNull
    private Long postId;

    @NotNull
    private Long memberId;

    @NotBlank
    private String text;
}
