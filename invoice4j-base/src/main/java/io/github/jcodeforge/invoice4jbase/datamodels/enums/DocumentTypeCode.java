package io.github.jcodeforge.invoice4jbase.datamodels.enums;

/**
 * Document type code.
 *
 * Based on UN/CEFACT 1001.
 */
public enum DocumentTypeCode {

    /**
     * 220 - Purchase order
     */
    PURCHASE_ORDER("220"),

    /**
     * 326 - Contract
     */
    CONTRACT("326"),

    /**
     * 351 - Despatch advice
     *
     * Document issued by the seller advising
     * the buyer that goods have been dispatched.
     */
    DESPATCH_ADVICE("351"),

    /**
     * 352 - Receiving advice
     *
     * Document issued by the buyer confirming
     * receipt of delivered goods.
     */
    RECEIVING_ADVICE("352"),

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
    INVOICE_INFORMATION("751"),

    /**
     * 916 - Related document
     */
    RELATED_DOCUMENT("916");

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
