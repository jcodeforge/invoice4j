package io.github.licensing.encryption;

import io.github.licensing.exception.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * A class of utility methods for reading and writing private and public keys
 * to license files.
 */
public final class KeyFileUtils {
    /**
     * The standard key algorithm used for all of our keys.
     */
    public static final String KEY_ALGORITHM = "RSA";

    private static final int MINIMUM_PADDED_LENGTH = 20;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private static final char[] DEFAULT_PASSPHRASE = {
            'j', '4', 'K', 'g', 'U', '3', '0', '5', 'P', 'Z', 'p', '\'', 't',
            '.', '"', '%', 'o', 'r', 'd', 'A', 'Y', '7', 'q', '*', '?', 'z',
            '9', '%', '8', ']', 'a', 'm', 'N', 'L', '(', '0', 'W', 'x', '5',
            'e', 'G', '4', '9', 'b', '1', 's', 'R', 'j', '(', '^', ';', '8',
            'K', 'g', '2', 'w', '0', 'E', 'o', 'M',
    };

    private static final byte[] SALT = {
            (byte) 0xA9, (byte) 0xA2, (byte) 0xB5, (byte) 0xDE,
            (byte) 0x2A, (byte) 0x8A, (byte) 0x9A, (byte) 0xE6,
    };

    private static final int ITERATION_COUNT = 1024;

    // must be 128, 192, 256; 128 is maximum without "unlimited strength" JCE policy files
    private static final int AES_KEY_LENGTH = 128;

    private static final int INTERMEDIATE_KEY_LENGTH = AES_KEY_LENGTH / 8;

    private static final String AES_WITH_SHA_1_DIGEST = "AES With SHA-1 digest";

    private static Cipher sDefaultEncryptionCipher;
    private static Cipher sDefaultDecryptionCipher;

    /**
     * Encrypts and writes the private key to this file.
     *
     * @param privateKey The private key
     * @param file The file
     * @param passphrase The passphrase with which to protect the key
     *
     * @throws IOException if writing fails.
     */
    public static void writeEncryptedPrivateKey(final PrivateKey privateKey, final File file,
                                                final char[] passphrase) throws IOException {
        FileUtils.writeByteArrayToFile(file, writeEncryptedPrivateKey(privateKey, passphrase));
    }

    /**
     * Encrypts and writes the private key to a byte array and returns it.
     *
     * @param privateKey The private key
     * @param passphrase The passphrase with which to protect the key
     *
     * @return the encrypted private key bytes.
     */
    public static byte[] writeEncryptedPrivateKey(final PrivateKey privateKey,
                                                  final char[] passphrase) {
        final PKCS8EncodedKeySpec pkcs8EncodedKeySpec =
                new PKCS8EncodedKeySpec(privateKey.getEncoded());
        return encryptRaw(pkcs8EncodedKeySpec.getEncoded(), passphrase);
    }

    /**
     * Encrypts and writes the public key to this file.
     *
     * @param publicKey The public key
     * @param file The file
     * @param passphrase The passphrase with which to protect the key
     *
     * @throws IOException if writing fails.
     */
    public static void writeEncryptedPublicKey(final PublicKey publicKey, final File file,
                                               final char[] passphrase) throws IOException {
        FileUtils.writeByteArrayToFile(file, writeEncryptedPublicKey(publicKey, passphrase));
    }

    /**
     * Encrypts and writes the public key to a byte array and returns it.
     *
     * @param publicKey The public key
     * @param passphrase The passphrase with which to protect the key
     *
     * @return the encrypted public key bytes.
     */
    public static byte[] writeEncryptedPublicKey(final PublicKey publicKey, final char[] passphrase) {
        final X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
        return encryptRaw(x509EncodedKeySpec.getEncoded(), passphrase);
    }

    /**
     * Reads and decrypts the encrypted private key from this file.
     *
     * @param file The file
     * @param passphrase The passphrase with which the key was protected
     *
     * @return the read, decrypted private key.
     * @throws IOException if reading fails.
     */
    public static PrivateKey readEncryptedPrivateKey(final File file, final char[] passphrase)
            throws IOException {
        return readEncryptedPrivateKey(FileUtils.readFileToByteArray(file), passphrase);
    }

