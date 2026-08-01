package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

public class Buyer extends Party {

    /**
     * BT-48
     * Buyer's VAT identifier.
     */
    private TaxIdentifier vatIdentifier;

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

    private Buyer() {
        super();
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

        /**
         * BT-48
         * Buyer's VAT identifier.
         */
        public Builder vatIdentifier(TaxIdentifier vatIdentifier) {
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
            return buyer;
        }
    }
}
