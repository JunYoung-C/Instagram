package toyproject.instragram.common.exception.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import toyproject.instragram.common.exception.CustomException;

@Getter
@RequiredArgsConstructor
public enum ApiExceptionType {
    UNKNOWN(new UnknownException("알 수 없는 에러가 발생했습니다."));

    private final CustomException exception;
}
