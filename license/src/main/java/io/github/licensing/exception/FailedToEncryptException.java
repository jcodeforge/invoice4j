package io.github.licensing.exception;

@SuppressWarnings("unused")
public class FailedToEncryptException extends RuntimeException {

    public FailedToEncryptException()
    {
        super("Failed to decrypt the data. Either the password was incorrect or the data was corrupt.");
    }

    public FailedToEncryptException(final String message)
    {
        super(message);
    }

    public FailedToEncryptException(final Throwable cause)
    {
        super("Failed to decrypt the data. Either the password was incorrect or the data was corrupt.", cause);
    }

    public FailedToEncryptException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
