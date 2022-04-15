package toyproject.instragram.common.exception.signin;

public class NotFoundAccountByPhoneNumberException extends NotFoundAccountException {
    public NotFoundAccountByPhoneNumberException(String message) {
        super(message);
    }
}
