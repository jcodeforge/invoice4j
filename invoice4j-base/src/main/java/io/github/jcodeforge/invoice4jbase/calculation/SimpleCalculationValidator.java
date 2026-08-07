package io.github.jcodeforge.invoice4jbase.calculation;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.*;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public final class SimpleCalculationValidator {

    public void validate(Invoice invoice) {
        validateMonetarySummation(invoice);
    }

    private void validateMonetarySummation(Invoice invoice) {
        Objects.requireNonNull(invoice, "Invoice must not be null.");
        Objects.requireNonNull(invoice.getMonetarySummation(), "Monetary summation must not be null.");
        Objects.requireNonNull(invoice.getAllowanceCharges(), "Allowance charges must not be null.");
        Objects.requireNonNull(invoice.getLines(), "Invoice lines must not be null.");

        MonetarySummation monetarySummation = invoice.getMonetarySummation();
        List<AllowanceCharge> allowanceCharges = invoice.getAllowanceCharges();
        List<InvoiceLine> lines = invoice.getLines();

        BigDecimal lineExtensionTotal = lines.stream()
                .map(line -> line.getLineExtensionAmount().getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal allowanceTotal = allowanceCharges.stream()
                .filter(ac -> !ac.isCharge())
                .map(AllowanceCharge::getAmount)
                .filter(Objects::nonNull)
                .map(MonetaryAmount::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal chargeTotal = allowanceCharges.stream()
                .filter(AllowanceCharge::isCharge)
                .map(AllowanceCharge::getAmount)
                .filter(Objects::nonNull)
                .map(MonetaryAmount::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal taxExclusiveAmount = lineExtensionTotal
                .subtract(allowanceTotal)
                .add(chargeTotal);

        if (lineExtensionTotal.compareTo(monetarySummation.getLineExtensionAmount().getAmount()) != 0) {
            throw new InvoiceValidationException("BT-106 Line extension amount mismatch.");
        }

        if (allowanceTotal.compareTo(monetarySummation.getAllowanceTotalAmount().getAmount()) != 0) {
            throw new InvoiceValidationException("BT-107 Allowance total amount mismatch.");
        }

        if (chargeTotal.compareTo(monetarySummation.getChargeTotalAmount().getAmount()) != 0) {
            throw new InvoiceValidationException("BT-108 Charge total amount mismatch.");
        }

        if (taxExclusiveAmount.compareTo(monetarySummation.getTaxExclusiveAmount().getAmount()) != 0) {
            throw new InvoiceValidationException("BT-109 Tax exclusive amount mismatch.");
        }
    }
}
