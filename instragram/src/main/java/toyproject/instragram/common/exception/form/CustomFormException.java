package toyproject.instragram.common.exception.form;

import lombok.Getter;
import toyproject.instragram.common.exception.CustomException;

@Getter
public class CustomFormException extends CustomException {
    private String field;
    private String errorCode;

    public CustomFormException(String message) {
        super(message);
    }

    public CustomFormException(String message, String field) {
        super(message);
        this.field = field;
    }

    public CustomFormException(String message, String field, String errorCode) {
        super(message);
        this.field = field;
        this.errorCode = errorCode;
    }
}
