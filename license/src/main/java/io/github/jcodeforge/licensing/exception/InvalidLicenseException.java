package io.github.jcodeforge.licensing.exception;

@SuppressWarnings("unused")
public class InvalidLicenseException extends RuntimeException {
    /**
     * Constructor.
     */
    public InvalidLicenseException()
    {
        super("The license is not valid.");
    }

    /**
     * Constructor.
     *
     * @param message The message
     */
    public InvalidLicenseException(final String message)
    {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param cause The cause
     */
    public InvalidLicenseException(final Throwable cause)
    {
        super("The license is not valid.", cause);
    }

    /**
     * Constructor.
     *
     * @param message The message
     * @param cause The cause
     */
    public InvalidLicenseException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
