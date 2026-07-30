package io.github.jcodeforge.licensing;

import io.github.jcodeforge.licensing.encryption.KeyFileUtils;
import io.github.jcodeforge.licensing.encryption.PasswordProvider;
import io.github.jcodeforge.licensing.encryption.PrivateKeyPasswordProvider;
import io.github.jcodeforge.licensing.exception.AlgorithmNotSupportedException;
import io.github.jcodeforge.licensing.exception.InappropriateKeyException;
import io.github.jcodeforge.licensing.exception.InappropriateKeySpecificationException;
import io.github.jcodeforge.licensing.exception.KeyNotFoundException;
import io.github.licensing.exception.*;
import io.github.jcodeforge.licensing.encryption.EncryptedKeyDataProvider;
import io.github.jcodeforge.licensing.encryption.KeyDataProvider;
import io.github.jcodeforge.licensing.schemes.License;
import io.github.jcodeforge.licensing.schemes.SignedLicense;
import io.github.jcodeforge.core.exception.ObjectSerializationException;
import io.github.jcodeforge.core.utils.JsonConverter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.PrivateKey;
import java.util.Arrays;

public final class LicenseCreator {

    private static LicenseCreator sInstance;

    private final LicenseCreatorProperties mProperties;

    private LicenseCreator(LicenseCreatorProperties properties) {
        mProperties = properties;
    }

    public static synchronized LicenseCreator getInstance(String privateKeyPath) {
        if(sInstance == null) {
            sInstance = new LicenseCreator(new LicenseCreatorProperties(privateKeyPath));
        }

        return sInstance;
    }

    /**
     * Takes a license object and creates a secure version of it for serialization and delivery to the customer.
     *
     * @param license The license object to be signed
     *
     * @return the signed license object.
     *
     * @throws AlgorithmNotSupportedException if the encryption algorithm is not supported.
     * @throws KeyNotFoundException if the public key data could not be found.
     * @throws InappropriateKeySpecificationException if an inappropriate key specification is provided.
     * @throws InappropriateKeyException if the key type and cipher type do not match.
     */
    private SignedLicense signLicense(final License license)
        throws AlgorithmNotSupportedException, KeyNotFoundException,
            InappropriateKeySpecificationException, InappropriateKeyException {
        final char[] password = mProperties.getPrivateKeyPasswordProvider().getPassword();
        final byte[] keyData = mProperties.getKeyDataProvider().getEncryptedPrivateKeyData();

        final PrivateKey key = KeyFileUtils.readEncryptedPrivateKey(keyData, password);

        final byte[] encryptedRaw = KeyFileUtils.encryptRaw(license.serialize(), password);
        final byte[] signature = KeyFileUtils.signData(key, encryptedRaw);

        Arrays.fill(password, '\u0000');
        Arrays.fill(keyData, (byte) 0);

        final SignedLicense signedLicense = new SignedLicense(encryptedRaw, signature);

        Arrays.fill(encryptedRaw, (byte) 0);
        Arrays.fill(signature, (byte) 0);

        return signedLicense;
    }

    /**
     * Takes a license object and creates a secure and serialized version of it for delivery to the customer.
     *
     * @param license The license object to be signed and serialized
     *
     * @return the signed and serialized license object.
     *
     * @throws AlgorithmNotSupportedException if the encryption algorithm is not supported.
     * @throws KeyNotFoundException if the public key data could not be found.
     * @throws InappropriateKeySpecificationException if an inappropriate key specification is provided.
     * @throws InappropriateKeyException if the key type and cipher type do not match.
     * @throws ObjectSerializationException if an error is encountered while serializing the key.
     */
    public byte[] signAndSerializeLicense(final License license)
        throws AlgorithmNotSupportedException, KeyNotFoundException, InappropriateKeyException,
               ObjectSerializationException {
        return JsonConverter.toJson(signLicense(license)).getBytes(StandardCharsets.UTF_8);
    }

    public static final class LicenseCreatorProperties {

        private final KeyDataProvider mKeyDataProvider;
        private final PasswordProvider mPrivateKeyPasswordProvider;

        public LicenseCreatorProperties(String privateKeyPath) {
            mKeyDataProvider = new EncryptedKeyDataProvider(Path.of(privateKeyPath));
            mPrivateKeyPasswordProvider = new PrivateKeyPasswordProvider();
        }

        public KeyDataProvider getKeyDataProvider() {
            return mKeyDataProvider;
        }

        public PasswordProvider getPrivateKeyPasswordProvider() {
            return mPrivateKeyPasswordProvider;
        }
    }
}
