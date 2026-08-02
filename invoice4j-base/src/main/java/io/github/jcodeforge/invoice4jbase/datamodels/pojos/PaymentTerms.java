package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
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
            if (description != null && description.isBlank()) {
                throw new InvoiceValidationException("BT-20 Payment terms description must not be blank.");
            }
            paymentTerms.description = description;
            return this;
        }

        public Builder discount(PaymentDiscount discount) {
            if (discount == null) {
                throw new InvoiceValidationException("Payment discount must not be null.");
            }
            paymentTerms.discount = discount;
            return this;
        }

        public PaymentTerms build() {
            if (paymentTerms.description != null && paymentTerms.description.isBlank()) {
                throw new InvoiceValidationException("BT-20 Payment terms description must not be blank.");
            }
            if (paymentTerms.dueDate == null && paymentTerms.description == null && paymentTerms.discount == null) {
                throw new InvoiceValidationException("Payment terms must contain at least one payment instruction.");
            }
            return paymentTerms;
        }
    }
}
