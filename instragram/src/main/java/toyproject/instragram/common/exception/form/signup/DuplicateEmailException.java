package toyproject.instragram.common.exception.form.signup;

import toyproject.instragram.common.exception.form.CustomFormException;

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
