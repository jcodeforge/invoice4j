package io.github.jcodeforge.invoice4jbase.datamodels.enums;

/**
 * Unit of measure code.
 *
 * Based on UN/ECE Recommendation 20.
 */
public enum UnitCode {

    /**
     * H87 - Piece
     */
    PIECE("H87"),

    /**
     * KGM - Kilogram
     */
    KILOGRAM("KGM"),

    /**
     * MTR - Meter
     */
    METER("MTR"),

    /**
     * LTR - Liter
     */
    LITER("LTR"),

    /**
     * DAY - Day
     */
    DAY("DAY"),

    /**
     * HUR - Hour
     */
    HOUR("HUR"),

    /**
     * MIN - Minute
     */
    MINUTE("MIN"),

    /**
     * C62 - One
     */
    ONE("C62"),

    /**
     * MON - Month
     */
    MONTH("MON");

    private final String code;

    UnitCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static UnitCode fromCode(String code) {
        for (UnitCode unit : values()) {
            if (unit.code.equals(code)) {
                return unit;
            }
        }

        throw new IllegalArgumentException("Unknown unit code: " + code);
    }
}