    /**
     * Reads and decrypts the encrypted private key from the provided bytes.
     *
     * @param fileContents The encrypted key bytes
     * @param passphrase The passphrase with which the key was protected
     *
     * @return the decrypted private key.
     */
    public static PrivateKey readEncryptedPrivateKey(final byte[] fileContents, final char[] passphrase)
    {
        final PKCS8EncodedKeySpec privateKeySpec =
                new PKCS8EncodedKeySpec(decryptRaw(fileContents, passphrase));
        try {
            return KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(privateKeySpec);
        }
        catch(final NoSuchAlgorithmException e) {
            throw new AlgorithmNotSupportedException(KEY_ALGORITHM, e);
        }
        catch(final InvalidKeySpecException e) {
            throw new InappropriateKeySpecificationException(e);
        }
    }

    /**
     * Reads and decrypts the encrypted public key from this file.
     *
     * @param file The file
     * @param passphrase The passphrase with which the key was protected
     *
     * @return the read, decrypted public key.
     *
     * @throws IOException if reading fails.
     */
    public static PublicKey readEncryptedPublicKey(final File file, final char[] passphrase)
            throws IOException
    {
        return readEncryptedPublicKey(FileUtils.readFileToByteArray(file), passphrase);
    }

    /**
     * Reads and decrypts the encrypted public key from the provided bytes.
     *
     * @param fileContents The encrypted key bytes
     * @param passphrase The passphrase with which the key was protected
     *
     * @return the decrypted public key.
     */
    public static PublicKey readEncryptedPublicKey(final byte[] fileContents, final char[] passphrase)
    {
        final X509EncodedKeySpec publicKeySpec =
                new X509EncodedKeySpec(decryptRaw(fileContents, passphrase));
        try {
            return KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(publicKeySpec);
        }
        catch(final NoSuchAlgorithmException e) {
            throw new AlgorithmNotSupportedException(KEY_ALGORITHM, e);
        }
        catch(final InvalidKeySpecException e) {
            throw new InappropriateKeySpecificationException(e);
        }
    }

