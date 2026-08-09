package io.github.jcodeforge.invoice4jzugferd.examples;

import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiInvoiceReader;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiInvoiceWriter;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiProfile;
import java.io.File;

/**
 * Demonstrates the basic usage of the invoice4j ZUGFeRD module.
 *
 * <p>This example shows how to:
 * <ol>
 *     <li>Create a minimal invoice.</li>
 *     <li>Calculate invoice totals and taxes.</li>
 *     <li>Write the invoice as an EN 16931 Cross Industry Invoice (CII) XML document.</li>
 *     <li>Read the generated XML back into an {@link Invoice}.</li>
 * </ol>
 */
public final class QuickStart {

    public static void main(String[] args) {
        // Create an invoice
        Invoice invoice = MinimalInvoiceFactory.createMinimalInvoice();

        // Calculate totals and taxes
        Invoice calculatedInvoice = new InvoiceCalculator().calculate(invoice);

        // Create a writer
        CiiInvoiceWriter writer = CiiInvoiceWriter.builder()
                .profile(CiiProfile.EN16931)
                .prettyPrint(true)
                .validateAgainstXsd(true)
                .build();

        // Write the invoice
        File file = new File("minimal-invoice.xml");
        writer.writeToFile(calculatedInvoice, file);

        System.out.println("Invoice written to minimal-invoice.xml");

        // Create a CII reader
        CiiInvoiceReader reader = CiiInvoiceReader.builder()
                .validateAgainstXsd(true)
                .build();

        // Read the invoice back
        Invoice parsedInvoice = reader.readFromFile(file);

        System.out.println("Invoice successfully read.");
        System.out.println("Invoice number: " + parsedInvoice.getInvoiceNumber());
        System.out.println("Seller: " + parsedInvoice.getSeller().getName());
        System.out.println("Buyer: " + parsedInvoice.getBuyer().getName());
        System.out.println("Payable amount: "
                + parsedInvoice.getMonetarySummation()
                .getPayableAmount()
                .getAmount()
                + " "
                + parsedInvoice.getCurrency());
    }
}
