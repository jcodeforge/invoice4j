package io.github.jcodeforge.invoice4jbase.datamodels.enums;

/**
 * ISO 4217 currency codes.
 */
public enum CurrencyCode {

    EUR("EUR"),
    USD("USD"),
    GBP("GBP"),
    CHF("CHF"),
    JPY("JPY"),
    CNY("CNY");

    private final String code;

    CurrencyCode(String code) {
        this.code = code;
    }

    /**
     * Returns the ISO 4217 code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Creates currency from ISO code.
     */
    public static CurrencyCode fromCode(String code) {
        for (CurrencyCode currency : values()) {
            if (currency.code.equalsIgnoreCase(code)) {
                return currency;
            }
        }

        throw new IllegalArgumentException(
                "Unknown currency code: " + code
        );
    }
}
