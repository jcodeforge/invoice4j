package io.github.licensing.exception;

@SuppressWarnings("unused")
public class InvalidSignatureException extends RuntimeException {
    /**
     * Constructor.
     */
    public InvalidSignatureException()
    {
        super("The signature provided is invalid and cannot be verified.");
    }

    /**
     * Constructor.
     *
     * @param message The message
     */
    public InvalidSignatureException(final String message)
    {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param cause The cause
     */
    public InvalidSignatureException(final Throwable cause)
    {
        super("The signature provided is invalid and cannot be verified.", cause);
    }

    /**
     * Constructor.
     *
     * @param message The message
     * @param cause The cause
     */
    public InvalidSignatureException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
