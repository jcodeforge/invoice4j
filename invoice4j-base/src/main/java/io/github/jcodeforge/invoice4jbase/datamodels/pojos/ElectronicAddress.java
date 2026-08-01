package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

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
    private String scheme;

    public String getValue() {
        return value;
    }

    public String getScheme() {
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

        public Builder scheme(String scheme) {
            electronicAddress.scheme = scheme;
            return this;
        }

        public ElectronicAddress build() {
            return electronicAddress;
        }
    }
}
