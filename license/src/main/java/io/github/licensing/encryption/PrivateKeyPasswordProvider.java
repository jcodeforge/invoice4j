package io.github.licensing.encryption;

public final class PrivateKeyPasswordProvider implements PasswordProvider {
    /**
     * Returns the password.
     *
     * @return The password
     */
    @Override
    public char[] getPassword()
    {
        return new char[] {
                's', 'B', 'Q', '8', '5', '1', '8', 'e', 'y', '1', 'y', '8', 'K',
        };
    }
}
