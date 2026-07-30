package io.github.jcodeforge.licensing.exception;

@SuppressWarnings("unused")
public class CorruptSignatureException extends RuntimeException {

    public CorruptSignatureException()
    {
        super("The signature provided is corrupt or not a signature.");
    }

    public CorruptSignatureException(final String message)
    {
        super(message);
    }

    public CorruptSignatureException(final Throwable cause)
    {
        super("The signature provided is corrupt or not a signature.", cause);
    }

    public CorruptSignatureException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
