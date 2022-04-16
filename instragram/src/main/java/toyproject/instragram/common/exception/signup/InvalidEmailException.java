package toyproject.instragram.common.exception.signup;

import toyproject.instragram.common.exception.CustomFormException;

public class InvalidEmailException extends CustomFormException {
    public InvalidEmailException(String message) {
        super(message);
    }

    public InvalidEmailException(String message, String field) {
        super(message, field);
    }

    public InvalidEmailException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
