package toyproject.instragram.common.exception.inheritance;

import toyproject.instragram.common.exception.CustomException;

public class NotFoundAccountException extends CustomException {
    public NotFoundAccountException(String message) {
        super(message);
    }
}
