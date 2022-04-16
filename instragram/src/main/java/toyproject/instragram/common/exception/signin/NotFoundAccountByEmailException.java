package toyproject.instragram.common.exception.signin;

import toyproject.instragram.common.exception.CustomException;

public class NotFoundAccountByEmailException extends CustomException {
    public NotFoundAccountByEmailException(String message) {
        super(message);
    }
}
