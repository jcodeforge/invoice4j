package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.math.BigDecimal;

public class MonetaryAmount {

    private BigDecimal amount;

    private CurrencyCode currency;

    private MonetaryAmount() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final MonetaryAmount monetaryAmount;

        private Builder() {
            this.monetaryAmount = new MonetaryAmount();
        }

        public Builder amount(BigDecimal amount) {
            monetaryAmount.amount = amount;
            return this;
        }

        public Builder currency(CurrencyCode currency) {
            monetaryAmount.currency = currency;
            return this;
        }

        public MonetaryAmount build() {
            if (monetaryAmount.amount == null) {
                throw new InvoiceValidationException("Monetary amount is required.");
            }
            if (monetaryAmount.currency == null) {
                throw new InvoiceValidationException("Currency is required.");
            }
            // Maximum of 2 fraction digits according to EN 16931
            if (monetaryAmount.amount.scale() > 2) {
                throw new InvoiceValidationException("Monetary amount must not contain more than two decimal places.");
            }

            return monetaryAmount;
        }
    }
}
