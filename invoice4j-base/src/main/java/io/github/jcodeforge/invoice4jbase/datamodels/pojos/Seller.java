package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import java.util.ArrayList;
import java.util.List;

public class Seller extends Party {

    /**
     * BT-29
     *
     * Seller identifier.
     *
     * Identifier assigned to the seller.
     * Examples:
     * - Customer number
     * - GLN
     * - Company identifier
     */
    private List<PartyIdentifier> identifiers = new ArrayList<>();

    /**
     * BT-30
     * Seller legal registration identifier.
     */
    private String legalRegistrationIdentifier;

    /**
     * BT-31
     * Seller VAT identifier.
     */
    private TaxIdentifier vatIdentifier;

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

    public List<PartyIdentifier> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<PartyIdentifier> identifiers) {
        this.identifiers = identifiers;
    }

    public String getLegalRegistrationIdentifier() {
        return legalRegistrationIdentifier;
    }

    public void setLegalRegistrationIdentifier(String legalRegistrationIdentifier) {
        this.legalRegistrationIdentifier = legalRegistrationIdentifier;
    }

    public TaxIdentifier getVatIdentifier() {
        return vatIdentifier;
    }

    public void setVatIdentifier(TaxIdentifier vatIdentifier) {
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
