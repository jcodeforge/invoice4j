package io.github.jcodeforge.invoice4jbase.ubl;

/**
 * Supported UBL invoice profiles.
 *
 * <p>Each profile defines the Specification ID (BT-24) and,
 * if applicable, the Business Process ID (BT-23).</p>
 */
public enum UblProfile {

    /**
     * Generic EN 16931 UBL profile.
     */
    EN16931(
            "urn:cen.eu:en16931:2017",
            null
    ),

    /**
     * PEPPOL BIS Billing 3.0 UBL profile.
     */
    PEPPOL_BIS_BILLING_3(
            "urn:cen.eu:en16931:2017",
            "urn:fdc:peppol.eu:2017:poacc:billing:01:1.0"
    ),

    /**
     * XRechnung UBL profile.
     */
    XRECHNUNG(
            "urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0",
            "urn:fdc:peppol.eu:2017:poacc:billing:01:1.0"
    );

    /**
     * BT-24
     * Specification / guideline identifier.
     *
     * <p>Mapped to {@code cbc:CustomizationID} in UBL.</p>
     */
    private final String customizationId;

    /**
     * BT-23
     * Business process identifier.
     *
     * <p>Mapped to {@code cbc:ProfileID} in UBL.</p>
     */
    private final String profileId;

    UblProfile(String customizationId, String profileId) {
        this.customizationId = customizationId;
        this.profileId = profileId;
    }

    /**
     * Returns the Specification ID (BT-24).
     *
     * @return customization identifier
     */
    public String getCustomizationId() {
        return customizationId;
    }

    /**
     * Returns the Business Process ID (BT-23).
     *
     * @return business process identifier, or {@code null}
     */
    public String getProfileId() {
        return profileId;
    }

    /**
     * Returns whether this profile defines a Business Process ID.
     *
     * @return {@code true} if a ProfileID is defined
     */
    public boolean hasProfileId() {
        return profileId != null && !profileId.isBlank();
    }
}