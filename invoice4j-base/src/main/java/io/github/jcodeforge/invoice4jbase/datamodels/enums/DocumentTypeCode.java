package io.github.jcodeforge.invoice4jbase.datamodels.enums;

/**
 * Document type code based on UNTDID 1001 / UN/CEFACT 1001.
 * Primarily maps to the BT-3 element in EN 16931 / ZUGFeRD / Factur-X specifications.
 */
public enum DocumentTypeCode {

    /**
     * 130 - Invoicing data sheet
     */
    INVOICING_DATA_SHEET("130"),

    /**
     * 220 - Purchase order
     */
    PURCHASE_ORDER("220"),

    /**
     * 261 - Self-billed credit note
     */
    SELF_BILLED_CREDIT_NOTE("261"),

    /**
     * 326 - Partial invoice
     * (Corrected from 'Contract')
     */
    PARTIAL_INVOICE("326"),

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
     * 751 - Accounting aid
     * (Corrected from 'Invoice information')
     */
    ACCOUNTING_AID("751"),

    /**
     * 875 - Partial construction invoice
     * Germany specific (Abschlagsrechnung)
     */
    PARTIAL_CONSTRUCTION_INVOICE("875"),

    /**
     * 876 - Partial final construction invoice
     * Germany specific (Teilschlussrechnung)
     */
    PARTIAL_FINAL_CONSTRUCTION_INVOICE("876"),

    /**
     * 877 - Final construction invoice
     * Germany specific (Schlussrechnung)
     */
    FINAL_CONSTRUCTION_INVOICE("877"),

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
