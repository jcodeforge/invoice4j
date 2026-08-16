import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import io.github.jcodeforge.invoice4jbase.exceptions.XsdValidationException;
import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jxr.XrCiiInvoiceReader;
import io.github.jcodeforge.invoice4jxr.XrCiiInvoiceWriter;
import io.github.jcodeforge.invoice4jxr.XrProfile;
import io.github.jcodeforge.invoice4jxr.exceptions.KositValidationException;
import org.junit.Test;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class XrCiiReaderTest {

    private final XrCiiInvoiceReader SUT = XrCiiInvoiceReader.builder().build();

    @Test
    public void shouldReadXRechnungCiiSample() throws Exception {
        try (InputStream input = getClass().getResourceAsStream(
                "/xrechnung/cii/01.01a-INVOICE_uncefact.xml")) {

            assertNotNull(input);

            Invoice invoice = SUT.read(input);

            assertNotNull(invoice);

            assertEquals("123456XX", invoice.getInvoiceNumber());
            assertNotNull(invoice.getIssueDate());
            assertNotNull(invoice.getCurrency());

            assertEquals("EUR", invoice.getCurrency().getCode());

            assertNotNull(invoice.getSeller());
            assertNotNull(invoice.getBuyer());

            assertEquals(2, invoice.getLines().size());
        }
    }

    @Test
    public void shouldRoundTripXRechnungCii() {
        Invoice original = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = XrCiiInvoiceWriter.builder()
                .profile(XrProfile.XRECHNUNG)
                .build()
                .writeToString(original);

        Invoice parsed = SUT.readFromString(xml);

        assertEquals(original.getInvoiceNumber(), parsed.getInvoiceNumber());
        assertEquals(original.getIssueDate(), parsed.getIssueDate());
        assertEquals(original.getCurrency().getCode(),
                parsed.getCurrency().getCode());
        assertEquals(original.getLines().size(), parsed.getLines().size());
    }

    @Test(expected = DeserializationException.class)
    public void shouldRejectXmlWithoutXRechnungProfile() {
        SUT.readFromString("<invalid/>");
    }

    @Test
    public void shouldRejectInvalidXRechnungCii() throws Exception {
        String xml = Files.readString(Path.of(getClass()
                        .getResource("/xrechnung/invalid/cii-br-de-25-test-bg-19-remove-bt-90-1031-remove.xml")
                        .toURI()), StandardCharsets.UTF_8);

        try {
            SUT.readFromString(xml);
            fail("Expected KositValidationException");

        } catch (KositValidationException exception) {
            assertFalse(exception.getValidationResult().isValid());
            assertFalse(exception.getValidationResult().getMessages().isEmpty());
        }
    }

    @Test
    public void shouldReadValidXRechnungCii() {
        Invoice original = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = XrCiiInvoiceWriter.builder()
                .build()
                .writeToString(original);

        Invoice parsed = SUT.readFromString(xml);

        assertNotNull(parsed);
    }
}
