package toyproject.instragram.common.exception.signup;

import toyproject.instragram.common.exception.CustomException;

public class InvalidNicknameByNumberException extends CustomException {
    public InvalidNicknameByNumberException(String message) {
        super(message);
    }
}
