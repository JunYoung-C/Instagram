package toyproject.instragram.common.exception.signup;

import toyproject.instragram.common.exception.CustomException;

public class InvalidPhoneNumberException extends CustomException {
    public InvalidPhoneNumberException(String message) {
        super(message);
    }
}
