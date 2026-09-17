package io.github.jcodeforge.invoice4jxr;

import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jxr.exceptions.KositValidationException;
import org.junit.Test;
import java.io.InputStream;

import static org.junit.Assert.*;

public class XrUblReaderTest {

    private final XrUblInvoiceReader SUT = XrUblInvoiceReader.builder().build();

    @Test
    public void shouldReadXRechnungUblSample() throws Exception {
        try (InputStream input = getClass().getResourceAsStream("/xrechnung/ubl/03.07a-INVOICE_ubl.xml")) {

            assertNotNull(input);

            Invoice invoice = SUT.read(input);

            assertNotNull(invoice);

            assertEquals("1234567", invoice.getInvoiceNumber());
            assertNotNull(invoice.getIssueDate());
            assertNotNull(invoice.getCurrency());

            assertEquals("EUR", invoice.getCurrency().getCode());

            assertNotNull(invoice.getSeller());
            assertNotNull(invoice.getBuyer());

            assertEquals(2, invoice.getLines().size());
        }
    }

    @Test
    public void shouldRoundTripXRechnungUbl() {
        Invoice original = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = XrUblInvoiceWriter.builder()
                .profile(XrProfile.XRECHNUNG)
                .build()
                .writeToString(original);

        Invoice parsed = SUT.readFromString(xml);

        assertEquals(original.getInvoiceNumber(), parsed.getInvoiceNumber());
        assertEquals(original.getIssueDate(), parsed.getIssueDate());
        assertEquals(original.getCurrency().getCode(), parsed.getCurrency().getCode());
        assertEquals(original.getLines().size(), parsed.getLines().size());
    }

    @Test(expected = DeserializationException.class)
    public void shouldRejectXmlWithoutXRechnungProfile() {
        SUT.readFromString("<invalid/>");
    }

    @Test
    public void shouldRejectInvalidXRechnungUbl() {
        Invoice original = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = XrUblInvoiceWriter.builder()
                .profile(XrProfile.XRECHNUNG)
                .build()
                .writeToString(original);

        // Deliberately break a mandatory XRechnung value
        xml = xml.replace(
                "<cbc:BuyerReference>BUYER-REF-001</cbc:BuyerReference>",
                "");

        try {
            SUT.readFromString(xml);
            fail("Expected KositValidationException");
        } catch (KositValidationException exception) {
            assertFalse(exception.getValidationResult().isValid());
            assertFalse(exception.getValidationResult().getMessages().isEmpty());
        }
    }

    @Test
    public void shouldReadValidXRechnungUbl() {
        Invoice original = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = XrUblInvoiceWriter.builder()
                .build()
                .writeToString(original);

        Invoice parsed = SUT.readFromString(xml);

        assertNotNull(parsed);
    }
}