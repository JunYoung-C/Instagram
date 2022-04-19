package toyproject.instragram.common.exception.form.addpost;

import toyproject.instragram.common.exception.form.CustomFormException;

public class InvalidImageExtensionException extends CustomFormException {
    public InvalidImageExtensionException(String message) {
        super(message);
    }

    public InvalidImageExtensionException(String message, String field) {
        super(message, field);
    }

    public InvalidImageExtensionException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
