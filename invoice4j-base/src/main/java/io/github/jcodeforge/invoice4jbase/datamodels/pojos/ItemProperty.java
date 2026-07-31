package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

/**
 * BG-32
 *
 * Item property / attribute.
 */
public class ItemProperty {

    /**
     * BT-160
     * Item property name.
     *
     * Example:
     * Color
     * Size
     * Material
     */
    private String name;

    /**
     * BT-161
     * Item property value.
     *
     * Example:
     * Black
     * XL
     * Stainless steel
     */
    private String value;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
