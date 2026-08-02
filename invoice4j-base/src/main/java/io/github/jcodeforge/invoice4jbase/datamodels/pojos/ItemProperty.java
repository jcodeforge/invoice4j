package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;

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
            itemProperty.name = name == null ? null : name.trim();
            return this;
        }

        public Builder value(String value) {
            itemProperty.value = value == null ? null : value.trim();
            return this;
        }

        public ItemProperty build() {
            if (itemProperty.name == null || itemProperty.name.isBlank()) {
                throw new InvoiceValidationException("BT-160 Item property name is required.");
            }
            if (itemProperty.value == null || itemProperty.value.isBlank()) {
                throw new InvoiceValidationException("BT-161 Item property value is required.");
            }
            if (itemProperty.name.length() > 256) {
                throw new InvoiceValidationException("BT-160 Item property name is too long.");
            }
            if (itemProperty.value.length() > 1024) {
                throw new InvoiceValidationException("BT-161 Item property value is too long.");
            }

            return itemProperty;
        }
    }
}
