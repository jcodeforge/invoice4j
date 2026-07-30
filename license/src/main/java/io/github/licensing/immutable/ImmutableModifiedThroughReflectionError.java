package io.github.licensing.immutable;

import java.io.Serial;

/**
 * This exception is thrown when an unmodifiable list is modified illegally
 * through exception.
 */
@SuppressWarnings("unused")
public class ImmutableModifiedThroughReflectionError extends Error {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String defaultMessage =
        "This immutable object appears to have been modified through reflection.";

    /**
     * Constructor.
     */
    public ImmutableModifiedThroughReflectionError()
    {
        super(ImmutableModifiedThroughReflectionError.defaultMessage);
    }

    /**
     * Constructor.
     *
     * @param message The message
     */
    public ImmutableModifiedThroughReflectionError(final String message)
    {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param cause The cause
     */
    public ImmutableModifiedThroughReflectionError(final Throwable cause)
    {
        super(ImmutableModifiedThroughReflectionError.defaultMessage, cause);
    }

    /**
     * Constructor.
     *
     * @param message The message
     * @param cause The cause
     */
    public ImmutableModifiedThroughReflectionError(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
