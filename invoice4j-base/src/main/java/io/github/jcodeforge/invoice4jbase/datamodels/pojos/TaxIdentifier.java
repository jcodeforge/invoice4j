package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxRegistrationScheme;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;

public class TaxIdentifier {

    private String value;

    private TaxRegistrationScheme type;

    private TaxIdentifier() {
    }

    public String getValue() {
        return value;
    }

    public TaxRegistrationScheme getType() {
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

        public Builder type(TaxRegistrationScheme type) {
            taxIdentifier.type = type;
            return this;
        }

        public TaxIdentifier build() {
            if (taxIdentifier.value == null || taxIdentifier.value.isBlank()) {
                throw new InvoiceValidationException("Tax identifier value must not be null or blank.");
            }
            if (taxIdentifier.type == null) {
                throw new InvoiceValidationException("Tax identifier type must not be null.");
            }
            return taxIdentifier;
        }
    }
}
