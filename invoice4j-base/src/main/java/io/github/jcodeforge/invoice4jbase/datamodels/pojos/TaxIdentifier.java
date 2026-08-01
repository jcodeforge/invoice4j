package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxIdentifierType;

public class TaxIdentifier {

    private String value;

    private TaxIdentifierType type;

    private TaxIdentifier() {
    }

    public String getValue() {
        return value;
    }

    public TaxIdentifierType getType() {
        return type;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final TaxIdentifier taxIdentifier;

        private Builder() {
            this.taxIdentifier = new TaxIdentifier();
        }

        public Builder value(String value) {
            taxIdentifier.value = value;
            return this;
        }

        public Builder type(TaxIdentifierType type) {
            taxIdentifier.type = type;
            return this;
        }

        public TaxIdentifier build() {
            return taxIdentifier;
        }
    }
}
