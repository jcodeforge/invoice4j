package calculation;

import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoiceLine;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetarySummation;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Tax;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InvoiceCalculatorTest {

    private InvoiceCalculator SUT;

    private Invoice invoice;

    @Before
    public void setUp() {
        SUT = new InvoiceCalculator();

        invoice = CalculationUtils.createCalculatedInvoice()
                .lines(List.of(
                        CalculationUtils.createStandardLine( "100.00")))
                .build();
    }

    @Test
    public void shouldCalculateInvoice() {
        Invoice result = SUT.calculate(invoice);

        assertNotNull(result);

        assertNotNull(result.getLines());
        assertNotNull(result.getTaxes());
        assertNotNull(result.getMonetarySummation());

        assertEquals(1, result.getLines().size());
        assertEquals(1, result.getTaxes().size());
    }

    @Test
    public void shouldCalculateInvoiceLine() {
        Invoice result = SUT.calculate(invoice);
        InvoiceLine line = result.getLines().getFirst();

        assertEquals(new BigDecimal("100.00"), line.getLineExtensionAmount().getAmount());
    }

    @Test
    public void shouldCalculateVatBreakdown() {
        Invoice result = SUT.calculate(invoice);

        Tax tax = result.getTaxes().getFirst();

        assertEquals(new BigDecimal("100.00"), tax.getTaxableAmount().getAmount());
        assertEquals(new BigDecimal("19.00"), tax.getTaxAmount().getAmount());
    }

    @Test
    public void shouldCalculateMonetarySummation() {
        Invoice result = SUT.calculate(invoice);
        MonetarySummation totals = result.getMonetarySummation();

        assertEquals(new BigDecimal("100.00"), totals.getLineExtensionAmount().getAmount());

        assertEquals(new BigDecimal("119.00"), totals.getPayableAmount().getAmount());
    }

    @Test
    public void shouldCalculateMultipleInvoiceLines() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createStandardLine( "100.00"),
                        CalculationUtils.createStandardLine( "50.00")))
                .build();

        Invoice result = SUT.calculate(invoice);

        assertEquals(2, result.getLines().size());
        assertEquals(new BigDecimal("150.00"), result.getMonetarySummation().getLineExtensionAmount().getAmount());
        assertEquals(new BigDecimal("28.50"), result.getMonetarySummation().getTaxAmount().getAmount());
        assertEquals(new BigDecimal("178.50"), result.getMonetarySummation().getPayableAmount().getAmount());
    }

    @Test
    public void shouldCalculateMultipleVatRates() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createStandardLine( "100.00"),
                        CalculationUtils.createReducedRateLine( "50.00")))
                .build();

        Invoice result = SUT.calculate(invoice);

        assertEquals(2, result.getTaxes().size());
        assertEquals(new BigDecimal("22.50"), result.getMonetarySummation().getTaxAmount().getAmount());
        assertEquals(new BigDecimal("172.50"), result.getMonetarySummation().getPayableAmount().getAmount());
    }

    @Test
    public void shouldCalculateInvoiceWithDocumentAllowance() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createStandardLine( "100.00")))
                .allowanceCharges(List.of(
                        CalculationUtils.createAllowance("10.00")))
                .build();

        Invoice result = SUT.calculate(invoice);

        assertEquals(new BigDecimal("90.00"), result.getMonetarySummation().getTaxExclusiveAmount().getAmount());
        assertEquals(new BigDecimal("17.10"), result.getMonetarySummation().getTaxAmount().getAmount());
        assertEquals(new BigDecimal("107.10"), result.getMonetarySummation().getPayableAmount().getAmount());
    }

    @Test
    public void shouldCalculateInvoiceWithDocumentCharge() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createStandardLine( "100.00")))
                .allowanceCharges(List.of(
                        CalculationUtils.createCharge("20.00")))
                .build();

        Invoice result = SUT.calculate(invoice);

        assertEquals(new BigDecimal("120.00"), result.getMonetarySummation().getTaxExclusiveAmount().getAmount());
        assertEquals(new BigDecimal("22.80"), result.getMonetarySummation().getTaxAmount().getAmount());
        assertEquals(new BigDecimal("142.80"), result.getMonetarySummation().getPayableAmount().getAmount());
    }

    @Test
    public void shouldCalculateInvoiceWithPrepaidAmount() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .monetarySummation(
                        CalculationUtils.createMonetarySummation("20.00", "0.00"))
                .build();

        Invoice result = SUT.calculate(invoice);

        assertEquals(new BigDecimal("99.00"), result.getMonetarySummation().getPayableAmount().getAmount());
    }

    @Test
    public void shouldCalculateInvoiceWithRoundingAmount() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .monetarySummation(
                        CalculationUtils.createMonetarySummation("0.00", "0.05"))
                .build();

        Invoice result = SUT.calculate(invoice);

        assertEquals(new BigDecimal("119.05"), result.getMonetarySummation().getPayableAmount().getAmount());
    }

    @Test
    public void shouldCalculateInvoiceWithPrepaidAmountAndRoundingAmount() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .monetarySummation(
                        CalculationUtils.createMonetarySummation("20.00", "0.05"))
                .build();

        Invoice result = SUT.calculate(invoice);

        assertEquals(new BigDecimal("99.05"), result.getMonetarySummation().getPayableAmount().getAmount());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenInvoiceIsNull() {
        SUT.calculate(null);
    }

    @Test
    public void shouldReturnNotSameInstance() {
        Invoice result = SUT.calculate(invoice);

        assertNotSame(invoice, result);
    }

    @Test
    public void shouldCalculateCompleteInvoice() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createStandardLine( "100.00"),
                        CalculationUtils.createReducedRateLine( "50.00")))
                .allowanceCharges(List.of(
                        CalculationUtils.createAllowance("10.00"),
                        CalculationUtils.createCharge("20.00")))
                .monetarySummation(
                        CalculationUtils.createMonetarySummation("25.00", "0.05"))
                .build();

        Invoice result = SUT.calculate(invoice);

        assertEquals(2, result.getLines().size());
        assertEquals(2, result.getTaxes().size());

        assertEquals(new BigDecimal("160.00"), result.getMonetarySummation().getTaxExclusiveAmount().getAmount());
        assertEquals(new BigDecimal("24.40"), result.getMonetarySummation().getTaxAmount().getAmount());
        assertEquals(new BigDecimal("184.40"), result.getMonetarySummation().getTaxInclusiveAmount().getAmount());
        assertEquals(new BigDecimal("159.45"), result.getMonetarySummation().getPayableAmount().getAmount());
    }
}
