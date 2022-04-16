package toyproject.instragram.common.exception.signin;

import toyproject.instragram.common.exception.CustomException;

public class NotFoundAccountByNicknameException extends CustomException {
    public NotFoundAccountByNicknameException(String message) {
        super(message);
    }
}
