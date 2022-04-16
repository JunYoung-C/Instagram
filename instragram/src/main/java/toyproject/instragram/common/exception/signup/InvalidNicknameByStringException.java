package toyproject.instragram.common.exception.signup;

import toyproject.instragram.common.exception.CustomFormException;

public class InvalidNicknameByStringException extends CustomFormException {
    public InvalidNicknameByStringException(String message) {
        super(message);
    }

    public InvalidNicknameByStringException(String message, String field) {
        super(message, field);
    }

    public InvalidNicknameByStringException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
