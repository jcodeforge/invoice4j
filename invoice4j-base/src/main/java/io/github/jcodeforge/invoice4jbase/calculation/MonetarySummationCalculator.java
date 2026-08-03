package io.github.jcodeforge.invoice4jbase.calculation;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Calculates the invoice monetary summation (BG-22).
 *
 * <p>The calculator computes all monetary totals defined by EN 16931
 * from the calculated invoice lines, VAT breakdown, and document-level
 * allowances and charges.</p>
 *
 * <p>The following business terms are calculated:</p>
 * <ul>
 *     <li>BT-106 Invoice line net amount total</li>
 *     <li>BT-107 Sum of allowances</li>
 *     <li>BT-108 Sum of charges</li>
 *     <li>BT-109 Tax exclusive amount</li>
 *     <li>BT-110 VAT total amount</li>
 *     <li>BT-111 Tax inclusive amount</li>
 *     <li>BT-115 Payable amount</li>
 * </ul>
 */
public final class MonetarySummationCalculator {

    /**
     * Calculates the invoice monetary summation (BG-22).
     *
     * @param invoice the invoice
     * @return the calculated monetary summation
     * @throws NullPointerException if {@code invoice} is {@code null}
     */
    public MonetarySummation calculate(Invoice invoice) {
        Objects.requireNonNull(invoice, "Invoice must not be null.");

        MonetarySummation existing = invoice.getMonetarySummation();

        MonetaryAmount lineExtensionAmount = calculateLineExtensionAmount(invoice);
        MonetaryAmount allowanceTotalAmount = calculateAllowanceTotalAmount(invoice);
        MonetaryAmount chargeTotalAmount = calculateChargeTotalAmount(invoice);

        MonetaryAmount taxExclusiveAmount = calculateTaxExclusiveAmount(lineExtensionAmount,
                        allowanceTotalAmount, chargeTotalAmount);

        MonetaryAmount taxAmount = calculateTaxAmount(invoice);
        MonetaryAmount taxInclusiveAmount = calculateTaxInclusiveAmount(taxExclusiveAmount, taxAmount);

        MonetaryAmount payableAmount = calculatePayableAmount(taxInclusiveAmount,
                invoice.getMonetarySummation().getPrepaidAmount(), invoice.getMonetarySummation().getRoundingAmount());

        return MonetarySummation.builder()
                .lineExtensionAmount(lineExtensionAmount)
                .allowanceTotalAmount(allowanceTotalAmount)
                .chargeTotalAmount(chargeTotalAmount)
                .taxExclusiveAmount(taxExclusiveAmount)
                .taxAmount(taxAmount)
                .taxInclusiveAmount(taxInclusiveAmount)
                .prepaidAmount(existing.getPrepaidAmount())
                .roundingAmount(existing.getRoundingAmount())
                .payableAmount(payableAmount)
                .build();
    }

    /**
     * Calculates BT-106 (Invoice line net amount total).
     *
     * <p>BT-106 is the sum of all invoice line extension amounts
     * (BT-131).</p>
     *
     * @param invoice the invoice
     * @return the calculated invoice line net amount total
     */
    private MonetaryAmount calculateLineExtensionAmount(Invoice invoice) {

        BigDecimal amount = BigDecimal.ZERO;

        for (InvoiceLine line : invoice.getLines()) {
            amount = Money.add(amount, line.getLineExtensionAmount().getAmount());
        }

        return createMonetaryAmount(amount, invoice.getCurrency());
    }

    /**
     * Calculates BT-107 (Document level allowance total).
     *
     * <p>BT-107 is the sum of all document-level allowances.</p>
     *
     * @param invoice the invoice
     * @return the calculated allowance total amount
     */
    private MonetaryAmount calculateAllowanceTotalAmount(Invoice invoice) {
        BigDecimal amount = BigDecimal.ZERO;

        for (AllowanceCharge allowanceCharge : invoice.getAllowanceCharges()) {

            if (allowanceCharge.isCharge()) {
                amount = Money.add(amount, allowanceCharge.getAmount().getAmount());
            }
        }

        return createMonetaryAmount(amount, invoice.getCurrency());
    }

