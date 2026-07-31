package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

public class Buyer extends Party {

    /**
     * BT-48
     * Buyer's VAT identifier.
     */
    private String vatIdentifier;

    /**
     * BT-49
     * Buyer's legal registration identifier.
     */
    private String legalRegistrationIdentifier;

    /**
     * BT-10
     * Buyer reference.
     *
     * Optional reference supplied by the buyer (e.g. department,
     * cost center, purchaser). Frequently required in public procurement.
     */
    private String buyerReference;

    public String getVatIdentifier() {
        return vatIdentifier;
    }

    public void setVatIdentifier(String vatIdentifier) {
        this.vatIdentifier = vatIdentifier;
    }

    public String getLegalRegistrationIdentifier() {
        return legalRegistrationIdentifier;
    }

    public void setLegalRegistrationIdentifier(String legalRegistrationIdentifier) {
        this.legalRegistrationIdentifier = legalRegistrationIdentifier;
    }

    public String getBuyerReference() {
        return buyerReference;
    }

    public void setBuyerReference(String buyerReference) {
        this.buyerReference = buyerReference;
    }
}
