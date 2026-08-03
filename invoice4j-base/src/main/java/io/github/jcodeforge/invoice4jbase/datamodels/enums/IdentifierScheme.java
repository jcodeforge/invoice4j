package io.github.jcodeforge.invoice4jbase.datamodels.enums;

/**
 * Electronic address identification scheme (BT-34 / BT-49).
 *
 * Based on ISO 6523 ICD codes used by
 * EN 16931, PEPPOL BIS Billing and XRechnung.
 */
public enum IdentifierScheme {

    /**
     * GLN (GS1 Global Location Number)
     * ICD: 0088
     */
    GLN("0088"),

    /**
     * DUNS Number
     * ICD: 0060
     */
    DUNS("0060"),

    /**
     * VAT Registration Number
     * ICD: 9930
     */
    VAT("9930"),

    /**
     * German Leitweg-ID
     * ICD: 0204
     */
    LEITWEG_ID("0204"),

    /**
     * E-mail address.
     * Commonly used outside PEPPOL.
     */
    EMAIL("EM"),

    /**
     * URI / URL.
     */
    URI("URI");

    private final String code;

    IdentifierScheme(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static IdentifierScheme fromCode(String code) {
        for (IdentifierScheme scheme : values()) {
            if (scheme.code.equals(code)) {
                return scheme;
            }
        }
        throw new IllegalArgumentException("Unknown electronic address scheme: " + code);
    }
}