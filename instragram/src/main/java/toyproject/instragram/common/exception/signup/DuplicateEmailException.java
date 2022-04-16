package toyproject.instragram.common.exception.signup;

import toyproject.instragram.common.exception.CustomException;

public class DuplicateEmailException extends CustomException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
