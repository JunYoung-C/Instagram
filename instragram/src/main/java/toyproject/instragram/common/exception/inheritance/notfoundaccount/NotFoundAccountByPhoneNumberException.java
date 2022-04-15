package toyproject.instragram.common.exception.inheritance.notfoundaccount;

public class NotFoundAccountByPhoneNumberException extends NotFoundAccountException {
    public NotFoundAccountByPhoneNumberException(String message) {
        super(message);
    }
}
