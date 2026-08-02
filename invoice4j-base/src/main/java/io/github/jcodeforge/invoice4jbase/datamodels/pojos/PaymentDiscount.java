package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
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
            if (paymentDiscount.percentage == null && paymentDiscount.amount == null) {
                throw new InvoiceValidationException("A discount percentage or amount must be specified.");
            }
            if (paymentDiscount.percentage != null) {
                if (paymentDiscount.percentage.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new InvoiceValidationException("Discount percentage must be greater than zero.");
                }
                if (paymentDiscount.percentage.compareTo(BigDecimal.valueOf(100)) > 0) {
                    throw new InvoiceValidationException("Discount percentage must not exceed 100%.");
                }
            }
            if (paymentDiscount.amount != null && paymentDiscount.amount.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new InvoiceValidationException("Discount amount must be greater than zero.");
            }
            if (paymentDiscount.paymentDays == null || paymentDiscount.paymentDays <= 0) {
                throw new InvoiceValidationException("Payment days must be greater than zero.");
            }

            return paymentDiscount;
        }
    }
}
