package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;

/**
 * Party identifier.
 *
 * Used to identify business parties such as sellers, buyers,
 * payees, and other invoice participants.
 *
 * Examples:
 * - GLN
 * - Peppol Participant ID
 * - Customer number
 * - Supplier number
 */
public class PartyIdentifier {

    private static final int MAX_IDENTIFIER_LENGTH = 256;

    /**
     * BT-29 / BT-46
     *
     * Identifier value.
     */
    private String value;

    /**
     * Identification scheme.
     *
     * Example:
     * - GLN
     * - 0088
     * - 0208
     */
    private IdentifierScheme scheme;

    private PartyIdentifier() {
    }

    public String getValue() {
        return value;
    }

    public IdentifierScheme getScheme() {
        return scheme;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final PartyIdentifier partyIdentifier;

        private Builder() {
            this.partyIdentifier = new PartyIdentifier();
        }

        public Builder value(String value) {
            partyIdentifier.value = value == null ? null : value.trim();
            return this;
        }

        public Builder scheme(IdentifierScheme scheme) {
            partyIdentifier.scheme = scheme;
            return this;
        }

        public PartyIdentifier build() {
            if (partyIdentifier.value == null || partyIdentifier.value.isBlank()) {
                throw new InvoiceValidationException("BT-29 / BT-46 Party identifier is required.");
            }
            if (partyIdentifier.scheme == null) {
                throw new InvoiceValidationException("Party identifier scheme is required.");
            }
            if (partyIdentifier.value.length() > MAX_IDENTIFIER_LENGTH) {
                throw new InvoiceValidationException("BT-29 / BT-46 Party identifier is too long.");
            }

            return partyIdentifier;
        }
    }
}
