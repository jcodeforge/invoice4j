package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import java.math.BigDecimal;

public class MonetaryAmount {

    private BigDecimal amount;
    private CurrencyCode currency;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyCode currency) {
        this.currency = currency;
    }
}
