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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public IdentifierScheme getScheme() {
        return scheme;
    }

    public void setScheme(IdentifierScheme scheme) {
        this.scheme = scheme;
    }
}
