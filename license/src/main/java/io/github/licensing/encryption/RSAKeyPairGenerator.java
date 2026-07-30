package io.github.licensing.encryption;

import io.github.licensing.exception.*;
import java.io.File;
import java.io.IOException;
import java.security.*;

/**
 * The generator one should use to create public/private key pairs for use with
 * the application.
 */
public final class RSAKeyPairGenerator implements RSAKeyPairGeneratorInterface {
    /**
     * Generates a key pair with RSA 2048-bit security.
     *
     * @return a public/private key pair.
     *
     * @throws RSA2048NotSupportedException if RSA or 2048-bit encryption are not supported.
     */
    private KeyPair generateKeyPair() throws RSA2048NotSupportedException {
        KeyPairGenerator keyGenerator;
        try {
            keyGenerator = KeyPairGenerator.getInstance(KeyFileUtils.KEY_ALGORITHM);
        }
        catch(final NoSuchAlgorithmException e) {
            throw new RSA2048NotSupportedException(
                    "RSA keys are not supported on your system. Contact your system administrator for assistance.",
                    e
            );
        }

        try {
            keyGenerator.initialize(RSAKeyPairGeneratorInterface.KEY_SIZE);
        }
        catch(final InvalidParameterException e) {
            throw new RSA2048NotSupportedException(
                "RSA is supported on your system, but 2048-bit keys are not. Contact your system administrator for " +
                "assistance.",
                e
            );
        }

        return keyGenerator.generateKeyPair();
    }

    /**
     * Saves the key pair specified to output files specified, encrypting both with the specified password.
     *
     * @param privateKeyOutputFileName The name of the file to save the encrypted private key to
     * @param publicKeyOutputFileName The name of the file to save the encrypted public key to
     * @param password The password to encrypt both keys with
     *
     * @throws IOException if an error occurs while writing to the files.
     * @throws AlgorithmNotSupportedException If the encryption algorithm is not supported
     * @throws InappropriateKeyException If the public or private keys are invalid
     * @throws InappropriateKeySpecificationException If the public or private keys are invalid
     */
    @Override
    public void saveKeyPairToFiles(String privateKeyOutputFileName, String publicKeyOutputFileName,
                                   char[] password) throws IOException, AlgorithmNotSupportedException,
            InappropriateKeyException, InappropriateKeySpecificationException {

        KeyPair keyPair = generateKeyPair();

        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        KeyFileUtils.writeEncryptedPrivateKey(privateKey, new File(privateKeyOutputFileName),
                password);
        KeyFileUtils.writeEncryptedPublicKey(publicKey, new File(publicKeyOutputFileName),
                password);
    }
}
