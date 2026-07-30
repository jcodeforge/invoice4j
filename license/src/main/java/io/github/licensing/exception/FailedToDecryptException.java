package io.github.licensing.exception;

@SuppressWarnings("unused")
public class FailedToDecryptException extends RuntimeException {

    public FailedToDecryptException()
    {
        super("Failed to decrypt the data. Either the password was incorrect or the data was corrupt.");
    }

    public FailedToDecryptException(final String message)
    {
        super(message);
    }

    public FailedToDecryptException(final Throwable cause)
    {
        super("Failed to decrypt the data. Either the password was incorrect or the data was corrupt.", cause);
    }

    public FailedToDecryptException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
