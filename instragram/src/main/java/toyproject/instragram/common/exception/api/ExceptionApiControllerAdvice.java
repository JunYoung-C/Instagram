package toyproject.instragram.common.exception.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionApiControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleCustomApiException(CustomApiException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(e.getStatus())
                .body(new ExceptionResponse(e.getMessage()));
    }
}
