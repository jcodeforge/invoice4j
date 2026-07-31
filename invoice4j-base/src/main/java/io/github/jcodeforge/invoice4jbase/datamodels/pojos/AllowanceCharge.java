package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;

import java.math.BigDecimal;

/**
 * BG-20 / BG-21
 *
 * Document level allowance or charge.
 */
public class AllowanceCharge {

    /**
     * Indicates whether this is a charge or an allowance.
     *
     * true  = charge
     * false = allowance
     *
     * BT-100
     */
    private boolean charge;

    /**
     * Reason code.
     *
     * BT-105 (allowance reason code)
     * BT-151 (charge reason code)
     */
    private String reasonCode;

    /**
     * Reason text.
     *
     * BT-97 (allowance reason)
     * BT-104 (charge reason)
     */
    private String reason;

    /**
     * Percentage applied.
     *
     * BT-94 (allowance)
     * BT-145 (charge)
     */
    private BigDecimal percentage;

    /**
     * Base amount.
     *
     * BT-93 (allowance)
     * BT-142 (charge)
     */
    private BigDecimal baseAmount;

    /**
     * Amount of allowance/charge.
     *
     * BT-92 (allowance)
     * BT-99 (charge)
     */
    private BigDecimal amount;

    /**
     * VAT category.
     */
    private TaxCategoryCode taxCategory;

    /**
     * VAT rate.
     */
    private BigDecimal taxRate;


    public boolean isCharge() {
        return charge;
    }

    public void setCharge(boolean charge) {
        this.charge = charge;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TaxCategoryCode getTaxCategory() {
        return taxCategory;
    }

    public void setTaxCategory(TaxCategoryCode taxCategory) {
        this.taxCategory = taxCategory;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}
