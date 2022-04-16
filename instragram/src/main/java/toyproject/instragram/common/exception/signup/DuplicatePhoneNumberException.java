package toyproject.instragram.common.exception.signup;

import toyproject.instragram.common.exception.CustomException;

public class DuplicatePhoneNumberException extends CustomException {
    public DuplicatePhoneNumberException(String message) {
        super(message);
    }
}
