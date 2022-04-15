package toyproject.instragram.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import toyproject.instragram.common.exception.inheritance.IncorrectPasswordException;
import toyproject.instragram.common.exception.inheritance.notfoundaccount.*;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
    UNKNOWN(new UnknownException("알 수 없는 에러가 발생했습니다.")),
    NOT_FOUND_ACCOUNT(new NotFoundAccountException("해당 계정을 찾을 수 없습니다. 아이디를 확인하고 다시 시도하세요.")),
    NOT_FOUND_ACCOUNT_BY_NICKNAME(new NotFoundAccountByNicknameException(
            "입력한 사용자 이름을 사용하는 계정을 찾을 수 없습니다. 사용자 이름을 확인하고 다시 시도하세요.")),
    NOT_FOUND_ACCOUNT_BY_Email(new NotFoundAccountByEmailException(
            "입력한 이메일을 사용하는 계정을 찾을 수 없습니다. 사용자의 이메일을 확인하고 다시 시도해세요")),
    NOT_FOUND_ACCOUNT_BY_Phone_Number(new NotFoundAccountByPhoneNumberException(
            "입력한 전화번호를 사용하는 계정을 찾을 수 없습니다. 사용자의 전화번호를 확인하고 다시 시도해세요")),
    INCORRECT_PASSWORD(new IncorrectPasswordException("잘못된 비밀번호입니다. 다시 확인하세요."));

    private final CustomException exception;
}
