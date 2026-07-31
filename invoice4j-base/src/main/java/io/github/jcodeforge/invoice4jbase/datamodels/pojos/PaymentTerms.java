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


    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PaymentDiscount getDiscount() {
        return discount;
    }

    public void setDiscount(PaymentDiscount discount) {
        this.discount = discount;
    }
}
