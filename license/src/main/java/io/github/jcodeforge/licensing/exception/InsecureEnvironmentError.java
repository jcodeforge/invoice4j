package io.github.jcodeforge.licensing.exception;

@SuppressWarnings("unused")
public class InsecureEnvironmentError extends Error {

    /**
     * Constructor.
     *
     * @param message The message
     * @param cause The cause
     */
    public InsecureEnvironmentError(final String message, final Throwable cause)
    {
        super("The license manager was activated in an insecure environment. " + message, cause);
    }

    /**
     * Constructor.
     *
     * @param cause The cause
     */
    public InsecureEnvironmentError(final SecurityException cause)
    {
        super("The license manager was activated in an insecure environment. A security manager has already been " +
              "installed, but it allows reflection access to the license cache and doesn't allow a new security " +
              "manager to be installed.", cause);
    }
}
