package toyproject.instragram.common.exception.inheritance.notfoundaccount;

import toyproject.instragram.common.exception.CustomException;

public class UnknownException extends CustomException {
    public UnknownException(String message) {
        super(message);
    }
}
