package io.github.jcodeforge.invoice4jbase.datamodels.enums;

/**
 * ISO 3166-1 alpha-2 country codes.
 */
public enum CountryCode {

    DE("DE"),
    AT("AT"),
    CH("CH"),
    FR("FR"),
    IT("IT"),
    ES("ES"),
    NL("NL"),
    BE("BE"),
    US("US"),
    GB("GB");

    private final String code;

    CountryCode(String code) {
        this.code = code;
    }

    /**
     * Returns ISO 3166-1 alpha-2 code.
     */
    public String getCode() {
        return code;
    }

    public static CountryCode fromCode(String code) {
        for (CountryCode country : values()) {
            if (country.code.equalsIgnoreCase(code)) {
                return country;
            }
        }

        throw new IllegalArgumentException("Unknown country code: " + code);
    }
}
