package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;

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

        public Builder value(String identifier) {
            identifierScheme.identifier = identifier == null ? null : identifier.trim();
            return this;
        }

        public Builder name(String name) {
            identifierScheme.name = name == null ? null : name.trim();
            return this;
        }

        public IdentifierScheme build() {
            if (identifierScheme.identifier == null || identifierScheme.identifier.isBlank()) {
                throw new InvoiceValidationException("Identifier scheme code is required.");
            }
            if (identifierScheme.identifier.length() > 64) {
                throw new InvoiceValidationException("Identifier scheme code is too long.");
            }
            if (identifierScheme.name != null) {
                if (identifierScheme.name.isBlank()) {
                    throw new InvoiceValidationException("Identifier scheme name must not be blank.");
                }
                if (identifierScheme.name.length() > 256) {
                    throw new InvoiceValidationException("Identifier scheme name is too long.");
                }
            }

            return identifierScheme;
        }
    }
}
