package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;

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
            if (identifier == null) {
                throw new InvoiceValidationException("BT-29 Seller identifier must not be null");
            }
            for (PartyIdentifier existing : seller.identifiers) {
                if (existing.getScheme().equals(identifier.getScheme())) {
                    throw new InvoiceValidationException("BT-29 Duplicate seller identifier scheme: " + existing.getScheme());
                }
            }
            seller.identifiers.add(identifier);
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
            if (vatIdentifier == null) {
                throw new InvoiceValidationException("BT-31 Seller VAT identifier must not be null.");
            }
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
            if (name == null || name.isBlank()) {
                throw new InvoiceValidationException("BT-27 Seller name is required.");
            }
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
            if (address == null) {
                throw new InvoiceValidationException("BG-5 Seller postal address is required.");
            }
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
            if (seller.name == null || seller.name.isBlank()) {
                throw new InvoiceValidationException("BT-27 Seller name is required.");
            }
            if (seller.address == null) {
                throw new InvoiceValidationException("BG-5 Seller postal address is required.");
            }
            if (seller.taxRegistrationIdentifier != null && seller.taxRegistrationIdentifier.isBlank()) {
                throw new InvoiceValidationException("BT-32 Tax registration identifier value must not be null or blank.");
            }
            if (seller.legalRegistrationIdentifier != null && seller.legalRegistrationIdentifier.isBlank()) {
                throw new InvoiceValidationException("BT-30 Legal registration identifier value must not be null or blank.");
            }
            if (seller.legalInformation != null && seller.legalInformation.isBlank()) {
                throw new InvoiceValidationException("BT-33 Legal information value must not be null or blank.");
            }
            return seller;
        }
    }
}
