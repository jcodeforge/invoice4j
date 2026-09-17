package io.github.jcodeforge.invoice4jxr;

import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class XrUblInvoiceWriterTest {

    private final XrUblInvoiceWriter SUT = XrUblInvoiceWriter.builder()
            .profile(XrProfile.XRECHNUNG)
            .prettyPrint(true)
            .build();

    @Test
    public void shouldWriteXRechnungUbl() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);

        assertNotNull(xml);
        assertTrue(xml.contains("<Invoice"));
        assertTrue(xml.contains(
                "urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0"
        ));
        assertTrue(xml.contains(
                "urn:fdc:peppol.eu:2017:poacc:billing:01:1.0"
        ));
        assertTrue(xml.contains(
                "<cbc:ID>" + invoice.getInvoiceNumber() + "</cbc:ID>"
        ));
        assertTrue(xml.contains("<cbc:InvoiceTypeCode>380</cbc:InvoiceTypeCode>"));
        assertTrue(xml.contains(
                "<cbc:DocumentCurrencyCode>"
                        + invoice.getCurrency().getCode()
                        + "</cbc:DocumentCurrencyCode>"
        ));
    }

    @Test
    public void shouldWriteXRechnungUblContext() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);

        assertTrue(xml.contains("<cbc:CustomizationID>"));
        assertTrue(xml.contains("<cbc:ProfileID>"));
        assertTrue(xml.contains(
                "urn:fdc:peppol.eu:2017:poacc:billing:01:1.0"
        ));
        assertTrue(xml.contains(
                "urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0"
        ));
    }

    @Test
    public void shouldValidateXRechnungUblByDefault() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = XrUblInvoiceWriter.builder()
                .build()
                .writeToString(invoice);

        assertNotNull(xml);
    }

    @Test
    public void shouldAllowDisablingValidation() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = XrUblInvoiceWriter.builder()
                .validate(false)
                .build()
                .writeToString(invoice);

        assertNotNull(xml);
    }
}