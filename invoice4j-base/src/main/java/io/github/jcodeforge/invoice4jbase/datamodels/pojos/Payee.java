package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Payee party.
 *
 * Represents the party receiving payment when different
 * from the seller.
 */
public class Payee extends Party {

    /**
     * BT-60
     *
     * Payee identifiers.
     */
    private List<PartyIdentifier> identifiers = new ArrayList<>();

    private Payee() {
        super();
    }

    public List<PartyIdentifier> getIdentifiers() {
        return identifiers;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Payee payee;

        private Builder() {
            this.payee = new Payee();
        }

        /**
         * BT-60
         *
         * Payee identifier.
         */
        public Builder addIdentifier(PartyIdentifier identifier) {
            if (identifier == null) {
                throw new InvoiceValidationException("BT-60 Payee identifier must not be null.");
            }

            for (PartyIdentifier existing : payee.identifiers) {
                if (existing.getScheme().equals(identifier.getScheme())) {
                    throw new InvoiceValidationException("BT-60 Duplicate payee identifier scheme: " + existing.getScheme());
                }
            }

            payee.identifiers.add(identifier);
            return this;
        }

        /**
         * Party name.
         */
        public Builder name(String name) {
            payee.name = name;
            return this;
        }

        /**
         * Trading name.
         */
        public Builder tradingName(String tradingName) {
            payee.tradingName = tradingName;
            return this;
        }

        /**
         * Electronic address.
         */
        public Builder electronicAddress(ElectronicAddress electronicAddress) {
            payee.electronicAddress = electronicAddress;
            return this;
        }

        /**
         * Postal address.
         */
        public Builder address(Address address) {
            payee.address = address;
            return this;
        }

        /**
         * Contact information.
         */
        public Builder contact(Contact contact) {
            payee.contact = contact;
            return this;
        }

        public Payee build() {
            if (payee.name == null || payee.name.isBlank()) {
                throw new InvoiceValidationException("BT-59 Payee name is required.");
            }
            if (payee.tradingName != null && payee.tradingName.isBlank()) {
                throw new InvoiceValidationException("Payee trading name must not be blank.");
            }
            return payee;
        }
    }
}
