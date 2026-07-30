package io.github.licensing.exception;

@SuppressWarnings("unused")
public class KeyNotFoundException extends RuntimeException {
    /**
     * Constructor.
     */
    public KeyNotFoundException()
    {
        super("The key file could not be found.");
    }

    /**
     * Constructor.
     *
     * @param message The message
     */
    public KeyNotFoundException(final String message)
    {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param cause The cause
     */
    public KeyNotFoundException(final Throwable cause)
    {
        super("The key file could not be found.", cause);
    }

    /**
     * Constructor.
     *
     * @param message The message
     * @param cause The cause
     */
    public KeyNotFoundException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
