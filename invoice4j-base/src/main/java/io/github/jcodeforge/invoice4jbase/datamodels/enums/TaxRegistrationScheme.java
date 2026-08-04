package io.github.jcodeforge.invoice4jbase.datamodels.enums;

/**
 * Tax registration identifier type.
 *
 * Used for BT-31, BT-32 and BT-48.
 */
public enum TaxRegistrationScheme {

    /**
     * VAT registration number.
     * CII schemeID = VA
     */
    VAT_REGISTRATION_NUMBER("VA"),

    /**
     * Fiscal code / national tax identifier.
     * CII schemeID = FC
     */
    TAX_IDENTIFICATION_NUMBER("FC"),

    /**
     * Local tax registration number.
     */
    LOCAL_TAX_NUMBER("LOCAL"),

    /**
     * Other identifier.
     */
    OTHER("OTHER");

    private final String code;

    TaxRegistrationScheme(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
