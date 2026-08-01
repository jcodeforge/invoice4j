package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

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
     * Payee identifier.
     */
    private PartyIdentifier identifier;

    private Payee() {
        super();
    }

    public PartyIdentifier getIdentifier() {
        return identifier;
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
        public Builder identifier(PartyIdentifier identifier) {
            payee.identifier = identifier;
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
            return payee;
        }
    }
}
