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

    private AllowanceCharge() {
    }

    public boolean isCharge() {
        return charge;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public String getReason() {
        return reason;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TaxCategoryCode getTaxCategory() {
        return taxCategory;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final AllowanceCharge allowanceCharge;

        private Builder() {
            this.allowanceCharge = new AllowanceCharge();
        }

        public Builder charge(boolean charge) {
            allowanceCharge.charge = charge;
            return this;
        }

        public Builder reasonCode(String reasonCode) {
            allowanceCharge.reasonCode = reasonCode;
            return this;
        }

        public Builder reason(String reason) {
            allowanceCharge.reason = reason;
            return this;
        }

        public Builder percentage(BigDecimal percentage) {
            allowanceCharge.percentage = percentage;
            return this;
        }

        public Builder baseAmount(BigDecimal baseAmount) {
            allowanceCharge.baseAmount = baseAmount;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            allowanceCharge.amount = amount;
            return this;
        }

        public Builder taxCategory(TaxCategoryCode taxCategory) {
            allowanceCharge.taxCategory = taxCategory;
            return this;
        }

        public Builder taxRate(BigDecimal taxRate) {
            allowanceCharge.taxRate = taxRate;
            return this;
        }

        public AllowanceCharge build() {
            return allowanceCharge;
        }
    }
}
