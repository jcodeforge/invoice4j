package io.github.jcodeforge.invoice4jxr.examples;

import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jxr.XrInvoiceReader;
import io.github.jcodeforge.invoice4jxr.XrProfile;
import io.github.jcodeforge.invoice4jxr.XrUblInvoiceWriter;
import java.io.File;

public final class XRechnungUblExample {

    public static void main(String[] args) {
        /*
         * Create your invoice using the invoice4j base model.
         *
         * In a real application this would normally come from
         * your application/database.
         */
        Invoice invoice = new InvoiceCalculator().calculate(createInvoice());

        /*
         * Create an XRechnung UBL writer.
         *
         * Validation is enabled by default:
         *
         * - UBL 2.1 XSD validation
         * - KoSIT XRechnung validation
         * - Genericode validation
         */
        XrUblInvoiceWriter writer = XrUblInvoiceWriter.builder()
                .profile(XrProfile.XRECHNUNG)
                .prettyPrint(true)
                .build();

        /*
         * Write XRechnung UBL XML.
         *
         * If validation fails, a validation exception is thrown.
         */
        File file = new File("complete-xrechnung-invoice-ubl.xml");

        writer.writeToFile(invoice, file);

        System.out.println("XRechnung UBL invoice written successfully.");

        /*
         * Read the generated XRechnung UBL invoice again.
         */
        XrInvoiceReader reader = XrInvoiceReader.builder().build();

        Invoice imported = reader.readFromFile(file);

        System.out.println(
                "Imported invoice: " + imported.getInvoiceNumber()
        );
    }

    private static Invoice createInvoice() {
        /*
         * Replace this with your actual invoice construction.
         */
        return TestInvoiceFactory.createCompleteInvoice();
    }
}