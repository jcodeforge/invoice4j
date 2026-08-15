package io.github.jcodeforge.invoice4jxr;

public enum XrProfile {

    XRECHNUNG(
            "urn:cen.eu:en16931:2017"
                    + "#compliant"
                    + "#urn:xeinkauf.de:kosit:xrechnung_3.0"
    );

    private final String guidelineId;

    XrProfile(String guidelineId) {
        this.guidelineId = guidelineId;
    }

    public String getGuidelineId() {
        return guidelineId;
    }
}
