package toyproject.instragram.common.exception.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionApiControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleCustomApiException(CustomApiException e) {
        return ResponseEntity.status(e.getStatus())
                .body(new ExceptionResponse(e.getMessage()));
    }
}
