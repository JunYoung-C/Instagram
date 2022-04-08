package toyproject.instragram.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SignInForm {

    /**
     * 로그인 id는 전화번호, 사용자 이름 또는 이메일 중 하나
     */
    @NotNull
    private String signInId;

    @NotNull
    private String password;
}
