package io.github.jcodeforge.invoice4jbase.calculations;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.AllowanceCharge;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoiceLine;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Calculates all derived values of a single invoice line.
 *
 * Computes:
 * - effective unit price
 * - BT-131 Line Extension Amount
 */
public final class InvoiceLineCalculator {

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

    private BigDecimal calculateEffectiveUnitPrice(InvoiceLine line) {
        return Money.subtract(line.getUnitPrice().getAmount(), calculateDiscount(line));
    }

    private BigDecimal calculateDiscount(InvoiceLine line) {
        if (line.getPriceDiscount() != null) {
            return line.getPriceDiscount();
        }
        if (line.getPriceDiscountPercentage() != null) {
            return Money.percentage(line.getUnitPrice().getAmount(), line.getPriceDiscountPercentage());
        }

        return BigDecimal.ZERO;
    }

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

    private MonetaryAmount createMonetaryAmount(BigDecimal amount, InvoiceLine line) {
        return MonetaryAmount.builder()
                .amount(amount)
                .currency(line.getUnitPrice().getCurrency())
                .build();
    }
}
