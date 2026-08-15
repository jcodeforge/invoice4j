package io.github.jcodeforge.invoice4jzugferd.validation;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetarySummation;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.math.BigDecimal;
import java.util.Objects;

public final class BrCo15Validator implements InvoiceBusinessRule {

    @Override
    public void validate(Invoice invoice) {
        Objects.requireNonNull(invoice, "invoice must not be null");

        MonetarySummation summation = invoice.getMonetarySummation();

        if (summation == null) {
            return;
        }

        BigDecimal taxExclusiveAmount = summation.getTaxExclusiveAmount().getAmount();
        BigDecimal taxTotalAmount = summation.getTaxAmount().getAmount();
        BigDecimal taxInclusiveAmount = summation.getTaxInclusiveAmount().getAmount();
        BigDecimal expectedTaxInclusive = taxExclusiveAmount.add(taxTotalAmount);

        if (expectedTaxInclusive.compareTo(taxInclusiveAmount) != 0) {
            throw new InvoiceValidationException(
                    "BR-CO-15: BT-112 Invoice total with VAT must equal "
                            + "BT-109 Invoice total without VAT plus BT-110 VAT total."
            );
        }
    }
}