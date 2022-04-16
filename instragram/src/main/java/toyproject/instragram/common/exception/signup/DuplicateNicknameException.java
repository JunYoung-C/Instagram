package toyproject.instragram.common.exception.signup;

import toyproject.instragram.common.exception.CustomFormException;

public class DuplicateNicknameException extends CustomFormException {
    public DuplicateNicknameException(String message) {
        super(message);
    }

    public DuplicateNicknameException(String message, String field) {
        super(message, field);
    }

    public DuplicateNicknameException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
