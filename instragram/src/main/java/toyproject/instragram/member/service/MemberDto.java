package toyproject.instragram.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberDto {

    private String phoneNumberOrEmail;
    private String name;
    private String nickname;
    private String password;
}
