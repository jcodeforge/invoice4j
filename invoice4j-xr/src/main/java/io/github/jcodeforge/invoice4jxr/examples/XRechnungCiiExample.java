package io.github.jcodeforge.invoice4jxr.examples;

import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jxr.XrCiiInvoiceReader;
import io.github.jcodeforge.invoice4jxr.XrCiiInvoiceWriter;
import io.github.jcodeforge.invoice4jxr.XrProfile;
import java.io.File;

public final class XRechnungCiiExample {

    public static void main(String[] args) {
        /*
         * Create your invoice using the invoice4j base model.
         *
         * In a real application this would normally come from
         * your application/database.
         */
        Invoice invoice = new InvoiceCalculator().calculate(createInvoice());

        /*
         * Create an XRechnung CII writer.
         *
         * Validation is enabled by default:
         *
         * - CII 16B XSD validation
         * - KoSIT XRechnung validation
         */
        XrCiiInvoiceWriter writer = XrCiiInvoiceWriter.builder()
                .profile(XrProfile.XRECHNUNG)
                .prettyPrint(true)
                .build();

        /*
         * Write XRechnung CII XML.
         *
         * If validation fails, a validation exception is thrown.
         */

        File file = new File("xrechnung.xml");

        writer.writeToFile(invoice, file);

        System.out.println("XRechnung CII invoice written successfully.");

        XrCiiInvoiceReader reader = XrCiiInvoiceReader.builder().build();

        Invoice imported = reader.readFromFile(file);

        System.out.println("Imported invoice: " + imported.getInvoiceNumber());
    }

    private static Invoice createInvoice() {
        /*
         * Replace this with your actual invoice construction.
         */
        return TestInvoiceFactory.createCompleteInvoice();
    }
}
