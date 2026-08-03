package io.github.jcodeforge.invoice4jbase.calculation;

import io.github.jcodeforge.invoice4jbase.DefaultInvoiceValidator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoiceLine;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetarySummation;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Tax;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Calculates all derived values of an invoice.
 *
 * <p>The calculation is performed in three steps:</p>
 *
 * <ol>
 *     <li>Calculate all invoice lines.</li>
 *     <li>Calculate the VAT breakdown.</li>
 *     <li>Calculate the monetary summation.</li>
 * </ol>
 *
 * <p>The original invoice is never modified. Instead, a new immutable
 * {@link Invoice} instance containing all calculated values is returned.</p>
 *
 * <p>This class is stateless and thread-safe.</p>
 */
public final class InvoiceCalculator {

    private final InvoiceLineCalculator invoiceLineCalculator = new InvoiceLineCalculator();

    private final TaxCalculator taxCalculator = new TaxCalculator();

    private final MonetarySummationCalculator monetarySummationCalculator = new MonetarySummationCalculator();

    private final DefaultInvoiceValidator validator = new DefaultInvoiceValidator();

    /**
     * Calculates all derived invoice values.
     *
     * @param invoice the invoice to calculate
     * @return the calculated invoice
     * @throws NullPointerException if {@code invoice} is {@code null}
     */
    public Invoice calculate(Invoice invoice) {
        Objects.requireNonNull(invoice, "Invoice must not be null.");

        List<InvoiceLine> calculatedLines = calculateInvoiceLines(invoice);
        Invoice invoiceWithCalculatedLines = createInvoiceWithCalculatedLines(invoice, calculatedLines);

        List<Tax> calculatedTaxes = taxCalculator.calculate(invoiceWithCalculatedLines);
        Invoice invoiceWithCalculatedTaxes = createInvoiceWithCalculatedTaxes(invoiceWithCalculatedLines, calculatedTaxes);

        MonetarySummation monetarySummation = monetarySummationCalculator.calculate(invoiceWithCalculatedTaxes);

        Invoice calculatedInvoice = createCalculatedInvoice(invoiceWithCalculatedTaxes, monetarySummation);

        validator.validate(calculatedInvoice);

        return calculatedInvoice;
    }

    /**
     * Calculates all invoice lines.
     *
     * @param invoice the invoice
     * @return the calculated invoice lines
     */
    private List<InvoiceLine> calculateInvoiceLines(Invoice invoice) {
        List<InvoiceLine> calculatedLines = new ArrayList<>();

        for (InvoiceLine line : invoice.getLines()) {
            calculatedLines.add(invoiceLineCalculator.calculate(line));
        }

        return calculatedLines;
    }

    /**
     * Creates an invoice containing the calculated invoice lines.
     *
     * @param invoice the original invoice
     * @param calculatedLines the calculated invoice lines
     * @return an invoice containing the calculated lines
     */
    private Invoice createInvoiceWithCalculatedLines(Invoice invoice, List<InvoiceLine> calculatedLines) {
        return Invoice.builder()
                .from(invoice)
                .lines(calculatedLines)
                .build();
    }

    /**
     * Creates an invoice containing the calculated VAT breakdown.
     *
     * @param invoice the invoice containing the calculated lines
     * @param calculatedTaxes the calculated VAT breakdown
     * @return an invoice containing the calculated taxes
     */
    private Invoice createInvoiceWithCalculatedTaxes(Invoice invoice, List<Tax> calculatedTaxes) {
        return Invoice.builder()
                .from(invoice)
                .taxes(calculatedTaxes)
                .build();
    }

    /**
     * Creates the final calculated invoice.
     *
     * @param invoice the invoice containing calculated lines and taxes
     * @param monetarySummation the calculated monetary summation
     * @return the fully calculated invoice
     */
    private Invoice createCalculatedInvoice(Invoice invoice, MonetarySummation monetarySummation) {
        return Invoice.builder()
                .from(invoice)
                .monetarySummation(monetarySummation)
                .build();
    }
}
