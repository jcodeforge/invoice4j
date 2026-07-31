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
    private BigDecimal lineExtensionAmount;

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
    private BigDecimal taxAmount;

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
    private BigDecimal payableAmount;


    public BigDecimal getLineExtensionAmount() {
        return lineExtensionAmount;
    }

    public void setLineExtensionAmount(BigDecimal lineExtensionAmount) {
        this.lineExtensionAmount = lineExtensionAmount;
    }

    public BigDecimal getAllowanceTotalAmount() {
        return allowanceTotalAmount;
    }

    public void setAllowanceTotalAmount(BigDecimal allowanceTotalAmount) {
        this.allowanceTotalAmount = allowanceTotalAmount;
    }

    public BigDecimal getChargeTotalAmount() {
        return chargeTotalAmount;
    }

    public void setChargeTotalAmount(BigDecimal chargeTotalAmount) {
        this.chargeTotalAmount = chargeTotalAmount;
    }

    public BigDecimal getTaxExclusiveAmount() {
        return taxExclusiveAmount;
    }

    public void setTaxExclusiveAmount(BigDecimal taxExclusiveAmount) {
        this.taxExclusiveAmount = taxExclusiveAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTaxInclusiveAmount() {
        return taxInclusiveAmount;
    }

    public void setTaxInclusiveAmount(BigDecimal taxInclusiveAmount) {
        this.taxInclusiveAmount = taxInclusiveAmount;
    }

    public BigDecimal getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(BigDecimal prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }

    public BigDecimal getRoundingAmount() {
        return roundingAmount;
    }

    public void setRoundingAmount(BigDecimal roundingAmount) {
        this.roundingAmount = roundingAmount;
    }

    public BigDecimal getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(BigDecimal payableAmount) {
        this.payableAmount = payableAmount;
    }
}
