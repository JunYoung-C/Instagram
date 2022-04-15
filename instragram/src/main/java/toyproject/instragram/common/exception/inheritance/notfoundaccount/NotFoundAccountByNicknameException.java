package toyproject.instragram.common.exception.inheritance.notfoundaccount;

public class NotFoundAccountByNicknameException extends NotFoundAccountException {
    public NotFoundAccountByNicknameException(String message) {
        super(message);
    }
}
