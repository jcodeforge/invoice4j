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

    public PartyIdentifier getLocationIdentifier() {
        return locationIdentifier;
    }

    public void setLocationIdentifier(PartyIdentifier locationIdentifier) {
        this.locationIdentifier = locationIdentifier;
    }
}
