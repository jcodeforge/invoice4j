package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import java.math.BigDecimal;

/**
 * BG-23
 *
 * VAT breakdown information.
 */
public class Tax {

    /**
     * BT-118
     * VAT category code.
     */
    private TaxCategoryCode categoryCode;

    /**
     * BT-119
     * VAT rate percentage.
     */
    private BigDecimal rate;

    /**
     * BT-116
     * VAT taxable amount.
     */
    private MonetaryAmount taxableAmount;

    /**
     * BT-117
     * VAT amount.
     */
    private MonetaryAmount taxAmount;

    /**
     * VAT exemption reason code.
     *
     * BT-120
     */
    private String exemptionReasonCode;

    /**
     * VAT exemption reason text.
     *
     * BT-121
     */
    private String exemptionReason;

    private Tax() {
    }

    public TaxCategoryCode getCategoryCode() {
        return categoryCode;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public MonetaryAmount getTaxableAmount() {
        return taxableAmount;
    }

    public MonetaryAmount getTaxAmount() {
        return taxAmount;
    }

    public String getExemptionReasonCode() {
        return exemptionReasonCode;
    }

    public String getExemptionReason() {
        return exemptionReason;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Tax tax;

        private Builder() {
            this.tax = new Tax();
        }

        public Builder categoryCode(TaxCategoryCode categoryCode) {
            tax.categoryCode = categoryCode;
            return this;
        }

        public Builder rate(BigDecimal rate) {
            tax.rate = rate;
            return this;
        }

        public Builder taxableAmount(MonetaryAmount taxableAmount) {
            tax.taxableAmount = taxableAmount;
            return this;
        }

        public Builder taxAmount(MonetaryAmount taxAmount) {
            tax.taxAmount = taxAmount;
            return this;
        }

        public Builder exemptionReasonCode(String exemptionReasonCode) {
            tax.exemptionReasonCode = exemptionReasonCode;
            return this;
        }

        public Builder exemptionReason(String exemptionReason) {
            tax.exemptionReason = exemptionReason;
            return this;
        }

        public Tax build() {
            return tax;
        }
    }
}
