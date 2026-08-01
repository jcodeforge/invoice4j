package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

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
    private BigDecimal allowanceTotalAmount;

    /**
     * BT-108
     * Sum of charges on document level.
     */
    private BigDecimal chargeTotalAmount;

    /**
     * BT-109
     * Invoice net amount.
     *
     * Line amounts + charges - allowances.
     */
    private BigDecimal taxExclusiveAmount;

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
    private BigDecimal taxInclusiveAmount;

    /**
     * BT-112
     * Paid amount.
     */
    private BigDecimal prepaidAmount;

    /**
     * BT-113
     * Rounding amount.
     */
    private BigDecimal roundingAmount;

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

    public BigDecimal getAllowanceTotalAmount() {
        return allowanceTotalAmount;
    }

    public BigDecimal getChargeTotalAmount() {
        return chargeTotalAmount;
    }

    public BigDecimal getTaxExclusiveAmount() {
        return taxExclusiveAmount;
    }

    public MonetaryAmount getTaxAmount() {
        return taxAmount;
    }

    public BigDecimal getTaxInclusiveAmount() {
        return taxInclusiveAmount;
    }

    public BigDecimal getPrepaidAmount() {
        return prepaidAmount;
    }

    public BigDecimal getRoundingAmount() {
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

        public Builder allowanceTotalAmount(BigDecimal allowanceTotalAmount) {
            monetarySummation.allowanceTotalAmount = allowanceTotalAmount;
            return this;
        }

        public Builder chargeTotalAmount(BigDecimal chargeTotalAmount) {
            monetarySummation.chargeTotalAmount = chargeTotalAmount;
            return this;
        }

        public Builder taxExclusiveAmount(BigDecimal taxExclusiveAmount) {
            monetarySummation.taxExclusiveAmount = taxExclusiveAmount;
            return this;
        }

        public Builder taxAmount(MonetaryAmount taxAmount) {
            monetarySummation.taxAmount = taxAmount;
            return this;
        }

        public Builder taxInclusiveAmount(BigDecimal taxInclusiveAmount) {
            monetarySummation.taxInclusiveAmount = taxInclusiveAmount;
            return this;
        }

        public Builder prepaidAmount(BigDecimal prepaidAmount) {
            monetarySummation.prepaidAmount = prepaidAmount;
            return this;
        }

        public Builder roundingAmount(BigDecimal roundingAmount) {
            monetarySummation.roundingAmount = roundingAmount;
            return this;
        }

        public Builder payableAmount(MonetaryAmount payableAmount) {
            monetarySummation.payableAmount = payableAmount;
            return this;
        }

        public MonetarySummation build() {
            return monetarySummation;
        }
    }
}
