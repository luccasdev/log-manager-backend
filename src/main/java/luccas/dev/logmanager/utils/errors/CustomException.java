package luccas.dev.logmanager.utils.errors;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final HttpStatus status;

    public CustomException(String message, HttpStatus status) {
        super(message, null, false, false);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}