package toyproject.instragram.common.exception.signin;

import toyproject.instragram.common.exception.CustomException;

public class IncorrectPasswordException extends CustomException {
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
