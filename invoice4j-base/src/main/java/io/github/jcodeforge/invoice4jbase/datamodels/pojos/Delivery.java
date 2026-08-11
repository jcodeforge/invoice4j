package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
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
            // BT-73 / BT-74
            // Delivery period dates must either both be present or both absent.
            if ((delivery.deliveryPeriodStartDate == null) != (delivery.deliveryPeriodEndDate == null)) {
                throw new InvoiceValidationException("BT-73 and BT-74 must either both be present or both be absent.");
            }

            if (delivery.deliveryPeriodStartDate != null
                    && delivery.deliveryPeriodStartDate.isAfter(delivery.deliveryPeriodEndDate)) {
                throw new InvoiceValidationException("BT-73 Delivery period start date must not be after BT-74 Delivery period end date.");
            }
            if (delivery.actualDeliveryDate != null && delivery.deliveryPeriodStartDate != null) {
                if (delivery.actualDeliveryDate.isBefore(delivery.deliveryPeriodStartDate)) {
                    throw new InvoiceValidationException(
                            "BT-72 Actual delivery date must not be before BT-73 Delivery period start.");
                }
                if (delivery.actualDeliveryDate.isAfter(delivery.deliveryPeriodEndDate)) {
                    throw new InvoiceValidationException(
                            "BT-72 Actual delivery date must not be after BT-74 Delivery period end.");
                }
            }

            if (delivery.deliveryNoteReference != null && delivery.deliveryNoteReference.isBlank()) {
                throw new InvoiceValidationException("Delivery note reference must not be blank.");
            }

            return delivery;
        }
    }
}
