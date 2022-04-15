package toyproject.instragram.common.exception.signin;

public class NotFoundAccountByEmailException extends NotFoundAccountException {
    public NotFoundAccountByEmailException(String message) {
        super(message);
    }
}
