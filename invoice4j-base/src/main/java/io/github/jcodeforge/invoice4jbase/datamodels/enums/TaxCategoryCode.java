package io.github.jcodeforge.invoice4jbase.datamodels.enums;

public enum TaxCategoryCode {

    STANDARD("S"),
    ZERO_RATED("Z"),
    EXEMPT("E"),
    REVERSE_CHARGE("AE"),
    OUTSIDE_SCOPE("O"),
    EXPORT("G"),
    INTRA_COMMUNITY("K"),
    CANARY_ISLANDS("L"),
    CEUTA_MELILLA("M");

    private final String code;

    TaxCategoryCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static TaxCategoryCode fromCode(String code) {
        for (TaxCategoryCode category : values()) {
            if (category.code.equals(code)) {
                return category;
            }
        }

        throw new IllegalArgumentException("Unknown tax category code: " + code);
    }
}
