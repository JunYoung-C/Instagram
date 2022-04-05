package toyproject.instragram.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReplySaveForm {

    @NotNull
    private Long commentId;

    @NotNull
    private Long memberId;

    @NotBlank
    private String text;
}
