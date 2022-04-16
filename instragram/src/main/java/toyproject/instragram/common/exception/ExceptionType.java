package toyproject.instragram.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import toyproject.instragram.common.exception.form.signin.IncorrectPasswordException;
import toyproject.instragram.common.exception.form.signin.NotFoundAccountByEmailException;
import toyproject.instragram.common.exception.form.signin.NotFoundAccountByNicknameException;
import toyproject.instragram.common.exception.form.signin.NotFoundAccountByPhoneNumberException;
import toyproject.instragram.common.exception.form.signup.*;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
    UNKNOWN(new UnknownException("알 수 없는 에러가 발생했습니다."));

    private final CustomException exception;
}
