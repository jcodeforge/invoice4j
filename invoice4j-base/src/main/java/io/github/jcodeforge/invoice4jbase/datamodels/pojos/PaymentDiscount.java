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
    private MonetaryAmount amount;

    /**
     * Number of days for discount payment.
     */
    private Integer paymentDays;

    private PaymentDiscount() {
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public MonetaryAmount getAmount() {
        return amount;
    }

    public Integer getPaymentDays() {
        return paymentDays;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final PaymentDiscount paymentDiscount;

        private Builder() {
            this.paymentDiscount = new PaymentDiscount();
        }

        public Builder percentage(BigDecimal percentage) {
            paymentDiscount.percentage = percentage;
            return this;
        }

        public Builder amount(MonetaryAmount amount) {
            paymentDiscount.amount = amount;
            return this;
        }

        public Builder paymentDays(Integer paymentDays) {
            paymentDiscount.paymentDays = paymentDays;
            return this;
        }

        public PaymentDiscount build() {
            return paymentDiscount;
        }
    }
}
