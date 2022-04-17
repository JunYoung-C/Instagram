package toyproject.instragram.common.exception.api.httpstatusexception;

import org.springframework.http.HttpStatus;
import toyproject.instragram.common.exception.api.CustomApiException;

public class InternalServerException extends CustomApiException {
    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(String message, HttpStatus status) {
        super(message, status);
    }
}
