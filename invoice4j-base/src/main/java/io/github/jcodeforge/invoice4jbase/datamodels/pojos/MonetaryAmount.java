package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
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
            return monetaryAmount;
        }
    }
}
