package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import java.time.LocalDate;

/**
 * BG-20
 * Payment terms.
 *
 * Defines conditions and instructions related to invoice payment.
 */
public class PaymentTerms {

    /**
     * BT-9
     * Payment due date.
     */
    private LocalDate dueDate;

    /**
     * BT-20
     * Payment terms text.
     *
     * Example:
     * "Payment due within 14 days."
     */
    private String description;

    /**
     * Payment discount information.
     */
    private PaymentDiscount discount;

    private PaymentTerms() {
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return description;
    }

    public PaymentDiscount getDiscount() {
        return discount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final PaymentTerms paymentTerms;

        private Builder() {
            this.paymentTerms = new PaymentTerms();
        }

        public Builder dueDate(LocalDate dueDate) {
            paymentTerms.dueDate = dueDate;
            return this;
        }

        public Builder description(String description) {
            paymentTerms.description = description;
            return this;
        }

        public Builder discount(PaymentDiscount discount) {
            paymentTerms.discount = discount;
            return this;
        }

        public PaymentTerms build() {
            return paymentTerms;
        }
    }
}
