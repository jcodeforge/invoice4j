package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
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
    private MonetaryAmount baseAmount;

    /**
     * Amount of allowance/charge.
     *
     * BT-92 (allowance)
     * BT-99 (charge)
     */
    private MonetaryAmount amount;

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

    public MonetaryAmount getBaseAmount() {
        return baseAmount;
    }

    public MonetaryAmount getAmount() {
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
            allowanceCharge.reasonCode = reasonCode == null ? null : reasonCode.trim();
            return this;
        }

        public Builder reason(String reason) {
            allowanceCharge.reason = reason == null ? null : reason.trim();
            return this;
        }

        public Builder percentage(BigDecimal percentage) {
            allowanceCharge.percentage = percentage;
            return this;
        }

        public Builder baseAmount(MonetaryAmount baseAmount) {
            allowanceCharge.baseAmount = baseAmount;
            return this;
        }

        public Builder amount(MonetaryAmount amount) {
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
            if (allowanceCharge.amount == null) {
                throw new InvoiceValidationException((allowanceCharge.charge ? "BT-99" : "BT-92")
                        + " Allowance/charge amount is required.");
            }
            if (allowanceCharge.amount.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                throw new InvoiceValidationException(
                        (allowanceCharge.charge ? "BT-99" : "BT-92") + " Allowance/charge amount must not be negative.");
            }
            if (allowanceCharge.reasonCode != null && allowanceCharge.reasonCode.isBlank()) {
                throw new InvoiceValidationException((allowanceCharge.charge ? "BT-151" : "BT-95")
                        + " Reason code must not be blank.");
            }
            if (allowanceCharge.reason != null && allowanceCharge.reason.isBlank()) {
                throw new InvoiceValidationException((allowanceCharge.charge ? "BT-104" : "BT-97") + " Reason must not be blank.");
            }
            if (allowanceCharge.percentage != null) {
                if (allowanceCharge.percentage.compareTo(BigDecimal.ZERO) < 0) {
                    throw new InvoiceValidationException("Percentage must not be negative.");
                }
                if (allowanceCharge.percentage.compareTo(BigDecimal.valueOf(100)) > 0) {
                    throw new InvoiceValidationException("Percentage must not exceed 100.");
                }
                if (allowanceCharge.baseAmount == null) {
                    throw new InvoiceValidationException("Base amount is required when percentage is specified.");
                }
            }
            if (allowanceCharge.baseAmount != null && allowanceCharge.baseAmount.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                throw new InvoiceValidationException("Base amount must not be negative.");
            }
            if (allowanceCharge.taxCategory == null) {
                throw new InvoiceValidationException("VAT category is required.");
            }
            if (allowanceCharge.taxRate == null) {
                throw new InvoiceValidationException("VAT rate is required.");
            }

            if (allowanceCharge.taxRate.compareTo(BigDecimal.ZERO) < 0) {
                throw new InvoiceValidationException("VAT rate must not be negative.");
            }
            if (allowanceCharge.taxRate.compareTo(BigDecimal.valueOf(100)) > 0) {
                throw new InvoiceValidationException("VAT rate must not exceed 100.");
            }
            if (allowanceCharge.baseAmount != null
                    && !allowanceCharge.baseAmount.getCurrency().equals(allowanceCharge.amount.getCurrency())) {
                throw new InvoiceValidationException("Base amount and allowance/charge amount must use the same currency.");
            }

            return allowanceCharge;
        }
    }
}
