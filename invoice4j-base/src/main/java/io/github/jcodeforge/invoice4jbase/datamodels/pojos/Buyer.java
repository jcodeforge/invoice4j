package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.util.ArrayList;
import java.util.List;

public class Buyer extends Party {

    /**
     * BT-45 Buyer identifier
     *
     * Identifier assigned to the seller.
     * Examples:
     * - Customer number
     * - GLN
     * - Company identifier
     */
    private List<PartyIdentifier> identifiers = new ArrayList<>();

    /**
     * BT-48
     * Buyer's VAT identifier.
     */
    private TaxIdentifier vatIdentifier;

    /**
     * BT-47
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

    private Buyer() {
        super();
    }

    public List<PartyIdentifier> getIdentifiers() {
        return identifiers;
    }

    public TaxIdentifier getVatIdentifier() {
        return vatIdentifier;
    }

    public String getLegalRegistrationIdentifier() {
        return legalRegistrationIdentifier;
    }


    public String getBuyerReference() {
        return buyerReference;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Buyer buyer;

        private Builder() {
            this.buyer = new Buyer();
        }

        public Builder addIdentifier(PartyIdentifier identifier) {
            if (identifier == null) {
                throw new InvoiceValidationException("BT-45 Buyer identifier must not be null.");
            }

            for (PartyIdentifier existing : buyer.identifiers) {
                if (existing.getScheme().equals(identifier.getScheme())) {
                    throw new InvoiceValidationException("BT-45 Duplicate buyer identifier scheme: " + existing.getScheme());
                }
            }

            buyer.identifiers.add(identifier);
            return this;
        }

        /**
         * BT-48
         * Buyer's VAT identifier.
         */
        public Builder vatIdentifier(TaxIdentifier vatIdentifier) {
            if (vatIdentifier == null) {
                throw new InvoiceValidationException("BT-48 Buyer VAT identifier must not be null.");
            }
            buyer.vatIdentifier = vatIdentifier;
            return this;
        }

        /**
         * BT-49
         * Buyer's legal registration identifier.
         */
        public Builder legalRegistrationIdentifier(String legalRegistrationIdentifier) {
            buyer.legalRegistrationIdentifier = legalRegistrationIdentifier;
            return this;
        }

        /**
         * BT-10
         * Buyer reference.
         */
        public Builder buyerReference(String buyerReference) {
            buyer.buyerReference = buyerReference;
            return this;
        }

        /**
         * Party name.
         */
        public Builder name(String name) {
            buyer.name = name;
            return this;
        }

        /**
         * Trading name.
         */
        public Builder tradingName(String tradingName) {
            buyer.tradingName = tradingName;
            return this;
        }

        /**
         * Electronic address.
         */
        public Builder electronicAddress(ElectronicAddress electronicAddress) {
            buyer.electronicAddress = electronicAddress;
            return this;
        }

        /**
         * Postal address.
         */
        public Builder address(Address address) {
            buyer.address = address;
            return this;
        }

        /**
         * Contact information.
         */
        public Builder contact(Contact contact) {
            buyer.contact = contact;
            return this;
        }

        public Buyer build() {
            if (buyer.name == null || buyer.name.isBlank()) {
                throw new InvoiceValidationException("BT-44 Buyer name is required.");
            }
            if (buyer.address == null) {
                throw new InvoiceValidationException("BG-8 Buyer postal address is required.");
            }
            if (buyer.legalRegistrationIdentifier != null && buyer.legalRegistrationIdentifier.isBlank()) {
                throw new InvoiceValidationException("BT-47 Buyer legal registration identifier must not be blank.");
            }
            if (buyer.tradingName != null && buyer.tradingName.isBlank()) {
                throw new InvoiceValidationException("Buyer trading name must not be blank.");
            }
            if (buyer.buyerReference != null && buyer.buyerReference.isBlank()) {
                throw new InvoiceValidationException("BT-10 Buyer reference must not be blank.");
            }
            return buyer;
        }
    }
}
