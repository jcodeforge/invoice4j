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

    private ItemProperty() {
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final ItemProperty itemProperty;

        private Builder() {
            this.itemProperty = new ItemProperty();
        }

        public Builder name(String name) {
            itemProperty.name = name;
            return this;
        }

        public Builder value(String value) {
            itemProperty.value = value;
            return this;
        }

        public ItemProperty build() {
            return itemProperty;
        }
    }
}
