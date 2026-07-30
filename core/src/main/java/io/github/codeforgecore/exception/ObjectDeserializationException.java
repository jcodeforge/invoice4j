package io.github.codeforgecore.exception;

/**
 * This class is thrown when an error occurs while reading an object from a
 * byte array. The general form of this error indicates an I/O problem.
 * Sub-classes of this exception indicate more specific problems.
 */
@SuppressWarnings("unused")
public class ObjectDeserializationException extends RuntimeException {

    public ObjectDeserializationException() {
        super("An error occurred while reading the object from the byte array.");
    }

    public ObjectDeserializationException(final String message)
    {
        super(message);
    }

    public ObjectDeserializationException(final Throwable cause) {
        super("An error occurred while reading the object from the byte array.", cause);
    }

    public ObjectDeserializationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
