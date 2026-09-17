package io.github.jcodeforge.invoice4jbase.ubl;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoiceLine;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Tax;
import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UblInvoiceReaderTest {

    private UblInvoiceReader SUT;

    @Before
    public void setUp() {
        SUT = UblInvoiceReader.builder().build();
    }

    @Test
    public void shouldReadMinimalInvoice() {
        Invoice original = TestInvoiceFactory.createMinimalInvoice();

        UblInvoiceWriter writer = UblInvoiceWriter.builder().build();
        String xml = writer.writeToString(original);

        System.out.println(xml);

        Invoice parsed = SUT.readFromString(xml);

        assertNotNull(parsed);

        assertEquals(original.getInvoiceNumber(),
                parsed.getInvoiceNumber());
        assertEquals(original.getDocumentTypeCode(),
                parsed.getDocumentTypeCode());
        assertEquals(original.getIssueDate(),
                parsed.getIssueDate());
        assertEquals(original.getCurrency(),
                parsed.getCurrency());

        assertNotNull(parsed.getSeller());
        assertEquals(original.getSeller().getName(),
                parsed.getSeller().getName());

        assertNotNull(parsed.getBuyer());
        assertEquals(original.getBuyer().getName(),
                parsed.getBuyer().getName());

        assertNotNull(parsed.getPayment());
        assertEquals(original.getPayment().getMeansCode(),
                parsed.getPayment().getMeansCode());

        assertNotNull(parsed.getPaymentTerms());
        assertEquals(original.getPaymentTerms().getDescription(),
                parsed.getPaymentTerms().getDescription());

        assertNotNull(parsed.getDelivery());
        assertEquals(original.getDelivery().getActualDeliveryDate(),
                parsed.getDelivery().getActualDeliveryDate());

        assertNotNull(parsed.getInvoicePeriod());
        assertEquals(original.getInvoicePeriod().getStartDate(),
                parsed.getInvoicePeriod().getStartDate());
        assertEquals(original.getInvoicePeriod().getEndDate(),
                parsed.getInvoicePeriod().getEndDate());

        assertEquals(1, parsed.getLines().size());
        assertEquals(1, parsed.getTaxes().size());

        assertNotNull(parsed.getMonetarySummation());
        assertEquals(
                original.getMonetarySummation()
                        .getPayableAmount()
                        .getAmount(),
                parsed.getMonetarySummation()
                        .getPayableAmount()
                        .getAmount());

        assertEquals(
                original.getSeller().getAddress().getCity(),
                parsed.getSeller().getAddress().getCity());

        assertEquals(
                original.getSeller().getAddress().getCountryCode(),
                parsed.getSeller().getAddress().getCountryCode());

        assertEquals(
                original.getBuyer().getAddress().getCity(),
                parsed.getBuyer().getAddress().getCity());

        assertEquals(
                original.getPayment()
                        .getBankAccount()
                        .getIban(),
                parsed.getPayment()
                        .getBankAccount()
                        .getIban());

        assertEquals(
                original.getPayment()
                        .getBankAccount()
                        .getBic(),
                parsed.getPayment()
                        .getBankAccount()
                        .getBic());

        InvoiceLine originalLine = original.getLines().getFirst();
        InvoiceLine parsedLine = parsed.getLines().getFirst();

        assertEquals(originalLine.getId(), parsedLine.getId());
        assertEquals(originalLine.getItemName(), parsedLine.getItemName());
        assertEquals(originalLine.getQuantity(), parsedLine.getQuantity());
        assertEquals(originalLine.getUnitCode(), parsedLine.getUnitCode());
        assertEquals(
                originalLine.getNetPrice().getAmount(),
                parsedLine.getNetPrice().getAmount());

        Tax originalTax = original.getTaxes().getFirst();
        Tax parsedTax = parsed.getTaxes().getFirst();

        assertEquals(
                originalTax.getCategoryCode(),
                parsedTax.getCategoryCode());

        assertEquals(
                originalTax.getRate(),
                parsedTax.getRate());

        assertEquals(
                originalTax.getTaxAmount().getAmount(),
                parsedTax.getTaxAmount().getAmount());
    }

    @Test
    public void shouldReadCompleteInvoice() {
        Invoice original = TestInvoiceFactory.createCompleteInvoice();

        UblInvoiceWriter writer = UblInvoiceWriter.builder().build();

        String xml = writer.writeToString(original);

        Invoice parsed = SUT.readFromString(xml);

        assertNotNull(parsed);

        // Header
        assertEquals(original.getInvoiceNumber(), parsed.getInvoiceNumber());
        assertEquals(original.getDocumentTypeCode(), parsed.getDocumentTypeCode());
        assertEquals(original.getIssueDate(), parsed.getIssueDate());
        assertEquals(original.getCurrency(), parsed.getCurrency());
        // References
        assertEquals(original.getBuyerReference(), parsed.getBuyerReference());
        assertEquals(original.getContractReference(), parsed.getContractReference());
        assertEquals(original.getPurchaseOrderReference(), parsed.getPurchaseOrderReference());
        // Delivery
        assertNotNull(parsed.getDelivery());
        assertEquals(original.getDelivery().getActualDeliveryDate(), parsed.getDelivery().getActualDeliveryDate());
        // Payment
        assertNotNull(parsed.getPayment());
        assertEquals(original.getPayment().getMeansCode(), parsed.getPayment().getMeansCode());
        assertEquals(
                original.getPayment()
                        .getBankAccount()
                        .getIban(),
                parsed.getPayment()
                        .getBankAccount()
                        .getIban());

        assertEquals(
                original.getPayment()
                        .getBankAccount()
                        .getBic(),
                parsed.getPayment()
                        .getBankAccount()
                        .getBic());

        // Payment terms
        assertNotNull(parsed.getPaymentTerms());
        assertEquals(original.getPaymentTerms().getDescription(), parsed.getPaymentTerms().getDescription());
        // Parties
        assertNotNull(parsed.getSeller());
        assertNotNull(parsed.getBuyer());
        assertEquals(original.getSeller().getName(), parsed.getSeller().getName());
        assertEquals(original.getBuyer().getName(), parsed.getBuyer().getName());
        // Notes
        assertEquals(original.getNotes().size(), parsed.getNotes().size());
        assertEquals(original.getNotes().getFirst().getText(), parsed.getNotes().getFirst().getText());
        // Lines
        assertEquals(original.getLines().size(), parsed.getLines().size());
        // Taxes
        assertEquals(original.getTaxes().size(), parsed.getTaxes().size());

        // Allowance / Charge
        assertEquals(original.getAllowanceCharges().size(), parsed.getAllowanceCharges().size());

        // Billing references
        assertEquals(
                original.getBillingReferences().size(),
                parsed.getBillingReferences().size());

        if (!original.getBillingReferences().isEmpty()) {
            assertEquals(
                    original.getBillingReferences().getFirst().getId(),
                    parsed.getBillingReferences().getFirst().getId());
        }

        // Additional documents
        assertEquals(
                original.getAdditionalDocuments().size(),
                parsed.getAdditionalDocuments().size());

        if (!original.getAdditionalDocuments().isEmpty()) {
            assertEquals(
                    original.getAdditionalDocuments().getFirst().getId(),
                    parsed.getAdditionalDocuments().getFirst().getId());
        }

        // Monetary summation
        assertNotNull(parsed.getMonetarySummation());

        assertEquals(
                original.getMonetarySummation()
                        .getPayableAmount()
                        .getAmount(),
                parsed.getMonetarySummation()
                        .getPayableAmount()
                        .getAmount());
    }

    @Test
    public void shouldReadMultipleTaxes() {
        Invoice original = TestInvoiceFactory.createInvoiceWithMultipleTaxes();

        UblInvoiceWriter writer = UblInvoiceWriter.builder().build();

        String xml = writer.writeToString(original);

        Invoice parsed = SUT.readFromString(xml);

        assertNotNull(parsed);

        assertEquals(2, original.getTaxes().size());
        assertEquals(2, parsed.getTaxes().size());

        Tax originalFirstTax = original.getTaxes().getFirst();
        Tax parsedFirstTax = parsed.getTaxes().getFirst();

        assertEquals(
                originalFirstTax.getCategoryCode(),
                parsedFirstTax.getCategoryCode());

        assertEquals(
                originalFirstTax.getRate(),
                parsedFirstTax.getRate());

        assertEquals(
                originalFirstTax.getTaxableAmount().getAmount(),
                parsedFirstTax.getTaxableAmount().getAmount());

        assertEquals(
                originalFirstTax.getTaxAmount().getAmount(),
                parsedFirstTax.getTaxAmount().getAmount());

        Tax originalSecondTax =
                original.getTaxes().get(1);

        Tax parsedSecondTax =
                parsed.getTaxes().get(1);

        assertEquals(
                originalSecondTax.getCategoryCode(),
                parsedSecondTax.getCategoryCode());

        assertEquals(
                originalSecondTax.getRate(),
                parsedSecondTax.getRate());

        assertEquals(
                originalSecondTax.getTaxableAmount().getAmount(),
                parsedSecondTax.getTaxableAmount().getAmount());

        assertEquals(
                originalSecondTax.getTaxAmount().getAmount(),
                parsedSecondTax.getTaxAmount().getAmount());
    }

    @Test
    public void shouldReadInvoiceLinePriceDetails() {
        Invoice original = TestInvoiceFactory.createCompleteInvoice();

        UblInvoiceWriter writer =
                UblInvoiceWriter.builder().build();

        String xml = writer.writeToString(original);

        Invoice parsed = SUT.readFromString(xml);

        InvoiceLine originalLine =
                original.getLines().getFirst();

        InvoiceLine parsedLine =
                parsed.getLines().getFirst();

        assertEquals(
                originalLine.getNote(),
                parsedLine.getNote());

        assertEquals(
                originalLine.getPriceDiscount(),
                parsedLine.getPriceDiscount());

        assertEquals(
                originalLine.getGrossPrice().getAmount(),
                parsedLine.getGrossPrice().getAmount());

        assertEquals(
                originalLine.getBaseQuantity(),
                parsedLine.getBaseQuantity());
    }

    @Test
    public void shouldRoundTripInvoice() {
        Invoice original = TestInvoiceFactory.createCompleteInvoice();

        UblInvoiceWriter writer =
                UblInvoiceWriter.builder().build();

        String xml1 = writer.writeToString(original);

        Invoice parsed = SUT.readFromString(xml1);

        String xml2 = writer.writeToString(parsed);

        assertNotNull(xml2);
    }

    @Test(expected = DeserializationException.class)
    public void shouldThrowExceptionForNonUblXml() {
        SUT.readFromString("""
                <root>
                    <test>Hello</test>
                </root>
                """);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullString() {
        SUT.readFromString(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullFile() {
        SUT.readFromFile(null);
    }

    @Test(expected = DeserializationException.class)
    public void shouldRejectEmptyXml() {
        SUT.readFromString("");
    }
}