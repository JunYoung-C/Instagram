package toyproject.instragram.common.exception.inheritance.notfoundaccount;

public class NotFoundAccountByEmailException extends NotFoundAccountException {
    public NotFoundAccountByEmailException(String message) {
        super(message);
    }
}
