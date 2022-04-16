package toyproject.instragram.common.exception.form.signup;

import toyproject.instragram.common.exception.form.CustomFormException;

public class DuplicatePhoneNumberException extends CustomFormException {
    public DuplicatePhoneNumberException(String message) {
        super(message);
    }

    public DuplicatePhoneNumberException(String message, String field) {
        super(message, field);
    }

    public DuplicatePhoneNumberException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
