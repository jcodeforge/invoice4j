package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

public class Seller extends Party {

    /**
     * BT-30
     * Seller legal registration identifier.
     */
    private String legalRegistrationIdentifier;

    /**
     * BT-31
     * Seller VAT identifier.
     */
    private String vatIdentifier;

    /**
     * BT-32
     * Seller tax registration identifier.
     */
    private String taxRegistrationIdentifier;

    /**
     * BT-33
     * Seller additional legal information.
     */
    private String legalInformation;

    public String getLegalRegistrationIdentifier() {
        return legalRegistrationIdentifier;
    }

    public void setLegalRegistrationIdentifier(String legalRegistrationIdentifier) {
        this.legalRegistrationIdentifier = legalRegistrationIdentifier;
    }

    public String getVatIdentifier() {
        return vatIdentifier;
    }

    public void setVatIdentifier(String vatIdentifier) {
        this.vatIdentifier = vatIdentifier;
    }

    public String getTaxRegistrationIdentifier() {
        return taxRegistrationIdentifier;
    }

    public void setTaxRegistrationIdentifier(String taxRegistrationIdentifier) {
        this.taxRegistrationIdentifier = taxRegistrationIdentifier;
    }

    public String getLegalInformation() {
        return legalInformation;
    }

    public void setLegalInformation(String legalInformation) {
        this.legalInformation = legalInformation;
    }
}