    /**
     * Encrypt the plain-text string using the default passphrase.
     * For encrypting, the data will first be padded to a safe number of
     * bytes with randomized data.
     *
     * @param unencrypted The plain-text string to encrypt
     *
     * @return the encrypted string Base64-encoded.
     *
     * @see #pad(byte[], int)
     */
    public static String encrypt(final String unencrypted) {
        return encrypt(unencrypted.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Encrypt the plain-text string. For encrypting, the
     * string will first be padded to a safe number of
     * characters with randomized data.
     *
     * @param unencrypted The plain-text string to encrypt
     * @param passphrase The passphrase to encrypt the data with
     *
     * @return the encrypted string Base64-encoded.
     */
    public static String encrypt(final String unencrypted, final char[] passphrase) {
        return encrypt(unencrypted.getBytes(StandardCharsets.UTF_8), passphrase);
    }

    /**
     * Encrypt the binary data using the default passphrase.
     * For encrypting, the data will first be padded to a safe number of
     * bytes with randomized data.
     *
     * @param unencrypted The binary data to encrypt
     *
     * @return the encrypted string Base64-encoded.
     *
     * @see #pad(byte[], int)
     */
    public static String encrypt(final byte[] unencrypted) {
        return new String(
                Base64.encodeBase64URLSafe(encryptRaw(unencrypted)), StandardCharsets.UTF_8
        );
    }

    /**
     * Encrypt the binary data. For encrypting, the
     * data will first be padded to a safe number of
     * bytes with randomized data.
     *
     * @param unencrypted The binary data to encrypt
     * @param passphrase The passphrase to encrypt the data with
     *
     * @return the encrypted string Base64-encoded.
     *
     * @see #pad(byte[], int)
     */
    public static String encrypt(final byte[] unencrypted, final char[] passphrase) {
        return new String(
                Base64.encodeBase64URLSafe(encryptRaw(unencrypted, passphrase)), StandardCharsets.UTF_8
        );
    }

    /**
     * Encrypt the binary data using the default passphrase.
     * For encrypting, the data will first be padded to a safe number of
     * bytes with randomized data.
     *
     * @param unencrypted The binary data to encrypt
     *
     * @return the encrypted data.
     *
     * @see #pad(byte[], int)
     */
    public static byte[] encryptRaw(final byte[] unencrypted) {
        try {
            return getDefaultEncryptionCipher().doFinal(pad(unencrypted, MINIMUM_PADDED_LENGTH));
        } catch(final IllegalBlockSizeException | BadPaddingException e) {
            throw new FailedToEncryptException(e);
        }
    }

    /**
     * Encrypt the binary data. For encrypting, the
     * data will first be padded to a safe number of
     * bytes with randomized data.
     *
     * @param unencrypted The binary data to encrypt
     * @param passphrase The passphrase to encrypt the data with
     *
     * @return the encrypted data.
     *
     * @see #pad(byte[], int)
     */
    public static byte[] encryptRaw(final byte[] unencrypted, final char[] passphrase) {
        try {
            return getEncryptionCipher(passphrase).doFinal(pad(unencrypted, MINIMUM_PADDED_LENGTH));
        } catch(final IllegalBlockSizeException | BadPaddingException e) {
            throw new FailedToEncryptException(e);
        }
    }

    /**
     * Decrypt an encrypted string using the default passphrase.
     * Any padded data will be removed from the string prior to its return.
     *
     * @param encrypted The encrypted string to decrypt
     *
     * @return the decrypted string.
     *
     * @throws FailedToDecryptException when the data was corrupt and undecryptable or when the provided decryption
     *     password was incorrect. It is impossible to know which is the actual cause.
     * @see #unPad(byte[])
     */
    public static String decrypt(final String encrypted) {
        return decrypt(Base64.decodeBase64(encrypted));
    }

    /**
     * Decrypt an encrypted string. Any padded data will
     * be removed from the string prior to its return.
     *
     * @param encrypted The encrypted string to decrypt
     * @param passphrase The passphrase to decrypt the string with
     *
     * @return the decrypted string.
     *
     * @throws FailedToDecryptException when the data was corrupt and undecryptable or when the provided decryption
     *     password was incorrect. It is impossible to know which is the actual cause.
     * @see #unPad(byte[])
     */
    public static String decrypt(final String encrypted, final char[] passphrase) {
        return decrypt(Base64.decodeBase64(encrypted), passphrase);
    }

    /**
     * Decrypt encrypted data using the default passphrase.
     * Any padded data will be removed from the string prior to its return.
     *
     * @param encrypted The encrypted data to decrypt
     *
     * @return the decrypted string.
     *
     * @throws FailedToDecryptException when the data was corrupt and undecryptable or when the provided decryption
     *     password was incorrect. It is impossible to know which is the actual cause.
     * @see #unPad(byte[])
     */
    public static String decrypt(final byte[] encrypted) {
        return new String(decryptRaw(encrypted), StandardCharsets.UTF_8);
    }

    /**
     * Decrypt an encrypted data. Any padded data will
     * be removed from the string prior to its return.
     *
     * @param encrypted The encrypted data to decrypt
     * @param passphrase The passphrase to decrypt the data with
     *
     * @return the decrypted string.
     *
     * @throws FailedToDecryptException when the data was corrupt and undecryptable or when the provided decryption
     *     password was incorrect. It is impossible to know which is the actual cause.
     * @see #unPad(byte[])
     */
    public static String decrypt(final byte[] encrypted, final char[] passphrase) {
        return new String(decryptRaw(encrypted, passphrase), StandardCharsets.UTF_8);
    }

    /**
     * Decrypt encrypted data using the default passphrase.
     * Any padded data will be removed from the string prior to its return.
     *
     * @param encrypted The encrypted data to decrypt
     *
     * @return the decrypted binary data.
     *
     * @throws FailedToDecryptException when the data was corrupt and undecryptable or when the provided decryption
     *     password was incorrect. It is impossible to know which is the actual cause.
     * @see #unPad(byte[])
     */
    public static byte[] decryptRaw(final byte[] encrypted) {
        try {
            return unPad(getDefaultDecryptionCipher().doFinal(encrypted));
        } catch(final IllegalBlockSizeException | BadPaddingException e) {
            throw new FailedToDecryptException(e);
        }
    }

    /**
     * Decrypt encrypted data. Any padded data will
     * be removed from the string prior to its return.
     *
     * @param encrypted The encrypted data to decrypt
     * @param passphrase The passphrase to decrypt the data with
     *
     * @return the decrypted binary data.
     *
     * @throws FailedToDecryptException when the data was corrupt and undecryptable or when the provided decryption
     *     password was incorrect. It is impossible to know which is the actual cause.
     * @see #unPad(byte[])
     */
    public static byte[] decryptRaw(final byte[] encrypted, final char[] passphrase) {
        try {
            return unPad(getDecryptionCipher(passphrase).doFinal(encrypted));
        } catch(final IllegalBlockSizeException | BadPaddingException e) {
            throw new FailedToDecryptException(e);
        }
    }

    /**
     * Pads a {@code byte} array to the specified length.
     * The output is pretty simple. The begin {@code byte}s
     * are the values from {@code bytes}. The last
     * {@code byte}, when cast to an integer, indicates the
     * number of end {@code byte}s (including itself) that
     * make up the padding. The returned array will always
     * be at least one element longer than the input.<br>
     * <br>
     * For example, if passed an array of 5 {@code byte}s and
     * the length 10, the first five {@code byte}s will be the
     * values from {@code bytes}. {@code byte}s 6-10 (indexes
     * 5-9) will be randomized data and {@code byte} 11
     * (index 10) will be the integer 6 cast as a byte. The
     * actual returned array will be 11 {@code byte}s long.<br>
     * <br>
     * If passed an array of 10 {@code byte}s and the length
     * of 10, the first 10 {@code byte}s will be the input
     * and {@code byte} 11 will be 1.
     *
     * @param bytes The array of {@code byte}s to pad
     * @param length The length to pad the array of {@code byte}s to
     *
     * @return the padded {@code byte} array.
     *
     * @see #unPad(byte[])
     */
    @SuppressWarnings("SameParameterValue")
    private static byte[] pad(final byte[] bytes, final int length) {
        if(bytes.length >= length) {
            final byte[] out = new byte[bytes.length + 1];
            System.arraycopy(bytes, 0, out, 0, bytes.length);
            out[bytes.length] = (byte) 1;
            return out;
        }

        final byte[] out = new byte[length + 1];

        int i = 0;
        for(; i < bytes.length; i++) {
            out[i] = bytes[i];
        }

        final int padded = length - i;

        // fill the rest with random bytes
        final byte[] fill = new byte[padded - 1];
        SECURE_RANDOM.nextBytes(fill);
        System.arraycopy(fill, 0, out, i, padded - 1);

        out[length] = (byte) (padded + 1);

        return out;
    }

    /**
     * Un-pads the specified array of {@code byte}s. Expects
     * an input that was padded with
     * {@link #pad(byte[], int)}. Its behavior is
     * unspecified if passed an input that was not the
     * result of {@link #pad(byte[], int)}.<br>
     * <br>
     * The returned array will be the {@code byte}s with all
     * the padding removed and the original {@code byte}s
     * left intact.
     *
     * @param bytes The array of {@code byte}s to un-pad
     *
     * @return the un-padded {@code byte} array.
     *
     * @see #pad(byte[], int)
     */
    private static byte[] unPad(final byte[] bytes) {
        final int padded = bytes[bytes.length - 1];
        final int targetLength = bytes.length - padded;

        final byte[] out = new byte[targetLength];

        System.arraycopy(bytes, 0, out, 0, targetLength);

        return out;
    }

    private static SecretKey getSecretKey(final char[] passphrase) {
        try {
            final PBEKeySpec keySpec = new PBEKeySpec(passphrase, SALT, ITERATION_COUNT,
                    AES_KEY_LENGTH);

            final byte[] shortKey = SecretKeyFactory.getInstance("PBEWithMD5AndDES").
                    generateSecret(keySpec).getEncoded();

            final byte[] intermediaryKey = new byte[INTERMEDIATE_KEY_LENGTH];
            for(int i = 0, j = 0; i < INTERMEDIATE_KEY_LENGTH; i++) {
                intermediaryKey[i] = shortKey[j];
                if(++j == shortKey.length) {
                    j = 0;
                }
            }

            return new SecretKeySpec(intermediaryKey, "AES");
        }
        catch(final NoSuchAlgorithmException e) {
            throw new AlgorithmNotSupportedException("DES with an MD5 Digest", e);
        } catch(final InvalidKeySpecException e) {
            throw new InappropriateKeySpecificationException(e);
        }
    }

    private static Cipher getDefaultEncryptionCipher() {
        if(sDefaultEncryptionCipher == null) {
            sDefaultEncryptionCipher = getEncryptionCipher(DEFAULT_PASSPHRASE);
        }

        return sDefaultEncryptionCipher;
    }

    private static Cipher getEncryptionCipher(final char[] passphrase) {
        return getEncryptionCipher(getSecretKey(passphrase));
    }

    private static Cipher getEncryptionCipher(final SecretKey secretKey) {
        try {
            final Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, SECURE_RANDOM);
            return cipher;
        }
        catch(final NoSuchAlgorithmException e) {
            throw new AlgorithmNotSupportedException(AES_WITH_SHA_1_DIGEST, e);
        }
        catch(final NoSuchPaddingException e) {
            throw new FailedToEncryptException(e.getMessage(), e);
        }
        catch(final InvalidKeyException e) {
            throw new InappropriateKeyException(e.getMessage(), e);
        }
    }

    private static Cipher getDefaultDecryptionCipher() {
        if(sDefaultDecryptionCipher == null) {
            sDefaultDecryptionCipher = getDecryptionCipher(DEFAULT_PASSPHRASE);
        }

        return sDefaultDecryptionCipher;
    }

    private static Cipher getDecryptionCipher(final char[] passphrase) {
        return getDecryptionCipher(getSecretKey(passphrase));
    }

    private static Cipher getDecryptionCipher(final SecretKey secretKey) {
        try {
            final Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, secretKey, SECURE_RANDOM);
            return cipher;
        }
        catch(final NoSuchAlgorithmException e) {
            throw new AlgorithmNotSupportedException(AES_WITH_SHA_1_DIGEST, e);
        }
        catch(final NoSuchPaddingException e) {
            throw new FailedToDecryptException(e.getMessage(), e);
        }
        catch(final InvalidKeyException e) {
            throw new InappropriateKeyException(e.getMessage(), e);
        }
    }

