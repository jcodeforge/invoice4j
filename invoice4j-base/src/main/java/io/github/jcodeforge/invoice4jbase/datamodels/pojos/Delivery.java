package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import java.time.LocalDate;

/**
 * BG-14
 * Delivery information.
 *
 * Information about the delivery of goods or services.
 */
public class Delivery {

    /**
     * BT-72
     * Actual delivery date.
     */
    private LocalDate actualDeliveryDate;

    /**
     * BT-75
     * Invoicing period delivery start date.
     */
    private LocalDate deliveryPeriodStartDate;

    /**
     * BT-76
     * Invoicing period delivery end date.
     */
    private LocalDate deliveryPeriodEndDate;

    /**
     * Deliver-to party location.
     */
    private ShipTo shipTo;

    /**
     * Delivery address.
     */
    private Address address;

    /**
     * Delivery note reference.
     */
    private String deliveryNoteReference;

    private Delivery() {
    }

    public LocalDate getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public LocalDate getDeliveryPeriodStartDate() {
        return deliveryPeriodStartDate;
    }

    public LocalDate getDeliveryPeriodEndDate() {
        return deliveryPeriodEndDate;
    }

    public ShipTo getShipTo() {
        return shipTo;
    }

    public Address getAddress() {
        return address;
    }

    public String getDeliveryNoteReference() {
        return deliveryNoteReference;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Delivery delivery;

        private Builder() {
            this.delivery = new Delivery();
        }

        public Builder actualDeliveryDate(LocalDate actualDeliveryDate) {
            delivery.actualDeliveryDate = actualDeliveryDate;
            return this;
        }

        public Builder deliveryPeriodStartDate(LocalDate deliveryPeriodStartDate) {
            delivery.deliveryPeriodStartDate = deliveryPeriodStartDate;
            return this;
        }

        public Builder deliveryPeriodEndDate(LocalDate deliveryPeriodEndDate) {
            delivery.deliveryPeriodEndDate = deliveryPeriodEndDate;
            return this;
        }

        public Builder shipTo(ShipTo shipTo) {
            delivery.shipTo = shipTo;
            return this;
        }

        public Builder address(Address address) {
            delivery.address = address;
            return this;
        }

        public Builder deliveryNoteReference(String deliveryNoteReference) {
            delivery.deliveryNoteReference = deliveryNoteReference;
            return this;
        }

        public Delivery build() {
            return delivery;
        }
    }
}
