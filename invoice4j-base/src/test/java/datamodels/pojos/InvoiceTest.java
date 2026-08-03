package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.DocumentTypeCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.*;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InvoiceTest {

    private Invoice.Builder SUT;

    @Before
    public void setUp() {
        SUT = CalculationUtils.createInvoiceBuilder();
    }

    @Test
    public void shouldCreateInvoice() {
        Invoice invoice = SUT.build();

        assertNotNull(invoice);
        assertEquals("INV-2026-0001", invoice.getInvoiceNumber());
        assertEquals(DocumentTypeCode.COMMERCIAL_INVOICE, invoice.getDocumentTypeCode());
        assertEquals(LocalDate.of(2026, 1, 1), invoice.getIssueDate());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingInvoiceNumber() {
        SUT.invoiceNumber(null).build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankInvoiceNumber() {
        SUT.invoiceNumber("   ").build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingIssueDate() {
        SUT.issueDate(null).build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingInvoiceTypeCode() {
        SUT.documentTypeCode(null).build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingCurrency() {
        SUT.currency(null).build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingSeller() {
        SUT.seller(null).build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingBuyer() {
        SUT.buyer(null).build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectDuplicateInvoiceLineIdentifiers() {
        InvoiceLine line1 = CalculationUtils.createStandardLine( "1", "100.00");
        InvoiceLine line2 = CalculationUtils.createStandardLine( "1", "50.00");

        SUT.lines(List.of(line1, line2)).build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNullInvoiceLine() {
        SUT.lines(Arrays.asList(
                        CalculationUtils.createStandardLine( "100.00"),
                        null))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNullDocumentAllowanceCharge() {
        SUT.allowanceCharges(Arrays.asList(
                        CalculationUtils.createAllowance("10.00"),
                        null))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNullTax() {
        SUT.taxes(Arrays.asList(
                        CalculationUtils.createStandardTax("100.00", "19.00"),
                        null))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingDelivery() {
        SUT.delivery(null).build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingPaymentMeans() {
        SUT.paymentMeans(null).build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingPaymentTerms() {
        SUT.paymentTerms(null).build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectPaymentDueDateBeforeIssueDate() {
        SUT.issueDate(LocalDate.of(2026, 1, 10))
                .dueDate(LocalDate.of(2026, 1, 9))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectTaxPointDateAndTaxPointDateCodeTogether() {
        SUT.taxPointDate(LocalDate.of(2026, 1, 5))
                .taxPointDateCode("35")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectEmptyInvoiceLines() {
        SUT.lines(Collections.emptyList()).build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingMonetarySummation() {
        SUT.monetarySummation(null).build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingInvoicePeriod() {
        SUT.invoicePeriod(null).build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectPaymentTermsDueDateBeforeIssueDate() {
        SUT.issueDate(LocalDate.of(2026, 1, 10))
                .paymentTerms(
                        PaymentTerms.builder()
                                .description("30 days")
                                .dueDate(LocalDate.of(2026, 1, 5))
                                .build())
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectInvoicePeriodStartAfterEnd() {
        SUT.invoicePeriod(
                        InvoicePeriod.builder()
                                .startDate(LocalDate.of(2026, 2, 1))
                                .endDate(LocalDate.of(2026, 1, 1))
                                .build())
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectVatBreakdownWithoutTaxableAmount() {
        Tax tax = Tax.builder()
                .categoryCode(TaxCategoryCode.STANDARD)
                .rate(new BigDecimal("19"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .build();

        SUT.taxes(List.of(tax)).build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectVatBreakdownWithoutTaxAmount() {
        Tax tax = Tax.builder()
                .categoryCode(TaxCategoryCode.STANDARD)
                .rate(new BigDecimal("19"))
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .build();

        SUT.taxes(List.of(tax)).build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectVatBreakdownWithoutVatRate() {
        Tax tax = Tax.builder()
                .categoryCode(TaxCategoryCode.EXEMPT)
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("0.00"))
                .build();

        SUT.taxes(List.of(tax)).build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectVatAmountMismatch() {
        Tax tax = Tax.builder()
                .categoryCode(TaxCategoryCode.STANDARD)
                .rate(new BigDecimal("19"))
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("18.00"))
                .build();

        SUT.taxes(List.of(tax)).build();
    }

    @Test
    public void shouldAcceptVatDifferenceOfOneCent() {
        Tax tax = Tax.builder()
                .categoryCode(TaxCategoryCode.STANDARD)
                .rate(new BigDecimal("19"))
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.01"))
                .build();

        Invoice invoice = SUT
                .taxes(List.of(tax))
                .build();

        assertNotNull(invoice);
    }
}