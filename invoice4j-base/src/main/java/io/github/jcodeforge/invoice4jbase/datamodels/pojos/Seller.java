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

    private Seller() {
        super();
    }

    public List<PartyIdentifier> getIdentifiers() {
        return identifiers;
    }


    public String getLegalRegistrationIdentifier() {
        return legalRegistrationIdentifier;
    }


    public TaxIdentifier getVatIdentifier() {
        return vatIdentifier;
    }


    public String getTaxRegistrationIdentifier() {
        return taxRegistrationIdentifier;
    }


    public String getLegalInformation() {
        return legalInformation;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Seller seller;

        private Builder() {
            this.seller = new Seller();
        }

        /**
         * BT-29
         * Seller identifier.
         */
        public Builder addIdentifier(PartyIdentifier identifier) {
            seller.identifiers.add(identifier);
            return this;
        }

        public Builder identifiers(List<PartyIdentifier> identifiers) {
            seller.identifiers = new ArrayList<>(identifiers);
            return this;
        }

        /**
         * BT-30
         * Seller legal registration identifier.
         */
        public Builder legalRegistrationIdentifier(String legalRegistrationIdentifier) {
            seller.legalRegistrationIdentifier = legalRegistrationIdentifier;
            return this;
        }

        /**
         * BT-31
         * Seller VAT identifier.
         */
        public Builder vatIdentifier(TaxIdentifier vatIdentifier) {
            seller.vatIdentifier = vatIdentifier;
            return this;
        }

        /**
         * BT-32
         * Seller tax registration identifier.
         */
        public Builder taxRegistrationIdentifier(String taxRegistrationIdentifier) {
            seller.taxRegistrationIdentifier = taxRegistrationIdentifier;
            return this;
        }

        /**
         * BT-33
         * Seller additional legal information.
         */
        public Builder legalInformation(String legalInformation) {
            seller.legalInformation = legalInformation;
            return this;
        }

        /**
         * Party name.
         */
        public Builder name(String name) {
            seller.name = name;
            return this;
        }

        /**
         * Trading name.
         */
        public Builder tradingName(String tradingName) {
            seller.tradingName = tradingName;
            return this;
        }

        /**
         * Electronic address.
         */
        public Builder electronicAddress(ElectronicAddress electronicAddress) {
            seller.electronicAddress = electronicAddress;
            return this;
        }

        /**
         * Postal address.
         */
        public Builder address(Address address) {
            seller.address = address;
            return this;
        }

        /**
         * Contact information.
         */
        public Builder contact(Contact contact) {
            seller.contact = contact;
            return this;
        }

        public Seller build() {
            return seller;
        }
    }
}
