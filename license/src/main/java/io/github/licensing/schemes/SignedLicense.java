package io.github.licensing.schemes;

import com.google.gson.annotations.SerializedName;
import java.util.Arrays;

public final class SignedLicense {

    @SerializedName("license_content")
    private final byte[] mLicenseContent;

    @SerializedName("signature_content")
    private final byte[] mSignatureContent;

    public SignedLicense(final byte[] licenseContent, final byte[] signatureContent) {
        mLicenseContent = Arrays.copyOf(licenseContent, licenseContent.length);
        mSignatureContent = Arrays.copyOf(signatureContent, signatureContent.length);
    }

    /**
     * Get the content of the actual license object. This is encrypted and
     * corresponds to {@link License}. For security reasons, only a copy of
     * the content is returned.
     */
    public byte[] getLicenseContent() {
        return Arrays.copyOf(mLicenseContent, mLicenseContent.length);
    }

    /**
     * Get the signature for the license content. For security reasons, only a
     * copy of the signature is returned.
     */
    public byte[] getSignatureContent() {
        return Arrays.copyOf(mSignatureContent, mSignatureContent.length);
    }

    /**
     * Erase the contents of this object. This is a security feature to write
     * zeroes to the license and signature data so that it doesn't hang around
     * in memory where it might be reverse engineered.
     */
    public void erase() {
        Arrays.fill(mLicenseContent, (byte) 0);
        Arrays.fill(mSignatureContent, (byte) 0);
    }
}
