package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.math.BigDecimal;

/**
 * BG-23
 *
 * VAT breakdown information.
 */
public class Tax {

    private static final BigDecimal MAX_VAT_RATE = BigDecimal.valueOf(100);

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
            if (tax.categoryCode == null) {
                throw new InvoiceValidationException("BT-118 VAT category code is required.");
            }
            if (tax.taxableAmount == null) {
                throw new InvoiceValidationException("BT-116 VAT taxable amount is required.");
            }
            if (tax.taxAmount == null) {
                throw new InvoiceValidationException("BT-117 VAT amount is required.");
            }

            if (tax.rate != null) {
                if (tax.rate.compareTo(BigDecimal.ZERO) < 0) {
                    throw new InvoiceValidationException("BT-119 VAT rate must not be negative.");
                }
                if (tax.rate.compareTo(MAX_VAT_RATE) > 0) {
                    throw new InvoiceValidationException("BT-119 VAT rate must not exceed 100.");
                }
            }

            if (tax.taxableAmount.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                throw new InvoiceValidationException("BT-116 VAT taxable amount must not be negative.");
            }
            if (tax.taxAmount.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                throw new InvoiceValidationException("BT-117 VAT amount must not be negative.");
            }
            if (tax.exemptionReasonCode != null && tax.exemptionReasonCode.isBlank()) {
                throw new InvoiceValidationException("BT-120 VAT exemption reason code must not be blank.");
            }
            if (tax.exemptionReason != null && tax.exemptionReason.isBlank()) {
                throw new InvoiceValidationException("BT-121 VAT exemption reason must not be blank.");
            }
            if (tax.taxableAmount.getCurrency() == null || tax.taxAmount.getCurrency() == null) {
                throw new InvoiceValidationException("Currency is required.");
            }
            if (!tax.taxableAmount.getCurrency().getCode().equals(tax.taxAmount.getCurrency().getCode())) {
                throw new InvoiceValidationException("BT-116 and BT-117 must use the same currency.");
            }

            // Consistency rules
            if (tax.categoryCode == TaxCategoryCode.STANDARD) {
                if (tax.rate == null) {
                    throw new InvoiceValidationException("BT-119 VAT rate is required for standard rated VAT.");
                }
            }

            if (tax.categoryCode != TaxCategoryCode.STANDARD && tax.rate != null && tax.rate.compareTo(BigDecimal.ZERO) == 0) {
                // Valid for many reduced/exempt categories,
                // so no validation here.
            }

            // Exemption information
            if ((tax.exemptionReasonCode != null || tax.exemptionReason != null) && tax.categoryCode == TaxCategoryCode.STANDARD) {
                throw new InvoiceValidationException("VAT exemption information is not allowed for standard rated VAT.");
            }

            return tax;
        }
    }
}
