package io.github.jcodeforge.invoice4jzugferd.zugferd;

public enum ZugferdProfile {

    MINIMUM("urn:factur-x.eu:1p0:minimum"),

    BASIC_WL("urn:factur-x.eu:1p0:basicwl"),

    BASIC("urn:cen.eu:en16931:2017#compliant#urn:factur-x.eu:1p0:basic"),

    EN16931("urn:cen.eu:en16931:2017"),

    EXTENDED("urn:cen.eu:en16931:2017#conformant#urn:factur-x.eu:1p0:extended");

    private final String guidelineId;

    ZugferdProfile(String guidelineId) {
        this.guidelineId = guidelineId;
    }

    public String getGuidelineId() {
        return guidelineId;
    }
}
