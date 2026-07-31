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

    public void setValue(String value) {
        this.value = value;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }
}
