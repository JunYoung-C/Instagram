package toyproject.instragram.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import toyproject.instragram.common.exception.signin.*;
import toyproject.instragram.common.exception.signup.*;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
    UNKNOWN(new UnknownException("알 수 없는 에러가 발생했습니다.")),

    //로그인 관련 에러
    NOT_FOUND_ACCOUNT_BY_NICKNAME(new NotFoundAccountByNicknameException(
            "signInId", "incorrect",
            "입력한 사용자 이름을 사용하는 계정을 찾을 수 없습니다. 사용자 이름을 확인하고 다시 시도하세요.")),
    NOT_FOUND_ACCOUNT_BY_Email(new NotFoundAccountByEmailException(
            "signInId", "incorrect",
            "입력한 이메일을 사용하는 계정을 찾을 수 없습니다. 사용자의 이메일을 확인하고 다시 시도하세요.")),
    NOT_FOUND_ACCOUNT_BY_Phone_Number(new NotFoundAccountByPhoneNumberException(
            "password", "incorrect",
            "입력한 휴대폰 번호를 사용하는 계정을 찾을 수 없습니다. 사용자의 휴대폰 번호를 확인하고 다시 시도하세요.")),
    INCORRECT_PASSWORD(new IncorrectPasswordException(
            "password", "incorrect", "잘못된 비밀번호입니다. 다시 확인하세요.")),

    // 회원가입 관련 에러
    DUPLICATE_NICKNAME(new DuplicateNicknameException(
            "nickname", "duplicate", "다른 계정에서 이미 사용중인 사용자 이름입니다.")),
    DUPLICATE_EMAIL(new DuplicateEmailException(
            "phoneNumberOrEmail", "duplicate", "다른 계정에서 이미 사용중인 이메일입니다.")),
    DUPLICATE_PHONE_NUMBER(new DuplicateEmailException(
            "phoneNumberOrEmail", "duplicate", "다른 계정에서 이미 사용중인 휴대폰 번호입니다.")),
    INVALID_NICKNAME_BY_NUMBER(new InvalidNicknameByNumberException(
            "nickname", "invalid", "사용자의 이름에 숫자만 포함할 수는 없습니다.")),
    INVALID_NICKNAME_BY_STRING(new InvalidNicknameByStringException(
            "nickname", "invalid", "사용자 이름에는 문자, 숫자, 밑줄 및 마침표만 사용할 수 있습니다.")),
    INVALID_EMAIL(new InvalidEmailException(
            "phoneNumberOrEmail", "invalid", "유효한 이메일 주소를 입력하세요.")),
    INVALID_PHONE_NUMBER(new InvalidPhoneNumberException(
            "phoneNumberOrEmail", "invalid", "유효한 휴대폰 번호를 입력하세요."));


    private final CustomException exception;
}
