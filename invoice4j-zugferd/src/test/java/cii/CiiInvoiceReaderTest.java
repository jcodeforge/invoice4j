package cii;

import factory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoiceLine;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Tax;
import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiInvoiceReader;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiInvoiceWriter;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiProfile;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CiiInvoiceReaderTest {

    private CiiInvoiceReader SUT;

    @Before
    public void setUp() {
        SUT = CiiInvoiceReader.builder().build();
    }

    @Test
    public void shouldReadMinimalInvoice() {
        Invoice original = TestInvoiceFactory.createMinimalInvoice();

        CiiInvoiceWriter writer = CiiInvoiceWriter.builder().build();
        String xml = writer.writeToString(original);

        Invoice parsed = SUT.readFromString(xml);

        assertNotNull(parsed);

        assertEquals(original.getInvoiceNumber(), parsed.getInvoiceNumber());
        assertEquals(original.getDocumentTypeCode(), parsed.getDocumentTypeCode());
        assertEquals(original.getIssueDate(), parsed.getIssueDate());
        assertEquals(original.getCurrency(), parsed.getCurrency());

        assertNotNull(parsed.getSeller());
        assertEquals(original.getSeller().getName(), parsed.getSeller().getName());

        assertNotNull(parsed.getBuyer());
        assertEquals(original.getBuyer().getName(), parsed.getBuyer().getName());

        assertNotNull(parsed.getPayment());
        assertEquals(original.getPayment().getMeansCode(), parsed.getPayment().getMeansCode());

        assertNotNull(parsed.getPaymentTerms());
        assertEquals(original.getPaymentTerms().getDescription(), parsed.getPaymentTerms().getDescription());

        assertNotNull(parsed.getDelivery());
        assertEquals(original.getDelivery().getActualDeliveryDate(), parsed.getDelivery().getActualDeliveryDate());

        assertNotNull(parsed.getInvoicePeriod());
        assertEquals(original.getInvoicePeriod().getStartDate(), parsed.getInvoicePeriod().getStartDate());
        assertEquals(original.getInvoicePeriod().getEndDate(), parsed.getInvoicePeriod().getEndDate());

        assertEquals(1, parsed.getLines().size());
        assertEquals(1, parsed.getTaxes().size());

        assertNotNull(parsed.getMonetarySummation());
        assertEquals(original.getMonetarySummation().getPayableAmount().getAmount(),
                parsed.getMonetarySummation().getPayableAmount().getAmount());

        assertEquals(original.getSeller().getAddress().getCity(),
                parsed.getSeller().getAddress().getCity());

        assertEquals(original.getSeller().getAddress().getCountryCode(),
                parsed.getSeller().getAddress().getCountryCode());

        assertEquals(original.getBuyer().getAddress().getCity(),
                parsed.getBuyer().getAddress().getCity());

        assertEquals(original.getPayment().getBankAccount().getIban(),
                parsed.getPayment().getBankAccount().getIban());

        assertEquals(original.getPayment().getBankAccount().getBic(),
                parsed.getPayment().getBankAccount().getBic());

        InvoiceLine originalLine = original.getLines().getFirst();
        InvoiceLine parsedLine = parsed.getLines().getFirst();

        assertEquals(originalLine.getId(), parsedLine.getId());
        assertEquals(originalLine.getItemName(), parsedLine.getItemName());
        assertEquals(originalLine.getQuantity(), parsedLine.getQuantity());
        assertEquals(originalLine.getUnitCode(), parsedLine.getUnitCode());
        assertEquals(originalLine.getNetPrice().getAmount(), parsedLine.getNetPrice().getAmount());

        Tax originalTax = original.getTaxes().getFirst();
        Tax parsedTax = parsed.getTaxes().getFirst();

        assertEquals(originalTax.getCategoryCode(), parsedTax.getCategoryCode());
        assertEquals(originalTax.getRate(), parsedTax.getRate());
        assertEquals(originalTax.getTaxAmount().getAmount(),
                parsedTax.getTaxAmount().getAmount());
    }

    @Test
    public void shouldReadCompleteInvoice() {
        Invoice original = TestInvoiceFactory.createCompleteInvoice();

        CiiInvoiceWriter writer = CiiInvoiceWriter.builder().build();
        String xml = writer.writeToString(original);

        Invoice parsed = SUT.readFromString(xml);

        assertNotNull(parsed);

        // Context
        assertEquals(original.getBusinessProcessIdentifier(), parsed.getBusinessProcessIdentifier());
        assertEquals(CiiProfile.EN16931.getGuidelineId(), parsed.getCustomizationIdentifier());

        assertEquals("PROJECT-42", parsed.getProjectReference());
        assertEquals("My Project", parsed.getProjectName());

        // Header
        assertEquals(original.getInvoiceNumber(), parsed.getInvoiceNumber());
        assertEquals(original.getDocumentTypeCode(), parsed.getDocumentTypeCode());
        assertEquals(original.getIssueDate(), parsed.getIssueDate());

        // References
        assertEquals(original.getBuyerReference(), parsed.getBuyerReference());
        assertEquals(original.getContractReference(), parsed.getContractReference());
        assertEquals(original.getProjectReference(), parsed.getProjectReference());
        assertEquals(original.getPurchaseOrderReference(), parsed.getPurchaseOrderReference());
        assertEquals(original.getSalesOrderReference(), parsed.getSalesOrderReference());

        // Parties
        assertNotNull(parsed.getSeller());
        assertNotNull(parsed.getBuyer());
        assertEquals(original.getSeller().getName(), parsed.getSeller().getName());
        assertEquals(original.getBuyer().getName(), parsed.getBuyer().getName());

        // Notes
        assertEquals(original.getNotes().size(), parsed.getNotes().size());
        assertEquals(original.getNotes().getFirst().getText(), parsed.getNotes().getFirst().getText());
        assertEquals(original.getNotes().getLast().getSubjectCode(), parsed.getNotes().getLast().getSubjectCode());

        // Lines
        assertEquals(2, parsed.getLines().size());

        // Taxes
        assertEquals(original.getTaxes().size(), parsed.getTaxes().size());

        // Allowance / Charge
        assertEquals(original.getAllowanceCharges().size(), parsed.getAllowanceCharges().size());

        // Billing references
        assertEquals(original.getBillingReferences().size(), parsed.getBillingReferences().size());
        assertEquals(original.getBillingReferences().getFirst().getId(), parsed.getBillingReferences().getFirst().getId());

        // Additional documents
        assertEquals(original.getAdditionalDocuments().size(), parsed.getAdditionalDocuments().size());
        assertEquals(original.getAdditionalDocuments().getFirst().getId(), parsed.getAdditionalDocuments().getFirst().getId());

        // Monetary summation
        assertNotNull(parsed.getMonetarySummation());
        assertEquals(original.getMonetarySummation().getPayableAmount().getAmount(),
                parsed.getMonetarySummation().getPayableAmount().getAmount());
    }

    @Test(expected = DeserializationException.class)
    public void shouldThrowExceptionForNonCiiXml() {
        SUT.readFromString("""
        <root>
            <test>Hello</test>
        </root>
        """);
    }
}
