package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

/**
 * Common base class for all business parties.
 *
 * Extended by:
 * - Seller
 * - Buyer
 * - Payee
 * - ShipTo
 */
public abstract class Party {

    /**
     * Party name.
     */
    protected String name;

    /**
     * Trading or business name.
     */
    protected String tradingName;

    /**
     * Electronic address.
     */
    protected ElectronicAddress electronicAddress;

    /**
     * Postal address.
     */
    protected Address address;

    /**
     * Contact information.
     */
    protected Contact contact;

    protected Party() {
    }

    public String getName() {
        return name;
    }

    public String getTradingName() {
        return tradingName;
    }

    public ElectronicAddress getElectronicAddress() {
        return electronicAddress;
    }


    public Address getAddress() {
        return address;
    }

    public Contact getContact() {
        return contact;
    }
}
