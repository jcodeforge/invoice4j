package io.github.jcodeforge.invoice4jbase.calculations;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Calculates the VAT breakdown (BG-23) for an invoice.
 *
 * <p>The calculator groups invoice lines by VAT category and VAT rate,
 * applies document-level allowances and charges, and produces one
 * {@link Tax} object for each unique VAT combination.</p>
 *
 * <p>Each generated VAT breakdown contains:</p>
 * <ul>
 *     <li>BT-118 VAT category code</li>
 *     <li>BT-119 VAT rate</li>
 *     <li>BT-116 VAT taxable amount</li>
 *     <li>BT-117 VAT amount</li>
 * </ul>
 *
 * <p>Exemption reason code (BT-120) and exemption reason text (BT-121)
 * are not calculated by this class and must be supplied separately
 * when required by the invoice profile.</p>
 */
public final class TaxCalculator {

    public List<Tax> calculate(Invoice invoice) {
        Objects.requireNonNull(invoice);
        Map<TaxKey, BigDecimal> taxableAmounts = new LinkedHashMap<>();

        // Sum invoice line amounts
        for (InvoiceLine line : invoice.getLines()) {
            TaxKey key = new TaxKey(line.getTaxCategory(), line.getTaxRate());

            BigDecimal current = taxableAmounts.getOrDefault(key, BigDecimal.ZERO);
            current = Money.add(current, line.getLineExtensionAmount().getAmount());

            taxableAmounts.put(key, current);
        }

        // Apply document level allowance / charges
        applyAllowanceCharges(taxableAmounts, invoice.getAllowanceCharges());

        //
        // Create VAT breakdown
        //
        List<Tax> taxes = new ArrayList<>();
        for (Map.Entry<TaxKey, BigDecimal> entry : taxableAmounts.entrySet()) {
            taxes.add(createTax(entry.getKey(), entry.getValue(), invoice.getCurrency()));
        }

        return taxes;
    }

    /**
     * Applies document level allowance/charges
     * to the taxable amounts.
     */
    private void applyAllowanceCharges(Map<TaxKey, BigDecimal> taxableAmounts, List<AllowanceCharge> allowanceCharges) {
        if (allowanceCharges == null || allowanceCharges.isEmpty()) {
            return;
        }

        for (AllowanceCharge allowanceCharge : allowanceCharges) {
            TaxKey key = new TaxKey(allowanceCharge.getTaxCategory(), allowanceCharge.getTaxRate());
            BigDecimal current = taxableAmounts.getOrDefault(key, BigDecimal.ZERO);

            BigDecimal amount = allowanceCharge.getAmount().getAmount();

            if (allowanceCharge.isCharge()) {
                current = Money.add(current, amount);
            } else {
                current = Money.subtract(current, amount);
            }

            taxableAmounts.put(key, current);
        }
    }

    /**
     * VAT group key.
     */
    private static final class TaxKey {

        private final TaxCategoryCode category;

        private final BigDecimal rate;

        private TaxKey(TaxCategoryCode category, BigDecimal rate) {
            this.category = category;
            this.rate = rate;
        }

        public TaxCategoryCode getCategory() {
            return category;
        }

        public BigDecimal getRate() {
            return rate;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (!(obj instanceof TaxKey other)) {
                return false;
            }

            return Objects.equals(category, other.category) && Objects.equals(rate, other.rate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(category, rate);
        }
    }

    /**
     * Creates one BG-23 VAT breakdown entry.
     *
     * Note: Keep TaxCalculator purely computational and let the caller provide the exemption information
     * afterwards.
     */
    private Tax createTax(TaxKey key, BigDecimal taxableAmount, CurrencyCode currency) {
        BigDecimal taxAmount = Money.percentage(taxableAmount, key.getRate());

        return Tax.builder()
                .categoryCode(key.getCategory())
                .rate(key.getRate())
                .taxableAmount(
                        MonetaryAmount.builder()
                                .amount(Money.round(taxableAmount))
                                .currency(currency)
                                .build())
                .taxAmount(
                        MonetaryAmount.builder()
                                .amount(Money.round(taxAmount))
                                .currency(currency)
                                .build())
                .build();
    }
}
