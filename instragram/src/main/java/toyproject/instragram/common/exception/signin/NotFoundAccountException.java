package toyproject.instragram.common.exception.signin;

import toyproject.instragram.common.exception.CustomException;

public class NotFoundAccountException extends CustomException {
    public NotFoundAccountException(String message) {
        super(message);
    }
}
