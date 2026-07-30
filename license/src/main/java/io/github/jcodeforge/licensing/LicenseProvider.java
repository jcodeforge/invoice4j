package io.github.jcodeforge.licensing;

import io.github.jcodeforge.licensing.encryption.*;
import io.github.jcodeforge.licensing.exception.*;
import io.github.licensing.encryption.*;
import io.github.licensing.exception.*;
import io.github.jcodeforge.licensing.schemes.License;
import io.github.jcodeforge.licensing.schemes.SignedLicense;
import io.github.jcodeforge.core.Constants;
import io.github.jcodeforge.core.utils.JsonConverter;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * A default implementation of the {@link LicenseProvider} that assumes the binary data from the signed and serialized
 * license is stored in a file.
 * This implementation also assumes that license contexts (lookup keys) are always either strings or have a meaningful
 * {@link Object#toString()} implementation that can be used within the file name.
 * Important: We need to use this object as a singleton via {@link #getInstance()}
 */
public class LicenseProvider {

    // 10 seconds
    private static final int DEFAULT_CACHE_TIME_IN_MILLIS = 10 * 1000;

    private static LicenseProvider sInstance;

    private final KeyDataProvider mKeyDataProvider;
    private final PasswordProvider mPasswordProvider;

    private final Hashtable<Object, LicenseCacheEntry> mLicenseCache = new Hashtable<>();

    private LicenseProvider(KeyDataProvider keyDataProvider, PasswordProvider passwordProvider) {
        mKeyDataProvider = keyDataProvider;
        mPasswordProvider = passwordProvider;
    }

    public static synchronized LicenseProvider getInstance() {
        if(sInstance == null) {
            sInstance = new LicenseProvider(new EncryptedKeyDataProvider(),
                    new PrivateKeyPasswordProvider());
        }

        return sInstance;
    }

    /**
     * Gets the stored, still-encrypted license content and signature from the persistence store.
     *
     * @param context The context for which to get the license
     *
     * @return the signed license object.
     */
    private SignedLicense getSignedLicense(Object context) {
        byte[] data = getLicenseData(context);
        return JsonConverter.fromJson(new String(data), SignedLicense.class);
    }

    /**
     * Gets the stored, still-encrypted, still-serialized license content and signature from the persistence store.
     * Returns null (not an empty array) if no license is found.
     *
     * @param context The context for which to get the license
     *
     * @return the signed license data.
     */
    private byte[] getLicenseData(final Object context) {
        if(context == null) {
            throw new IllegalArgumentException("Argument context cannot be null.");
        }

        byte[] data = null;

        File file = new File(Constants.APP_USER_DIR + context + ".lic");
        if(file.exists() && file.canRead()) {
            try {
                data = Base64.decodeBase64(FileUtils.readFileToByteArray(file));
            }
            catch(final IOException ignore) {}
        }

        return data;
    }

    /**
     * If the license has already been cached for the specified context (account, client, etc.) and the cache has not
     * become stale (its age has not surpassed the cache time limitation configured for this manager), this returns the
     * cached license. If it has not been cached or the cache is stale, this retrieves the license from the store,
     * decrypts it, deserializes it, checks its signature and, if everything is kosher, caches and returns the
     * license.<br>
     * <br>
     * This method takes precautions to ensure that the cache is not tampered with using reflection. However, it is not
     * infallible. For extra security, one could configure this manager with a 10-second cache (zero cache time limit
     * in minutes), but we highly recommend implementing caching in the license data provider: the signature checking
     * process is time-consuming (on the order of hundreds of milliseconds, and could happen multiple times per action)
     * and the added overhead of retrieving the license from the store every time could bring an application to
     * its knees.
     *
     * @param context The context (account, client, etc.) for which to retrieve the license object
     *
     * @return the requested license object, or null if none exists.
     */
    public License getLicense(Object context) throws KeyNotFoundException,
            CorruptSignatureException, InvalidSignatureException, FailedToDecryptException {

        if(context == null) {
            throw new IllegalArgumentException("License context cannot be null.");
        }

        long time = System.currentTimeMillis();

        LicenseCacheEntry entry;

        synchronized(mLicenseCache) {
            entry = mLicenseCache.get(context);

            if(entry != null && entry.getLicense() != null) {
                if(entry.getExpiresAt() <= time) {
                    entry = null;
                    mLicenseCache.remove(context);
                }
            }

            if(entry == null || entry.getLicense() == null) {
                SignedLicense signedLicense = getSignedLicense(context);
                if(signedLicense == null) {
                    return null;
                }

                License license = decryptAndVerifyLicense(signedLicense);
                signedLicense.erase();

                long expiresAt = time + DEFAULT_CACHE_TIME_IN_MILLIS;

                entry = new LicenseCacheEntry(license, expiresAt);

                mLicenseCache.put(context, entry);
            }
        }

        return entry.getLicense();
    }

    /**
     * This method verifies the signed license object's signature, then decrypts the signed license and returns the
     * decrypted license. It throws an exception if the signature is invalid. Normally you will not need to call this
     * method; all of the other methods in this class call this method at some point or another in one way or another
     * (specifically by way of {@link #getLicense(Object)}). This is a convenience method useful for verifying the
     * signature of and interpreting an individual license without going through all of the retrieval and caching
     * mechanisms normally used when calling {@link #getLicense(Object)}.
     *
     * @param signedLicense The signed license object to verify
     *
     * @return the decrypted license object
     */
    private License decryptAndVerifyLicense(final SignedLicense signedLicense) {
        verifyLicenseSignature(signedLicense);

        char[] password = mPasswordProvider.getPassword();
        byte[] encrypted = signedLicense.getLicenseContent();
        byte[] unencrypted = KeyFileUtils.decryptRaw(encrypted, password);

        Arrays.fill(password, '\u0000');
        Arrays.fill(encrypted, (byte) 0);

        License license = License.deserialize(unencrypted);

        Arrays.fill(unencrypted, (byte) 0);

        return license;
    }

    /**
     * This method verifies the signed license object's signature. It throws an exception if the signature is invalid.
     * Normally you will not need to call this method; all of the other methods in this class call this method at some
     * point or another in one way or another (specifically by way of {@link #getLicense(Object)}). This is a
     * convenience method useful for verifying the signature of an individual license without going through all of the
     * retrieval and caching mechanisms normally used when calling {@link #getLicense(Object)}.
     *
     * @param signedLicense The signed license object to verify
     */
    private void verifyLicenseSignature(SignedLicense signedLicense)
            throws AlgorithmNotSupportedException, InappropriateKeyException,
            CorruptSignatureException, InvalidSignatureException {

        byte[] keyData = mKeyDataProvider.getEncryptedPublicKeyData();
        char[] password = mPasswordProvider.getPassword();

        final PublicKey key = KeyFileUtils.readEncryptedPublicKey(keyData, password);

        Arrays.fill(password, '\u0000');
        Arrays.fill(keyData, (byte) 0);

        KeyFileUtils.verifySignature(
                key, signedLicense.getLicenseContent(), signedLicense.getSignatureContent()
        );
    }

    /**
     * An entry in the memory-based license cache.
     */
    private final static class LicenseCacheEntry {

        private final License mLicense;
        private final long mExpiresAt;

        public LicenseCacheEntry(License license, long expiresAt) {
            mLicense = license;
            mExpiresAt = expiresAt;
        }

        public License getLicense() {
            return mLicense;
        }

        public long getExpiresAt() {
            return mExpiresAt;
        }
    }
}
