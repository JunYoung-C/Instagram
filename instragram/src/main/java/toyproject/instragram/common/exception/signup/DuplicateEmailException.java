package toyproject.instragram.common.exception.signup;

import toyproject.instragram.common.exception.CustomFormException;

public class DuplicateEmailException extends CustomFormException {
    public DuplicateEmailException(String message) {
        super(message);
    }

    public DuplicateEmailException(String message, String field) {
        super(message, field);
    }

    public DuplicateEmailException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
