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
public class Party {

    /**
     * Party name.
     */
    private String name;

    /**
     * Trading or business name.
     */
    private String tradingName;

    /**
     * Electronic address.
     */
    private ElectronicAddress electronicAddress;

    /**
     * Postal address.
     */
    private Address address;

    /**
     * Contact information.
     */
    private Contact contact;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public ElectronicAddress getElectronicAddress() {
        return electronicAddress;
    }

    public void setElectronicAddress(ElectronicAddress electronicAddress) {
        this.electronicAddress = electronicAddress;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
