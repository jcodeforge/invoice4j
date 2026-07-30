package io.github.jcodeforge.licensing.exception;

@SuppressWarnings("unused")
public class InappropriateKeySpecificationException extends RuntimeException {

    /**
     * Constructor.
     */
    public InappropriateKeySpecificationException()
    {
        super("The specified key specification is inappropriate for the factory.");
    }

    /**
     * Constructor.
     *
     * @param message The message
     */
    public InappropriateKeySpecificationException(final String message)
    {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param cause The cause
     */
    public InappropriateKeySpecificationException(final Throwable cause)
    {
        super("The specified key specification is inappropriate for the factory.", cause);
    }

    /**
     * Constructor.
     *
     * @param message The message
     * @param cause The cause
     */
    public InappropriateKeySpecificationException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
