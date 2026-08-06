package io.github.jcodeforge.invoice4jbase.datamodels.enums;

public enum TaxCategoryCode {

    STANDARD("S"),
    ZERO_RATED("Z"),
    EXEMPT("E"),
    REVERSE_CHARGE("AE"),
    OUTSIDE_SCOPE("O"),
    REDUCED_RATE("AA");

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
