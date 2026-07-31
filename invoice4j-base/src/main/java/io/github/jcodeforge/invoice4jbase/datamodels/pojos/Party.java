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
     * Electronic address (Endpoint ID).
     */
    private String electronicAddress;

    /**
     * Electronic address scheme identifier.
     */
    private String electronicAddressScheme;

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

    public String getElectronicAddress() {
        return electronicAddress;
    }

    public void setElectronicAddress(String electronicAddress) {
        this.electronicAddress = electronicAddress;
    }

    public String getElectronicAddressScheme() {
        return electronicAddressScheme;
    }

    public void setElectronicAddressScheme(String electronicAddressScheme) {
        this.electronicAddressScheme = electronicAddressScheme;
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
