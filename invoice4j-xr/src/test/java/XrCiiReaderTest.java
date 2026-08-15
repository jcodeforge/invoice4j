import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jxr.XrCiiInvoiceReader;
import io.github.jcodeforge.invoice4jxr.XrCiiInvoiceWriter;
import io.github.jcodeforge.invoice4jxr.XrProfile;
import org.junit.Test;
import java.io.InputStream;

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
}
