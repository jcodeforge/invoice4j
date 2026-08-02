package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.math.BigDecimal;

/**
 * BG-22
 *
 * Invoice monetary totals.
 */
public class MonetarySummation {

    /**
     * BT-106
     * Sum of invoice line net amounts.
     */
    private MonetaryAmount lineExtensionAmount;

    /**
     * BT-107
     * Sum of allowances on document level.
     */
    private MonetaryAmount  allowanceTotalAmount;

    /**
     * BT-108
     * Sum of charges on document level.
     */
    private MonetaryAmount  chargeTotalAmount;

    /**
     * BT-109
     * Invoice net amount.
     *
     * Line amounts + charges - allowances.
     */
    private MonetaryAmount  taxExclusiveAmount;

    /**
     * BT-110
     * Total VAT amount.
     */
    private MonetaryAmount taxAmount;

    /**
     * BT-111
     * Invoice gross amount.
     *
     * Tax exclusive amount + VAT.
     */
    private MonetaryAmount  taxInclusiveAmount;

    /**
     * BT-112
     * Paid amount.
     */
    private MonetaryAmount  prepaidAmount;

    /**
     * BT-113
     * Rounding amount.
     */
    private MonetaryAmount  roundingAmount;

    /**
     * BT-115
     * Amount due for payment.
     */
    private MonetaryAmount payableAmount;

    private MonetarySummation() {
    }

    public MonetaryAmount getLineExtensionAmount() {
        return lineExtensionAmount;
    }

    public MonetaryAmount  getAllowanceTotalAmount() {
        return allowanceTotalAmount;
    }

    public MonetaryAmount  getChargeTotalAmount() {
        return chargeTotalAmount;
    }

    public MonetaryAmount  getTaxExclusiveAmount() {
        return taxExclusiveAmount;
    }

    public MonetaryAmount getTaxAmount() {
        return taxAmount;
    }

    public MonetaryAmount  getTaxInclusiveAmount() {
        return taxInclusiveAmount;
    }

    public MonetaryAmount  getPrepaidAmount() {
        return prepaidAmount;
    }

    public MonetaryAmount  getRoundingAmount() {
        return roundingAmount;
    }

    public MonetaryAmount getPayableAmount() {
        return payableAmount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final MonetarySummation monetarySummation;

        private Builder() {
            this.monetarySummation = new MonetarySummation();
        }

        public Builder lineExtensionAmount(MonetaryAmount lineExtensionAmount) {
            monetarySummation.lineExtensionAmount = lineExtensionAmount;
            return this;
        }

        public Builder allowanceTotalAmount(MonetaryAmount  allowanceTotalAmount) {
            monetarySummation.allowanceTotalAmount = allowanceTotalAmount;
            return this;
        }

        public Builder chargeTotalAmount(MonetaryAmount  chargeTotalAmount) {
            monetarySummation.chargeTotalAmount = chargeTotalAmount;
            return this;
        }

        public Builder taxExclusiveAmount(MonetaryAmount  taxExclusiveAmount) {
            monetarySummation.taxExclusiveAmount = taxExclusiveAmount;
            return this;
        }

        public Builder taxAmount(MonetaryAmount taxAmount) {
            monetarySummation.taxAmount = taxAmount;
            return this;
        }

        public Builder taxInclusiveAmount(MonetaryAmount  taxInclusiveAmount) {
            monetarySummation.taxInclusiveAmount = taxInclusiveAmount;
            return this;
        }

        public Builder prepaidAmount(MonetaryAmount  prepaidAmount) {
            monetarySummation.prepaidAmount = prepaidAmount;
            return this;
        }

        public Builder roundingAmount(MonetaryAmount  roundingAmount) {
            monetarySummation.roundingAmount = roundingAmount;
            return this;
        }

        public Builder payableAmount(MonetaryAmount payableAmount) {
            monetarySummation.payableAmount = payableAmount;
            return this;
        }

        private static void validateNonNegative(MonetaryAmount amount, String bt, String name) {
            if (amount != null && amount.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                throw new InvoiceValidationException(bt + " " + name + " must not be negative.");
            }
        }

        private static void validateCurrency(MonetaryAmount expected, MonetaryAmount actual, String bt) {
            if (actual == null) {
                return;
            }
            if (actual.getCurrency() == null) {
                throw new InvoiceValidationException(bt + " currency is required.");
            }
            if (!expected.getCurrency().equals(actual.getCurrency())) {
                throw new InvoiceValidationException(bt + " must use the same currency.");
            }
        }

        public MonetarySummation build() {
            if (monetarySummation.lineExtensionAmount == null) {
                throw new InvoiceValidationException("BT-106 Invoice line net amount total is required.");
            }
            if (monetarySummation.taxAmount == null) {
                throw new InvoiceValidationException("BT-110 VAT total amount is required.");
            }
            if (monetarySummation.payableAmount == null) {
                throw new InvoiceValidationException("BT-115 Payable amount is required.");
            }

            // BT-113 may be positive or negative depending on implementation.
            // Therefore only check for null if you decide to require it.

            // Currency consistency

            if (monetarySummation.lineExtensionAmount.getCurrency() == null || monetarySummation.taxAmount.getCurrency() == null
                    || monetarySummation.payableAmount.getCurrency() == null) {
                throw new InvoiceValidationException("Currency is required for monetary amounts.");
            }

            validateNonNegative(monetarySummation.lineExtensionAmount, "BT-106", "Invoice line total amount");
            validateNonNegative(monetarySummation.allowanceTotalAmount, "BT-107", "Allowance total amount");
            validateNonNegative(monetarySummation.chargeTotalAmount, "BT-108", "Charge total amount");
            validateNonNegative(monetarySummation.taxAmount, "BT-110", "VAT total amount");
            validateNonNegative(monetarySummation.payableAmount, "BT-115", "Payable amount");
            validateNonNegative(monetarySummation.taxExclusiveAmount, "BT-109", "Tax exclusive amount");
            validateNonNegative(monetarySummation.taxInclusiveAmount, "BT-111", "Tax inclusive amount");
            validateNonNegative(monetarySummation.prepaidAmount, "BT-112", "Prepaid amount");

            validateCurrency(monetarySummation.lineExtensionAmount, monetarySummation.allowanceTotalAmount, "BT-107");
            validateCurrency(monetarySummation.lineExtensionAmount, monetarySummation.chargeTotalAmount, "BT-108");
            validateCurrency(monetarySummation.lineExtensionAmount, monetarySummation.taxExclusiveAmount, "BT-109");
            validateCurrency(monetarySummation.lineExtensionAmount, monetarySummation.taxInclusiveAmount, "BT-111");
            validateCurrency(monetarySummation.lineExtensionAmount, monetarySummation.prepaidAmount, "BT-112");
            validateCurrency(monetarySummation.lineExtensionAmount, monetarySummation.roundingAmount, "BT-113");
            validateCurrency(monetarySummation.lineExtensionAmount, monetarySummation.payableAmount, "BT-115");

            return monetarySummation;
        }
    }
}
