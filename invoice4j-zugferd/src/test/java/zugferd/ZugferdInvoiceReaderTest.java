package zugferd;

import factory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdInvoiceReader;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdInvoiceWriter;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdProfile;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZugferdInvoiceReaderTest {

    private final ZugferdInvoiceReader SUT = ZugferdInvoiceReader.builder().build();

    @Test
    public void shouldRoundTripBasicInvoice() {
        Invoice original = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.BASIC)
                .build()
                .writeToString(original);

        Invoice parsed = SUT.readFromString(xml);

        assertEquals(original.getInvoiceNumber(), parsed.getInvoiceNumber());
        assertEquals(original.getIssueDate(), parsed.getIssueDate());
        assertEquals(original.getCurrency(), parsed.getCurrency());

        assertEquals(original.getSeller().getName(), parsed.getSeller().getName());
        assertEquals(original.getBuyer().getName(), parsed.getBuyer().getName());
        assertEquals(original.getLines().size(), parsed.getLines().size());
        assertEquals(original.getMonetarySummation().getTaxInclusiveAmount(),
                parsed.getMonetarySummation().getTaxInclusiveAmount());
    }

    @Test
    public void shouldReadMinimalBasicInvoice() {
        Invoice original = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());

        String xml = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.BASIC)
                .build()
                .writeToString(original);

        Invoice parsed = SUT.readFromString(xml);

        assertEquals(original.getInvoiceNumber(), parsed.getInvoiceNumber());
        assertEquals(original.getIssueDate(), parsed.getIssueDate());
        assertEquals(original.getCurrency(), parsed.getCurrency());
        assertEquals(original.getSeller().getName(), parsed.getSeller().getName());
        assertEquals(original.getBuyer().getName(), parsed.getBuyer().getName());
    }

    @Test
    public void shouldReadInvoicePeriod() {
        Invoice original = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.BASIC)
                .build()
                .writeToString(original);

        Invoice parsed = SUT.readFromString(xml);

        assertNotNull(parsed.getInvoicePeriod());
        assertEquals(original.getInvoicePeriod().getStartDate(), parsed.getInvoicePeriod().getStartDate());
        assertEquals(original.getInvoicePeriod().getEndDate(), parsed.getInvoicePeriod().getEndDate());
    }

    @Test
    public void shouldNotReadUnsupportedBasicProject() {
        Invoice original = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.BASIC)
                .build()
                .writeToString(original);

        Invoice parsed = SUT.readFromString(xml);

        assertNull(parsed.getProjectReference());
        assertNull(parsed.getProjectName());
    }

    @Test
    public void shouldReadBillingReferences() {
        Invoice original = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.BASIC)
                .build()
                .writeToString(original);

        Invoice parsed = SUT.readFromString(xml);

        assertEquals(original.getBillingReferences().size(), parsed.getBillingReferences().size());

        if (!original.getBillingReferences().isEmpty()) {
            assertEquals(original.getBillingReferences().getFirst().getId(), parsed.getBillingReferences().getFirst().getId());
        }
    }

    @Test
    public void shouldReadPaymentMeans() {
        Invoice original = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.BASIC)
                .build()
                .writeToString(original);

        Invoice parsed = SUT.readFromString(xml);

        assertNotNull(parsed.getPayment());

        assertEquals(original.getPayment().getMeansCode(), parsed.getPayment().getMeansCode());
        assertNotNull(parsed.getPayment().getBankAccount());
        assertEquals(original.getPayment()
                        .getBankAccount()
                        .getIban(),
                parsed.getPayment()
                        .getBankAccount()
                        .getIban()
        );
    }

    @Test
    public void shouldReadInvoiceLines() {
        Invoice original = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.BASIC)
                .build()
                .writeToString(original);

        Invoice parsed = SUT.readFromString(xml);

        assertEquals(original.getLines().size(), parsed.getLines().size());
        assertEquals(original.getLines().getFirst().getItemName(), parsed.getLines().getFirst().getItemName());
    }

    @Test(expected = DeserializationException.class)
    public void shouldRejectEmptyXml() {
        SUT.readFromString("");
    }

    @Test(expected = DeserializationException.class)
    public void shouldRejectInvalidXml() {
        SUT.readFromString("<invalid>");
    }
}
