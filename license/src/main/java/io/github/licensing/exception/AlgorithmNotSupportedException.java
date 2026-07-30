package io.github.licensing.exception;

@SuppressWarnings("unused")
public class AlgorithmNotSupportedException extends RuntimeException {

    public AlgorithmNotSupportedException()
    {
        super("The specified algorithm is not supported on this system.");
    }

    public AlgorithmNotSupportedException(final String algorithm)
    {
        super("The algorithm \"" + algorithm + "\" is not supported on this system.");
    }

    public AlgorithmNotSupportedException(final Throwable cause)
    {
        super(cause);
    }

    public AlgorithmNotSupportedException(final String algorithm, final Throwable cause)
    {
        super("The algorithm \"" + algorithm + "\" is not supported on this system.", cause);
    }
}
