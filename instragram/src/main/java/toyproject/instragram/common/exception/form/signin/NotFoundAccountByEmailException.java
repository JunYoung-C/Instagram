package toyproject.instragram.common.exception.form.signin;

import toyproject.instragram.common.exception.form.CustomFormException;

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
