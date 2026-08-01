package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

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
            partyIdentifier.value = value;
            return this;
        }

        public Builder scheme(IdentifierScheme scheme) {
            partyIdentifier.scheme = scheme;
            return this;
        }

        public PartyIdentifier build() {
            return partyIdentifier;
        }
    }
}
