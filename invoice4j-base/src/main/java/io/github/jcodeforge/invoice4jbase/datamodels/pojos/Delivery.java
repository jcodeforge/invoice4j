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

    public LocalDate getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(LocalDate actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public LocalDate getDeliveryPeriodStartDate() {
        return deliveryPeriodStartDate;
    }

    public void setDeliveryPeriodStartDate(LocalDate deliveryPeriodStartDate) {
        this.deliveryPeriodStartDate = deliveryPeriodStartDate;
    }

    public LocalDate getDeliveryPeriodEndDate() {
        return deliveryPeriodEndDate;
    }

    public void setDeliveryPeriodEndDate(LocalDate deliveryPeriodEndDate) {
        this.deliveryPeriodEndDate = deliveryPeriodEndDate;
    }

    public ShipTo getShipTo() {
        return shipTo;
    }

    public void setShipTo(ShipTo shipTo) {
        this.shipTo = shipTo;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDeliveryNoteReference() {
        return deliveryNoteReference;
    }

    public void setDeliveryNoteReference(String deliveryNoteReference) {
        this.deliveryNoteReference = deliveryNoteReference;
    }
}
