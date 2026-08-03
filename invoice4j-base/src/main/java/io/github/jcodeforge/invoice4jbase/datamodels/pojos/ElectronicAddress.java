package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.IdentifierScheme;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;

public class ElectronicAddress {

    /**
     * Electronic address value.
     * Example: "buyer@example.com" or "991-123456789"
     */
    private String value;

    /**
     * Identifier scheme.
     * Examples:
     * - 0088 = GLN
     * - 0204 = Leitweg-ID (Germany)
     * - 9930 = VAT number
     * - EM = Email
     */
    private IdentifierScheme scheme;

    public String getValue() {
        return value;
    }

    public IdentifierScheme getScheme() {
        return scheme;
    }

    private ElectronicAddress() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final ElectronicAddress electronicAddress;

        private Builder() {
            this.electronicAddress = new ElectronicAddress();
        }

        public Builder value(String value) {
            electronicAddress.value = value;
            return this;
        }

        public Builder scheme(IdentifierScheme scheme) {
            electronicAddress.scheme = scheme;
            return this;
        }

        public ElectronicAddress build() {
            if (electronicAddress.value == null || electronicAddress.value.isBlank()) {
                throw new InvoiceValidationException("Electronic address value must not be null or blank.");
            }
            if (electronicAddress.scheme == null) {
                throw new InvoiceValidationException("Electronic address scheme must not be null.");
            }
            return electronicAddress;
        }
    }
}
