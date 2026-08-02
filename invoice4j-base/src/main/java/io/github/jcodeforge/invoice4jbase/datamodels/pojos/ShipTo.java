package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.util.ArrayList;
import java.util.List;

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
    private List<PartyIdentifier> identifiers = new ArrayList<>();

    private ShipTo() {
        super();
    }

    public List<PartyIdentifier> getIdentifiers() {
        return identifiers;
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
        public Builder addIdentifier(PartyIdentifier identifier) {
            if (identifier == null) {
                throw new InvoiceValidationException("BT-71 Ship-to identifier must not be null.");
            }

            for (PartyIdentifier existing : shipTo.identifiers) {
                if (existing.getScheme().equals(identifier.getScheme())) {
                    throw new InvoiceValidationException("BT-71 Duplicate Ship-to identifier scheme: " + existing.getScheme());
                }
            }

            shipTo.identifiers.add(identifier);
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
            if (shipTo.name == null || shipTo.name.isBlank()) {
                throw new InvoiceValidationException("BT-70 Ship-to name is required.");
            }
            if (shipTo.address == null) {
                throw new InvoiceValidationException("BG-15 Ship-to postal address is required.");
            }
            if (shipTo.tradingName != null && shipTo.tradingName.isBlank()) {
                throw new InvoiceValidationException("Ship-to trading name must not be blank.");
            }
            return shipTo;
        }
    }
}
