package io.github.jcodeforge.invoice4jzugferd.examples;

import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiInvoiceWriter;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiProfile;
import java.io.File;

/**
 * Demonstrates how to generate a minimal EN 16931 CII invoice.
 */
public final class QuickStart {

    public static void main(String[] args) {
        // Create an invoice
        Invoice invoice = MinimalInvoiceFactory.createMinimalInvoice();

        InvoiceCalculator calculator = new InvoiceCalculator();

        // Calculate totals and taxes
        Invoice calculatedInvoice = calculator.calculate(invoice);

        // Create a writer
        CiiInvoiceWriter writer = CiiInvoiceWriter.builder()
                .profile(CiiProfile.EN16931)
                .prettyPrint(true)
                .build();

        // Write the invoice
        writer.writeToFile(calculatedInvoice, new File("minimal-invoice.xml"));

        System.out.println("Invoice written to minimal-invoice.xml");
    }
}
