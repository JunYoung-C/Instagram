package toyproject.instragram.common.exception.api.httpstatusexception;

import org.springframework.http.HttpStatus;
import toyproject.instragram.common.exception.api.CustomApiException;

public class BadRequestException extends CustomApiException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
