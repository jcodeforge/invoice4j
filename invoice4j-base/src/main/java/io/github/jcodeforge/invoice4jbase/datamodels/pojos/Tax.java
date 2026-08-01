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


    public TaxCategoryCode getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(TaxCategoryCode categoryCode) {
        this.categoryCode = categoryCode;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public MonetaryAmount getTaxableAmount() {
        return taxableAmount;
    }

    public void setTaxableAmount(MonetaryAmount taxableAmount) {
        this.taxableAmount = taxableAmount;
    }

    public MonetaryAmount getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(MonetaryAmount taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getExemptionReasonCode() {
        return exemptionReasonCode;
    }

    public void setExemptionReasonCode(String exemptionReasonCode) {
        this.exemptionReasonCode = exemptionReasonCode;
    }

    public String getExemptionReason() {
        return exemptionReason;
    }

    public void setExemptionReason(String exemptionReason) {
        this.exemptionReason = exemptionReason;
    }
}
