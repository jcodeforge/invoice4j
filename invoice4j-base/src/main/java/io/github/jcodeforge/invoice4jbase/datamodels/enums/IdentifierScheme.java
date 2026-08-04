package io.github.jcodeforge.invoice4jbase.datamodels.enums;

public enum IdentifierScheme {

    /**
     * GLN (Global Location Number)
     */
    GLN("0088", true),

    /**
     * German Leitweg-ID
     */
    LEITWEG_ID("0204", false),

    /**
     * VAT number
     */
    VAT("9930", false),

    /**
     * Email address
     */
    EMAIL("EM", false);

    private final String code;

    /**
     * Indicates whether the identifier shall be serialized
     * as ram:GlobalID instead of ram:ID.
     */
    private final boolean globalIdentifier;

    IdentifierScheme(String code, boolean globalIdentifier) {
        this.code = code;
        this.globalIdentifier = globalIdentifier;
    }

    public String getCode() {
        return code;
    }

    public boolean isGlobalIdentifier() {
        return globalIdentifier;
    }

    public static IdentifierScheme fromCode(String code) {
        for (IdentifierScheme scheme : values()) {
            if (scheme.code.equals(code)) {
                return scheme;
            }
        }

        throw new IllegalArgumentException("Unknown identifier scheme: " + code);
    }
}