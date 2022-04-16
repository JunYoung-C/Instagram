package toyproject.instragram.common.exception.signup;

import toyproject.instragram.common.exception.CustomException;

public class InvalidEmailException extends CustomException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
