package io.github.licensing.exception;

@SuppressWarnings("unused")
public class InappropriateKeyException extends RuntimeException {

    public InappropriateKeyException()
    {
        super("The specified key is inappropriate for the cipher.");
    }

    public InappropriateKeyException(final String message)
    {
        super(message);
    }

    public InappropriateKeyException(final Throwable cause)
    {
        super("The specified key is inappropriate for the cipher.", cause);
    }

    public InappropriateKeyException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
