package toyproject.instragram.common.exception.signup;

import toyproject.instragram.common.exception.CustomFormException;

public class InvalidPhoneNumberException extends CustomFormException {
    public InvalidPhoneNumberException(String message) {
        super(message);
    }

    public InvalidPhoneNumberException(String message, String field) {
        super(message, field);
    }

    public InvalidPhoneNumberException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
