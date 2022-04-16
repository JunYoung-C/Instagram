package toyproject.instragram.common.exception.signin;

import toyproject.instragram.common.exception.CustomFormException;

public class NotFoundAccountByEmailException extends CustomFormException {
    public NotFoundAccountByEmailException(String message) {
        super(message);
    }

    public NotFoundAccountByEmailException(String message, String field) {
        super(message, field);
    }

    public NotFoundAccountByEmailException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
