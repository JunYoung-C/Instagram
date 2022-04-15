package toyproject.instragram.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import toyproject.instragram.common.exception.inheritance.NotFoundAccountException;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
    NOT_FOUND_ACCOUNT(new NotFoundAccountException(
            "입력한 사용자 이름을 사용하는 계정을 찾을 수 없습니다. 사용자 이름을 확인하고 다시 시도하세요."));

    private final CustomException exception;
}
