package io.github.licensing.exception;

@SuppressWarnings("unused")
public class ExpiredLicenseException extends InvalidLicenseException {

    public ExpiredLicenseException()
    {
        super("The license has expired.");
    }

    public ExpiredLicenseException(final String message)
    {
        super(message);
    }

    public ExpiredLicenseException(final Throwable cause)
    {
        super("The license has expired.", cause);
    }

    public ExpiredLicenseException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
