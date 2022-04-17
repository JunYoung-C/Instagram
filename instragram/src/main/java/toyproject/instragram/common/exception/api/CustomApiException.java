package toyproject.instragram.common.exception.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import toyproject.instragram.common.exception.CustomException;

@Getter
public class CustomApiException extends CustomException {
    private HttpStatus status;

    public CustomApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
