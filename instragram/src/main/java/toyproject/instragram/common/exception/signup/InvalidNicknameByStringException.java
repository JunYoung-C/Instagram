package toyproject.instragram.common.exception.signup;

import toyproject.instragram.common.exception.CustomException;

public class InvalidNicknameByStringException extends CustomException {
    public InvalidNicknameByStringException(String message) {
        super(message);
    }
}
