package io.github.jcodeforge.invoice4jbase.datamodels.enums;

/**
 * Document type code.
 *
 * Based on UN/CEFACT 1001.
 */
public enum DocumentTypeCode {

    /**
     * 380 - Commercial invoice
     */
    COMMERCIAL_INVOICE("380"),

    /**
     * 381 - Credit note
     */
    CREDIT_NOTE("381"),

    /**
     * 384 - Corrected invoice
     */
    CORRECTED_INVOICE("384"),

    /**
     * 386 - Prepayment invoice
     */
    PREPAYMENT_INVOICE("386"),

    /**
     * 389 - Self-billed invoice
     */
    SELF_BILLED_INVOICE("389"),

    /**
     * 751 - Invoice information
     */
    INVOICE_INFORMATION("751");

    private final String code;

    DocumentTypeCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static DocumentTypeCode fromCode(String code) {
        for (DocumentTypeCode type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }

        throw new IllegalArgumentException(
                "Unknown document type code: " + code
        );
    }
}
