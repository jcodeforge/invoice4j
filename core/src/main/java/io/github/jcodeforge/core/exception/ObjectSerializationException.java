package io.github.jcodeforge.core.exception;

@SuppressWarnings("unused")
public class ObjectSerializationException extends RuntimeException {

    public ObjectSerializationException() {
        super("An I/O error occurred while writing the object to the byte array.");
    }

    public ObjectSerializationException(final String message) {
        super(message);
    }

    public ObjectSerializationException(final Throwable cause) {
        super("An I/O error occurred while writing the object to the byte array.", cause);
    }

    public ObjectSerializationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