    /**
     * Signs the given data using the provided private key and returns the signature bytes.
     *
     * @param key The private key with which to sign the data
     * @param data The data to sign
     *
     * @return the signature content.
     *
     * @throws AlgorithmNotSupportedException if the signature algorithm is not supported.
     * @throws InappropriateKeyException if the key is not a proper key for signing.
     */
    public static byte[] signData(final PrivateKey key, final byte[] data)
            throws AlgorithmNotSupportedException, InappropriateKeyException {
        final Signature signature = getSignature();
        try {
            signature.initSign(key);
        } catch(final InvalidKeyException e) {
            throw new InappropriateKeyException("Failed to initialize the signature object with the private key.", e);
        }

        try {
            signature.update(data);
        } catch(final SignatureException e) {
            throw new RuntimeException("Programming error on signature.update(data) (sign).", e);
        }

        try {
            return signature.sign();
        }
        catch(final SignatureException e) {
            throw new RuntimeException("Programming error on signature.sign().", e);
        }
    }

    /**
     * Verifies the given signature on the given data using the given public key.
     *
     * @param key The public key with which to verify the signature
     * @param data The data that was signed
     * @param signatureContent The signature to verify
     *
     * @throws AlgorithmNotSupportedException if the signature algorithm is not supported.
     * @throws InappropriateKeyException if the key is not a proper key for signature verification.
     * @throws CorruptSignatureException if the signature was corrupt.
     * @throws InvalidSignatureException if the signature was not valid.
     */
    public static void verifySignature(final PublicKey key, final byte[] data,
                                       final byte[] signatureContent)
            throws AlgorithmNotSupportedException, InappropriateKeyException, CorruptSignatureException,
            InvalidSignatureException {

        final Signature signature = getSignature();

        try {
            signature.initVerify(key);
        } catch(final InvalidKeyException e) {
            throw new InappropriateKeyException("Failed to initialize the signature object with the public key.", e);
        }

        try {
            signature.update(data);
        }
        catch(final SignatureException e) {
            throw new RuntimeException("Programming error on signature.update(data) (verify).", e);
        }

        try {
            if(!signature.verify(signatureContent)) {
                throw new InvalidSignatureException("The license signature is invalid.");
            }
        }
        catch(final SignatureException e) {
            throw new CorruptSignatureException("The license signature is corrupt.", e);
        }
    }

    private static Signature getSignature() {
        try {
            return Signature.getInstance("SHA1with" + KEY_ALGORITHM);
        } catch(final NoSuchAlgorithmException e) {
            throw new AlgorithmNotSupportedException("SHA-1 with " + KEY_ALGORITHM);
        }
    }
}
