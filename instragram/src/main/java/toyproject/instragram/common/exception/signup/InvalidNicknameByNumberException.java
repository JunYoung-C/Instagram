package toyproject.instragram.common.exception.signup;

import toyproject.instragram.common.exception.CustomFormException;

public class InvalidNicknameByNumberException extends CustomFormException {
    public InvalidNicknameByNumberException(String message) {
        super(message);
    }

    public InvalidNicknameByNumberException(String message, String field) {
        super(message, field);
    }

    public InvalidNicknameByNumberException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
