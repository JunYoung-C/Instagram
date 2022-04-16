package toyproject.instragram.common.exception.signup;

import toyproject.instragram.common.exception.CustomException;

public class DuplicateNicknameException extends CustomException {
    public DuplicateNicknameException(String message) {
        super(message);
    }
}
