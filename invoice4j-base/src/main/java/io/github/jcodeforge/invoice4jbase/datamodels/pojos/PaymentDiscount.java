package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import java.math.BigDecimal;

/**
 * Payment discount information.
 */
public class PaymentDiscount {

    /**
     * Discount percentage.
     */
    private BigDecimal percentage;

    /**
     * Discount amount.
     */
    private BigDecimal amount;

    /**
     * Number of days for discount payment.
     */
    private Integer paymentDays;


    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getPaymentDays() {
        return paymentDays;
    }

    public void setPaymentDays(Integer paymentDays) {
        this.paymentDays = paymentDays;
    }
}
