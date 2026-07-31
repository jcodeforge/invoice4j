package io.github.jcodeforge.invoice4jbase.exceptions;

public class SchemaValidationException extends RuntimeException {

    public SchemaValidationException(String message) {
        super(message);
    }

    public SchemaValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchemaValidationException(Throwable cause) {
        super(cause);
    }
}
