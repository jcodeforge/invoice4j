package io.github.jcodeforge.invoice4jbase.calculation;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.AllowanceCharge;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoiceLine;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Calculates all derived values of a single invoice line.
 *
 * <p>The calculator computes values derived from the invoice line input
 * data according to the EN 16931 calculation model.</p>
 *
 * <p>The following value is calculated:</p>
 * <ul>
 *     <li>BT-131 &ndash; Invoice line net amount (Line Extension Amount).</li>
 * </ul>
 *
 * <p>BT-146 (net price), BT-147 (price discount), and BT-148
 * (gross price) are input values and are preserved unchanged.</p>
 *
 * <p>The returned {@link InvoiceLine} is a new instance containing
 * the calculated values while preserving the remaining properties
 * of the original invoice line.</p>
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

        BigDecimal lineExtensionAmount = calculateLineExtensionAmount(line);

        return InvoiceLine.builder()
                .id(line.getId())
                .note(line.getNote())
                .buyerAccountingReference(line.getBuyerAccountingReference())
                .itemName(line.getItemName())
                .description(line.getDescription())
                .sellerAssignedIdentifier(line.getSellerAssignedIdentifier())
                .buyerAssignedIdentifier(line.getBuyerAssignedIdentifier())
                .itemClassificationIdentifier(line.getItemClassificationIdentifier())
                .quantity(line.getQuantity())
                .unitCode(line.getUnitCode())
                .netPrice(line.getNetPrice())
                .priceDiscount(line.getPriceDiscount())
                .grossPrice(line.getGrossPrice())
                .baseQuantity(line.getBaseQuantity())
                .taxCategory(line.getTaxCategory())
                .taxRate(line.getTaxRate())
                .allowanceCharges(line.getAllowanceCharges())
                .properties(line.getProperties())
                .lineExtensionAmount(createMonetaryAmount(lineExtensionAmount, line))
                .build();
    }

    private BigDecimal calculateLineExtensionAmount(InvoiceLine line) {
        BigDecimal lineAmount = Money.multiply(
                line.getNetPrice().getAmount(),
                line.getQuantity()
        );

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
                .currency(line.getNetPrice().getCurrency())
                .build();
    }
}
