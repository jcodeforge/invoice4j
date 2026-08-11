package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CountryCode;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;

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

    private Address() {
    }

    public String getStreet() {
        return street;
    }

    public String getAdditionalStreet() {
        return additionalStreet;
    }

    public String getCity() {
        return city;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCountrySubdivision() {
        return countrySubdivision;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Address address;

        private Builder() {
            this.address = new Address();
        }

        public Builder street(String street) {
            address.street = street;
            return this;
        }

        public Builder additionalStreet(String additionalStreet) {
            address.additionalStreet = additionalStreet;
            return this;
        }

        public Builder city(String city) {
            address.city = city;
            return this;
        }

        public Builder postcode(String postcode) {
            address.postcode = postcode;
            return this;
        }

        public Builder countrySubdivision(String countrySubdivision) {
            address.countrySubdivision = countrySubdivision;
            return this;
        }

        public Builder countryCode(CountryCode countryCode) {
            address.countryCode = countryCode;
            return this;
        }

        public Address build() {
            if (address.street != null && address.street.isBlank()) {
                throw new InvoiceValidationException("BT-35 / BT-50 / BT-61 / BT-75 street is required.");
            }
            if (address.city != null && address.city.isBlank()) {
                throw new InvoiceValidationException("BT-36 / BT-51 / BT-62 / BT-76 city is required.");
            }
            if (address.postcode != null && address.postcode.isBlank()) {
                throw new InvoiceValidationException("BT-38 / BT-53 / BT-64 / BT-78 post code is required.");
            }
            if (address.countryCode == null) {
                throw new InvoiceValidationException("BT-40 / BT-55 / BT-66 / BT-80 country code is required.");
            }
            return address;
        }
    }
}
