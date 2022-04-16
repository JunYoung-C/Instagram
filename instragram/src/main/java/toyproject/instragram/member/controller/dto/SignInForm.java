package toyproject.instragram.member.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignInForm {

    /**
     * 로그인 id는 전화번호, 사용자 이름 또는 이메일 중 하나
     */
    @NotBlank(message = "아이디를 입력해주세요")
    private String signInId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 4, message = "비밀번호는 4글자 이상 입력해주세요.")
    private String password;
}
