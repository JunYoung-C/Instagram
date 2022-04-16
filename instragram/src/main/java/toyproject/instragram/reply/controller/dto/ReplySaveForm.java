package toyproject.instragram.reply.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReplySaveForm {

    @NotNull
    private Long commentId;

    @NotBlank
    private String text;
}
