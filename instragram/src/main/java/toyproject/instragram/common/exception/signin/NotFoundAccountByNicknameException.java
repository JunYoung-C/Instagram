package toyproject.instragram.common.exception.signin;

import toyproject.instragram.common.exception.CustomFormException;

public class NotFoundAccountByNicknameException extends CustomFormException {
    public NotFoundAccountByNicknameException(String message) {
        super(message);
    }

    public NotFoundAccountByNicknameException(String message, String field) {
        super(message, field);
    }

    public NotFoundAccountByNicknameException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
