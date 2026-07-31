package io.github.jcodeforge.invoice4jbase.datamodels.enums;


/**
 * Payment means code.
 *
 * Based on UN/CEFACT 4461 payment means codes.
 */
public enum PaymentMeansCode {

    /**
     * 10 - In cash
     */
    CASH("10"),

    /**
     * 30 - Credit transfer
     */
    CREDIT_TRANSFER("30"),

    /**
     * 48 - Bank card
     */
    BANK_CARD("48"),

    /**
     * 49 - Direct debit
     */
    DIRECT_DEBIT("49"),

    /**
     * 58 - SEPA credit transfer
     */
    SEPA_CREDIT_TRANSFER("58"),

    /**
     * 59 - SEPA direct debit
     */
    SEPA_DIRECT_DEBIT("59");

    private final String code;

    PaymentMeansCode(String code) {
        this.code = code;
    }

    /**
     * Returns the UN/CEFACT payment means code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Find payment means by code.
     */
    public static PaymentMeansCode fromCode(String code) {
        for (PaymentMeansCode value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }

        throw new IllegalArgumentException("Unknown payment means code: " + code);
    }
}
