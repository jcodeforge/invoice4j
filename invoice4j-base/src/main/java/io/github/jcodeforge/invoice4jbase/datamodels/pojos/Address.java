package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CountryCode;

/**
 * Postal address.
 *
 * Used by Party and Delivery.
 */
public class Address {

    /**
     * BT-35 / BT-50 / BT-61 / BT-75
     *
     * Street name.
     */
    private String street;

    /**
     * Additional address line.
     */
    private String additionalStreet;

    /**
     * BT-36 / BT-51 / BT-62 / BT-76
     *
     * City name.
     */
    private String city;

    /**
     * BT-38 / BT-53 / BT-64 / BT-78
     *
     * Postal code.
     */
    private String postcode;

    /**
     * BT-39 / BT-54 / BT-65 / BT-79
     *
     * Country subdivision.
     *
     * Example:
     * - State
     * - Province
     */
    private String countrySubdivision;

    /**
     * BT-40 / BT-55 / BT-66 / BT-80
     *
     * Country code.
     */
    private CountryCode countryCode;


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdditionalStreet() {
        return additionalStreet;
    }

    public void setAdditionalStreet(String additionalStreet) {
        this.additionalStreet = additionalStreet;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }


    public String getCountrySubdivision() {
        return countrySubdivision;
    }

    public void setCountrySubdivision(String countrySubdivision) {
        this.countrySubdivision = countrySubdivision;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }
}
