package io.github.licensing.encryption;

import io.github.licensing.exception.AlgorithmNotSupportedException;
import io.github.licensing.exception.InappropriateKeyException;
import io.github.licensing.exception.InappropriateKeySpecificationException;
import java.io.IOException;

/**
 * An interface for the key pair generator to make unit testing possible.
 */
@SuppressWarnings({"UnusedReturnValue", "unused"})
public interface RSAKeyPairGeneratorInterface {
    /**
     * The key size we use for RSA key pairs. Anything larger is not portable in Java at this time.
     */
     int KEY_SIZE = 2048;

    /**
     * Saves the key pair specified to output files specified, encrypting both with the specified password.
     *
     * @param privateOutputFileName The name of the file to save the encrypted private key to
     * @param publicOutputFileName The name of the file to save the encrypted public key to
     * @param password The password to encrypt both keys with
     *
     * @throws IOException if an error occurs while writing to the files.
     * @throws AlgorithmNotSupportedException If the encryption algorithm is not supported
     * @throws InappropriateKeyException If the public or private keys are invalid
     * @throws InappropriateKeySpecificationException If the public or private keys are invalid
     */
    void saveKeyPairToFiles(String privateOutputFileName, String publicOutputFileName,
                            char[] password) throws IOException, AlgorithmNotSupportedException,
            InappropriateKeyException, InappropriateKeySpecificationException;

}
