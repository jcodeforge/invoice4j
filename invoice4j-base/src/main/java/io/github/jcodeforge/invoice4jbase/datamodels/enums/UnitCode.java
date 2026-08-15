package io.github.jcodeforge.invoice4jbase.datamodels.enums;

/**
 * Unit of measure code.
 *
 * Based on UN/ECE Recommendation 20.
 */
public enum UnitCode {

    /**
     * ANN - Year
     */
    YEAR("ANN"),

    /**
     * MON - Month
     */
    MONTH("MON"),

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
     * SEC - Second
     */
    SECOND("SEC"),

    /**
     * C62 - One
     */
    ONE("C62"),

    /**
     * H87 - Piece
     */
    PIECE("H87"),

    /**
     * KGM - Kilogram
     */
    KILOGRAM("KGM"),

    /**
     * GRM - Gram
     */
    GRAM("GRM"),

    /**
     * TNE - Tonne
     */
    TONNE("TNE"),

    /**
     * MTR - Metre
     */
    METER("MTR"),

    /**
     * DMT - Decimetre
     */
    DECIMETER("DMT"),

    /**
     * CMT - Centimetre
     */
    CENTIMETER("CMT"),

    /**
     * MMT - Millimetre
     */
    MILLIMETER("MMT"),

    /**
     * KMT - Kilometre
     */
    KILOMETER("KMT"),

    /**
     * XPP - Percentage point
     */
    XPP("XPP"),

    /**
     * LTR - Litre
     */
    LITER("LTR"),

    /**
     * MLT - Millilitre
     */
    MILLILITER("MLT"),

    /**
     * KWH - Kilowatt-hour
     */
    KILOWATT_HOUR("KWH"),

    /**
     * WHR - Watt-hour
     */
    WATT_HOUR("WHR"),

    /**
     * HHP - Horsepower hour
     */
    HORSEPOWER_HOUR("HHP"),

    /**
     * MTK - Square metre
     */
    SQUARE_METER("MTK"),

    /**
     * MTR - Metre
     *
     * Note: use MTR only once; retained above.
     */

    /**
     * MTQ - Cubic metre
     */
    CUBIC_METER("MTQ"),

    /**
     * DLT - Decilitre
     */
    DECILITER("DLT"),

    /**
     * CLT - Centilitre
     */
    CENTILITER("CLT"),

    /**
     * KWH - Kilowatt-hour
     *
     * Note: use KWH only once; retained above.
     */

    /**
     * TAH - Thousand ampere hour
     */
    THOUSAND_AMPERE_HOUR("TAH"),

    /**
     * AMP - Ampere
     */
    AMPERE("AMP"),

    /**
     * KVA - Kilovolt-ampere
     */
    KILOVOLT_AMPERE("KVA"),

    /**
     * KVT - Kilovolt
     */
    KILOVOLT("KVT"),

    /**
     * KWT - Kilowatt
     */
    KILOWATT("KWT"),

    /**
     * CMT - Centimetre
     *
     * Note: use CMT only once; retained above.
     */

    /**
     * PCE - Piece
     */
    PCE("PCE"),

    /**
     * SET - Set
     */
    SET("SET"),

    /**
     * PR - Pair
     */
    PAIR("PR"),

    /**
     * DZN - Dozen
     */
    DOZEN("DZN"),

    /**
     * GLL - Gallon
     */
    GALLON("GLL");

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

        throw new IllegalArgumentException(
                "Unknown unit code: " + code
        );
    }
}