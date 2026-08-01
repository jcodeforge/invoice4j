package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

/**
 * BG-13
 * Deliver-to party.
 *
 * The party to whom the goods or services are delivered,
 * if different from the buyer.
 */
public class ShipTo extends Party {

    /**
     * BT-71
     * Deliver-to location identifier.
     *
     * Example:
     * - GLN
     * - Warehouse identifier
     * - Internal location code
     */
    private PartyIdentifier locationIdentifier;

    private ShipTo() {
        super();
    }

    public PartyIdentifier getLocationIdentifier() {
        return locationIdentifier;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final ShipTo shipTo;

        private Builder() {
            this.shipTo = new ShipTo();
        }

        /**
         * BT-71
         *
         * Deliver-to location identifier.
         */
        public Builder locationIdentifier(PartyIdentifier locationIdentifier) {
            shipTo.locationIdentifier = locationIdentifier;
            return this;
        }

        /**
         * Party name.
         */
        public Builder name(String name) {
            shipTo.name = name;
            return this;
        }

        /**
         * Trading name.
         */
        public Builder tradingName(String tradingName) {
            shipTo.tradingName = tradingName;
            return this;
        }

        /**
         * Electronic address.
         */
        public Builder electronicAddress(ElectronicAddress electronicAddress) {
            shipTo.electronicAddress = electronicAddress;
            return this;
        }

        /**
         * Postal address.
         */
        public Builder address(Address address) {
            shipTo.address = address;
            return this;
        }

        /**
         * Contact information.
         */
        public Builder contact(Contact contact) {
            shipTo.contact = contact;
            return this;
        }

        public ShipTo build() {
            return shipTo;
        }
    }
}
