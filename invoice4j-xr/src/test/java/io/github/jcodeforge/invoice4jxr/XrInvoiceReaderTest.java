package io.github.jcodeforge.invoice4jxr;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class XrInvoiceReaderTest {

    private final XrInvoiceReader SUT = XrInvoiceReader.builder()
            .validate(false)
            .build();

    @Test
    public void shouldReadUblInvoice() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = XrUblInvoiceWriter.builder()
                .validate(false)
                .build()
                .writeToString(invoice);

        Invoice imported = SUT.readFromString(xml);

        assertNotNull(imported);
        assertEquals(invoice.getInvoiceNumber(), imported.getInvoiceNumber());
    }

    @Test
    public void shouldReadCiiInvoice() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = XrCiiInvoiceWriter.builder()
                .validate(false)
                .build()
                .writeToString(invoice);

        Invoice imported = SUT.readFromString(xml);

        assertNotNull(imported);
        assertEquals(invoice.getInvoiceNumber(), imported.getInvoiceNumber());
    }

    @Test
    public void shouldReadUblInvoiceFromFile() throws IOException {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        File file = File.createTempFile("xrechnung-", ".xml");
        file.deleteOnExit();

        XrUblInvoiceWriter.builder()
                .validate(false)
                .build()
                .writeToFile(invoice, file);

        Invoice imported = SUT.readFromFile(file);

        assertNotNull(imported);
        assertEquals(invoice.getInvoiceNumber(), imported.getInvoiceNumber());
    }

    @Test(expected = DeserializationException.class)
    public void shouldRejectEmptyXml() {
        SUT.readFromString("");
    }

    @Test(expected = DeserializationException.class)
    public void shouldRejectUnsupportedFormat() {
        SUT.readFromString("""
                <?xml version="1.0" encoding="UTF-8"?>
                <UnknownDocument/>
                """);
    }

    @Test(expected = DeserializationException.class)
    public void shouldRejectInvalidXml() {
        SUT.readFromString("<Invoice>");
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullXml() {
        SUT.readFromString(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullFile() {
        SUT.readFromFile(null);
    }
}