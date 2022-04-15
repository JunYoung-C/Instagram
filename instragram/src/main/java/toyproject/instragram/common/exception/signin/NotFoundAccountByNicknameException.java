package toyproject.instragram.common.exception.signin;

public class NotFoundAccountByNicknameException extends NotFoundAccountException {
    public NotFoundAccountByNicknameException(String message) {
        super(message);
    }
}
