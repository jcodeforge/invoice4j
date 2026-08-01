package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

/**
 * Identifier scheme.
 *
 * Defines the coding scheme used for a business identifier.
 *
 * Examples:
 * - 0088 (GLN)
 * - 0208 (Belgian enterprise number)
 * - 9930 (German VAT ID)
 * - 0204 (German Leitweg-ID)
 */
public class IdentifierScheme {

    /**
     * Scheme identifier.
     *
     * Usually an ISO/Peppol/XRechnung scheme code.
     */
    private String identifier;

    /**
     * Human-readable scheme name.
     *
     * Example:
     * Global Location Number.
     */
    private String name;

    private IdentifierScheme() {
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final IdentifierScheme identifierScheme;

        private Builder() {
            this.identifierScheme = new IdentifierScheme();
        }

        public Builder value(String value) {
            identifierScheme.identifier = value;
            return this;
        }

        public Builder name(String name) {
            identifierScheme.name = name;
            return this;
        }

        public IdentifierScheme build() {
            return identifierScheme;
        }
    }
}