    /**
     * Calculates BT-108 (Document level charge total).
     *
     * <p>BT-108 is the sum of all document-level charges.</p>
     *
     * @param invoice the invoice
     * @return the calculated charge total amount
     */
    private MonetaryAmount calculateChargeTotalAmount(Invoice invoice) {
        BigDecimal amount = BigDecimal.ZERO;

        for (AllowanceCharge allowanceCharge : invoice.getAllowanceCharges()) {

            if (allowanceCharge.isCharge()) {amount = Money.add(amount, allowanceCharge.getAmount().getAmount());
            }
        }

        return createMonetaryAmount(amount, invoice.getCurrency());
    }

    /**
     * Calculates BT-109 (Tax exclusive amount).
     *
     * <p>
     * Formula:
     * </p>
     *
     * <pre>
     * BT-109 = BT-106 + BT-108 - BT-107
     * </pre>
     *
     * @param lineExtensionAmount BT-106
     * @param allowanceTotalAmount BT-107
     * @param chargeTotalAmount BT-108
     * @return the calculated tax exclusive amount
     */
    private MonetaryAmount calculateTaxExclusiveAmount(MonetaryAmount lineExtensionAmount,
                                                       MonetaryAmount allowanceTotalAmount,
                                                       MonetaryAmount chargeTotalAmount) {

        BigDecimal amount = lineExtensionAmount.getAmount();

        amount = Money.add(amount, chargeTotalAmount.getAmount());
        amount = Money.subtract(amount, allowanceTotalAmount.getAmount());

        return createMonetaryAmount(amount, lineExtensionAmount.getCurrency());
    }

    /**
     * Calculates BT-110 (VAT total amount).
     *
     * <p>BT-110 is the sum of all VAT amounts (BT-117)
     * contained in the invoice VAT breakdown (BG-23).</p>
     *
     * @param invoice the invoice
     * @return the calculated VAT total amount
     */
    private MonetaryAmount calculateTaxAmount(Invoice invoice) {
        BigDecimal amount = BigDecimal.ZERO;

        for (Tax tax : invoice.getTaxes()) {
            amount = Money.add(
                    amount,
                    tax.getTaxAmount().getAmount());
        }

        return createMonetaryAmount(amount, invoice.getCurrency());
    }

    /**
     * Calculates BT-111 (Tax inclusive amount).
     *
     * <p>
     * Formula:
     * </p>
     *
     * <pre>
     * BT-111 = BT-109 + BT-110
     * </pre>
     *
     * @param taxExclusiveAmount BT-109
     * @param taxAmount BT-110
     * @return the calculated tax inclusive amount
     */
    private MonetaryAmount calculateTaxInclusiveAmount(MonetaryAmount taxExclusiveAmount, MonetaryAmount taxAmount) {
        BigDecimal amount = Money.add(taxExclusiveAmount.getAmount(), taxAmount.getAmount());

        return createMonetaryAmount(amount, taxExclusiveAmount.getCurrency());
    }

    /**
     * Calculates BT-115 (Amount due for payment).
     *
     * <p>
     * Formula:
     * </p>
     *
     * <pre>
     * BT-115 = BT-111 - BT-112 + BT-113
     * </pre>
     *
     * @param taxInclusiveAmount BT-111
     * @param prepaidAmount BT-112 (may be {@code null})
     * @param roundingAmount BT-113 (may be {@code null})
     * @return the calculated payable amount
     */
    private MonetaryAmount calculatePayableAmount(MonetaryAmount taxInclusiveAmount, MonetaryAmount prepaidAmount,
                                                  MonetaryAmount roundingAmount) {
        BigDecimal amount = taxInclusiveAmount.getAmount();

        if (prepaidAmount != null) {
            amount = Money.subtract(amount, prepaidAmount.getAmount());
        }

        if (roundingAmount != null) {
            amount = Money.add(amount, roundingAmount.getAmount());
        }

        return createMonetaryAmount(amount, taxInclusiveAmount.getCurrency());
    }

    /**
     * Creates a monetary amount using the specified currency.
     *
     * <p>The amount is rounded using the {@link Money} utility
     * before the {@link MonetaryAmount} is created.</p>
     *
     * @param amount the monetary value
     * @param currency the currency of the amount
     * @return a monetary amount containing the rounded value
     * and the specified currency
     */
    private MonetaryAmount createMonetaryAmount(BigDecimal amount, CurrencyCode currency) {
        return MonetaryAmount.builder()
                .amount(Money.round(amount))
                .currency(currency)
                .build();
    }
}
