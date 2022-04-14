package toyproject.instragram.member.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemberSaveForm {

    @NotBlank
    private String phoneNumberOrEmail;

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;
}
