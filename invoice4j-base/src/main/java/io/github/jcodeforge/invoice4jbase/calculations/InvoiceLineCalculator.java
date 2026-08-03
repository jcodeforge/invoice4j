package io.github.jcodeforge.invoice4jbase.calculations;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.AllowanceCharge;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoiceLine;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Calculates all derived values of a single invoice line.
 *
 * <p>The calculator computes values defined by EN 16931 that can be derived
 * from the invoice line input data.</p>
 *
 * <p>The following values are calculated:</p>
 * <ul>
 *     <li>Effective unit price after applying price discounts.</li>
 *     <li>BT-131 &ndash; Invoice line net amount (Line Extension Amount).</li>
 * </ul>
 *
 * <p>The returned {@link InvoiceLine} is a new immutable instance containing
 * the calculated values while preserving the remaining properties of the
 * original invoice line.</p>
 */
public final class InvoiceLineCalculator {

    /**
     * Calculates all derived values for the given invoice line.
     *
     * @param line the invoice line to calculate
     * @return a new invoice line containing the calculated values
     * @throws NullPointerException if {@code line} is {@code null}
     */
    public InvoiceLine calculate(InvoiceLine line) {
        Objects.requireNonNull(line, "Invoice line must not be null.");

        BigDecimal effectiveUnitPrice = calculateEffectiveUnitPrice(line);
        BigDecimal lineExtensionAmount = calculateLineExtensionAmount(line, effectiveUnitPrice);

        return InvoiceLine.builder()
                .id(line.getId())
                .objectIdentifier(line.getObjectIdentifier())
                .buyerAccountingReference(line.getBuyerAccountingReference())
                .itemName(line.getItemName())
                .description(line.getDescription())
                .sellerItemIdentifier(line.getSellerItemIdentifier())
                .buyerItemIdentifier(line.getBuyerItemIdentifier())
                .itemClassificationIdentifier(line.getItemClassificationIdentifier())
                .quantity(line.getQuantity())
                .unitCode(line.getUnitCode())
                .unitPrice(line.getUnitPrice())
                .priceDiscount(line.getPriceDiscount())
                .priceDiscountPercentage(line.getPriceDiscountPercentage())
                .baseQuantity(line.getBaseQuantity())
                .taxCategory(line.getTaxCategory())
                .taxRate(line.getTaxRate())
                .allowanceCharges(line.getAllowanceCharges())
                .properties(line.getProperties())
                .lineExtensionAmount(createMonetaryAmount(lineExtensionAmount, line))
                .build();
    }

    /**
     * Calculates the effective unit price after applying any configured
     * fixed or percentage discount.
     *
     * @param line the invoice line
     * @return the effective unit price
     */
    private BigDecimal calculateEffectiveUnitPrice(InvoiceLine line) {
        return Money.subtract(line.getUnitPrice().getAmount(), calculateDiscount(line));
    }

    /**
     * Calculates the discount amount for the invoice line.
     *
     * <p>If both a fixed discount and a percentage discount are present,
     * the fixed discount takes precedence.</p>
     *
     * @param line the invoice line
     * @return the calculated discount amount, or {@link BigDecimal#ZERO}
     * if no discount is specified
     */
    private BigDecimal calculateDiscount(InvoiceLine line) {
        if (line.getPriceDiscount() != null) {
            return line.getPriceDiscount();
        }
        if (line.getPriceDiscountPercentage() != null) {
            return Money.percentage(line.getUnitPrice().getAmount(), line.getPriceDiscountPercentage());
        }

        return BigDecimal.ZERO;
    }

    /**
     * Calculates BT-131 (Invoice line net amount).
     *
     * <p>The calculation multiplies the effective unit price by the invoiced
     * quantity and then applies all line-level allowances and charges.</p>
     *
     * @param line the invoice line
     * @param effectiveUnitPrice the effective unit price after discounts
     * @return the calculated line extension amount
     */
    private BigDecimal calculateLineExtensionAmount(InvoiceLine line, BigDecimal effectiveUnitPrice) {
        BigDecimal lineAmount = Money.multiply(effectiveUnitPrice, line.getQuantity());

        for (AllowanceCharge allowanceCharge : line.getAllowanceCharges()) {
            BigDecimal amount = allowanceCharge.getAmount().getAmount();

            if (allowanceCharge.isCharge()) {
                lineAmount = Money.add(lineAmount, amount);
            } else {
                lineAmount = Money.subtract(lineAmount, amount);
            }
        }

        return Money.round(lineAmount);
    }

    /**
     * Creates a monetary amount using the invoice line currency.
     *
     * @param amount the monetary value
     * @param line the invoice line providing the currency
     * @return a monetary amount with the specified value and currency
     */
    private MonetaryAmount createMonetaryAmount(BigDecimal amount, InvoiceLine line) {
        return MonetaryAmount.builder()
                .amount(amount)
                .currency(line.getUnitPrice().getCurrency())
                .build();
    }
}
