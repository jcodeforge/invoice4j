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

    public IdentifierScheme() {
    }

    public IdentifierScheme(String identifier) {
        this.identifier = identifier;
    }

    public IdentifierScheme(String identifier, String name) {
        this.identifier = identifier;
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
