package io.github.jcodeforge.invoice4jzugferd.examples;

import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdPdfReader;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdPdfWriter;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdProfile;
import java.io.File;

/**
 * Demonstrates the basic usage of the invoice4j ZUGFeRD module.
 *
 * <p>This example shows how to:
 * <ol>
 *     <li>Create an invoice.</li>
 *     <li>Calculate invoice totals and taxes.</li>
 *     <li>Create a ZUGFeRD PDF containing the invoice XML.</li>
 *     <li>Read the ZUGFeRD PDF back into an {@link Invoice}.</li>
 * </ol>
 */
public final class QuickStart {

    public static void main(String[] args) {

        // Create an invoice
        Invoice invoice = MinimalInvoiceFactory.createMinimalInvoice();

        // Calculate totals and taxes
        Invoice calculatedInvoice = new InvoiceCalculator().calculate(invoice);

        // Input PDF containing the visual invoice
        File inputPdf = new File("examples/invoice.pdf");

        // Output ZUGFeRD PDF
        File outputPdf = new File("zugferd-invoice.pdf");

        // Create the ZUGFeRD PDF
        ZugferdPdfWriter.builder()
                .invoice(calculatedInvoice)
                .profile(ZugferdProfile.EN16931)
                .prettyPrintXml(true)
                .build()
                .write(inputPdf, outputPdf);

        System.out.println("ZUGFeRD invoice written to " + outputPdf.getAbsolutePath());

        // Read the ZUGFeRD PDF back
        Invoice parsedInvoice = ZugferdPdfReader.builder()
                .build()
                .read(outputPdf);

        System.out.println("Invoice successfully read.");
        System.out.println("Invoice number: " + parsedInvoice.getInvoiceNumber());
        System.out.println("Seller: " + parsedInvoice.getSeller().getName());
        System.out.println("Buyer: " + parsedInvoice.getBuyer().getName());
        System.out.println(
                "Payable amount: "
                        + parsedInvoice.getMonetarySummation()
                        .getPayableAmount()
                        .getAmount()
                        + " "
                        + parsedInvoice.getCurrency()
        );
    }
}