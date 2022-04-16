package toyproject.instragram.common.exception.signin;

import toyproject.instragram.common.exception.CustomException;

public class NotFoundAccountByPhoneNumberException extends CustomException {
    public NotFoundAccountByPhoneNumberException(String message) {
        super(message);
    }
}
