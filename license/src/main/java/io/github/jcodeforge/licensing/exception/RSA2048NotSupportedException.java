package io.github.jcodeforge.licensing.exception;

@SuppressWarnings("unused")
public class RSA2048NotSupportedException extends RuntimeException {
    /**
     * Constructor.
     */
    public RSA2048NotSupportedException()
    {
        super("2048-bit RSA Security is not supported on this system.");
    }

    /**
     * Constructor.
     *
     * @param message The message
     */
    public RSA2048NotSupportedException(final String message)
    {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param cause The cause
     */
    public RSA2048NotSupportedException(final Throwable cause)
    {
        super("2048-bit RSA Security is not supported on this system.", cause);
    }

    /**
     * Constructor.
     *
     * @param message The message
     * @param cause The cause
     */
    public RSA2048NotSupportedException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
