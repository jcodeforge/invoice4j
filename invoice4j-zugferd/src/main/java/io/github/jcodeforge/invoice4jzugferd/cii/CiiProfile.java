package io.github.jcodeforge.invoice4jzugferd.cii;

/**
 * Supported Cross Industry Invoice (CII) profiles.
 *
 * <p>Each profile defines the Guideline ID (BT-24) and, if applicable,
 * the Business Process ID (BT-23) that are written into the
 * ExchangedDocumentContext.</p>
 */
public enum CiiProfile {

    /**
     * Generic EN 16931 profile.
     */
    EN16931(
            "urn:cen.eu:en16931:2017",
            null
    ),

    /**
     * PEPPOL BIS Billing 3.0 profile.
     */
    PEPPOL_BIS_BILLING_3(
            "urn:fdc:peppol.eu:2017:poacc:billing:3.0",
            "urn:fdc:peppol.eu:2017:poacc:billing:01:1.0"
    ),

    /**
     * XRechnung 3.x profile.
     */
    XRECHNUNG(
            "urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0",
            null
    ),

    /**
     * ZUGFeRD / Factur-X EN16931 profile.
     */
    ZUGFERD_EN16931(
            "urn:cen.eu:en16931:2017#compliant#urn:factur-x.eu:1p0:en16931",
            null
    ),

    /**
     * ZUGFeRD BASIC profile.
     */
    ZUGFERD_BASIC(
            "urn:factur-x.eu:1p0:basic",
            null
    );

    /**
     * BT-24
     * Specification / Guideline identifier.
     */
    private final String guidelineId;

    /**
     * BT-23
     * Business process identifier.
     * May be {@code null} if the profile does not define one.
     */
    private final String businessProcessId;

    CiiProfile(String guidelineId, String businessProcessId) {
        this.guidelineId = guidelineId;
        this.businessProcessId = businessProcessId;
    }

    /**
     * Returns the Guideline ID (BT-24).
     */
    public String getGuidelineId() {
        return guidelineId;
    }

    /**
     * Returns the Business Process ID (BT-23),
     * or {@code null} if not defined.
     */
    public String getBusinessProcessId() {
        return businessProcessId;
    }

    /**
     * Returns whether this profile defines a Business Process ID.
     */
    public boolean hasBusinessProcessId() {
        return businessProcessId != null && !businessProcessId.isBlank();
    }
}