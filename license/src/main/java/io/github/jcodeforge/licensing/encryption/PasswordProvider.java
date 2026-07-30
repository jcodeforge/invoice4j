package io.github.jcodeforge.licensing.encryption;

/**
 * This specifies an interface for providing a password for decrypting a key or license. Every user of this library
 * must implement this (one to three times).
 */
@SuppressWarnings({"UnusedReturnValue", "unused"})
public interface PasswordProvider
{
    /**
     * When integrating the license manager in your application, you must
     * implement this interface. It should return the password that you used
     * when encrypting the key or license.<br>
     * <br>
     * Also, do not implement this using any strings, i.e:<br>
     * <br>
     * <code>
     * return "myInsecurePassword".toCharArray();
     * </code><br>
     * <br>
     * Strings persist in memory for a long time, and the string's contents
     * cannot be overwritten. Using only a character array allows the license
     * manager to overwrite the password when it is finished with it, thus
     * keeping it in the memory pool for as short a time as possible.<br>
     * <br>
     * Finally, the password must be between six and 32 characters
     * (inclusively). We recommend it be at least 10 characters.<br>
     * <br>
     * It is <em>imperative</em> that you obfuscate the bytecode for the
     * implementation of this class. It is also imperative that the character
     * array exist only for the life of this method (i.e., DO NOT store it as
     * an instance or class field).
     *
     * @return the password in character array form.
     */
    char[] getPassword();
}
