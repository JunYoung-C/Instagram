package toyproject.instragram.reply.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ReplySaveForm {

    @NotNull
    private Long commentId;

    @NotBlank
    @Size(max = 2200, message = "글자 길이는 2200자를 넘을 수 없습니다.")
    private String text;
}
