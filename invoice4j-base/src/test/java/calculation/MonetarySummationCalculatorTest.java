package calculation;

import io.github.jcodeforge.invoice4jbase.calculation.MonetarySummationCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetarySummation;
import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MonetarySummationCalculatorTest {

    private MonetarySummationCalculator SUT;

    private Invoice invoice;

    @Before
    public void setUp() {
        SUT = new MonetarySummationCalculator();
        invoice = CalculationUtils.createCalculatedInvoice().build();
    }

    @Test
    public void shouldCalculateLineExtensionAmount() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createStandardLine( "100.00"),
                        CalculationUtils.createStandardLine("50.00")))
                .build();

        MonetarySummation result = SUT.calculate(invoice);

        assertEquals(new BigDecimal("150.00"), result.getLineExtensionAmount().getAmount());
    }

    @Test
    public void shouldCalculateAllowanceTotalAmount() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .allowanceCharges(List.of(
                        CalculationUtils.createAllowance("10.00"),
                        CalculationUtils.createAllowance("5.00")))
                .build();

        MonetarySummation result = SUT.calculate(invoice);

        assertEquals(new BigDecimal("15.00"), result.getAllowanceTotalAmount().getAmount());
    }

    @Test
    public void shouldCalculateChargeTotalAmount() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .allowanceCharges(List.of(
                        CalculationUtils.createCharge("10.00"),
                        CalculationUtils.createCharge("5.00")))
                .build();

        MonetarySummation result = SUT.calculate(invoice);

        assertEquals(new BigDecimal("15.00"), result.getChargeTotalAmount().getAmount());
    }

    @Test
    public void shouldCalculateZeroAllowanceAndChargeTotals() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .allowanceCharges(List.of())
                .build();

        MonetarySummation result = SUT.calculate(invoice);

        assertEquals(new BigDecimal("0.00"), result.getAllowanceTotalAmount().getAmount());
        assertEquals(new BigDecimal("0.00"), result.getChargeTotalAmount().getAmount());
    }

    @Test
    public void shouldCalculateTaxExclusiveAmount() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createStandardLine( "100.00")))
                .allowanceCharges(List.of(
                        CalculationUtils.createAllowance("10.00"),
                        CalculationUtils.createCharge("20.00")))
                .taxes(List.of(
                        CalculationUtils.createStandardTax("110.00", "20.90")))
                .build();

        MonetarySummation result = SUT.calculate(invoice);

        assertEquals(new BigDecimal("110.00"), result.getTaxExclusiveAmount().getAmount());
    }

    @Test
    public void shouldCalculateTaxAmount() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .taxes(List.of(
                        CalculationUtils.createStandardTax("100.00", "19.00"),
                        CalculationUtils.createReducedRateTax("50.00", "3.50")))
                .build();

        MonetarySummation result = SUT.calculate(invoice);

        assertEquals(new BigDecimal("22.50"), result.getTaxAmount().getAmount());
    }

    @Test
    public void shouldCalculateTaxInclusiveAmount() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createStandardLine( "100.00")))
                .taxes(List.of(
                        CalculationUtils.createStandardTax("100.00", "19.00")))
                .build();

        MonetarySummation result = SUT.calculate(invoice);

        assertEquals(new BigDecimal("119.00"), result.getTaxInclusiveAmount().getAmount());
    }

    @Test
    public void shouldCalculateMultipleTaxes() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .taxes(List.of(
                        CalculationUtils.createStandardTax("100.00", "19.00"),
                        CalculationUtils.createReducedRateTax("50.00", "3.50"),
                        CalculationUtils.createZeroRatedTax("25.00")))
                .build();

        MonetarySummation result = SUT.calculate(invoice);

        assertEquals(new BigDecimal("22.50"), result.getTaxAmount().getAmount());
    }

    @Test
    public void shouldCalculatePayableAmount() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .monetarySummation(CalculationUtils.createMonetarySummation())
                .build();

        MonetarySummation result = SUT.calculate(invoice);

        assertEquals(new BigDecimal("119.00"), result.getPayableAmount().getAmount());
    }

    @Test
    public void shouldCalculatePayableAmountWithPrepaidAmount() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .monetarySummation(CalculationUtils.createMonetarySummation("20.00", "0.00"))
                .build();

        MonetarySummation result = SUT.calculate(invoice);

        assertEquals(new BigDecimal("99.00"), result.getPayableAmount().getAmount());
    }

    @Test
    public void shouldCalculatePayableAmountWithRoundingAmount() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .monetarySummation(CalculationUtils.createMonetarySummation("0.00", "0.05"))
                .build();

        MonetarySummation result = SUT.calculate(invoice);

        assertEquals(new BigDecimal("119.05"), result.getPayableAmount().getAmount());
    }

    @Test
    public void shouldCalculatePayableAmountWithPrepaidAndRounding() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .monetarySummation(CalculationUtils.createMonetarySummation("20.00", "0.05"))
                .build();

        MonetarySummation result = SUT.calculate(invoice);

        assertEquals(new BigDecimal("99.05"), result.getPayableAmount().getAmount());
    }

    @Test
    public void shouldCalculateCompleteMonetarySummation() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createStandardLine("100.00"),
                        CalculationUtils.createStandardLine("50.00")))
                .allowanceCharges(List.of(
                        CalculationUtils.createAllowance("10.00"),
                        CalculationUtils.createCharge("20.00")))
                .taxes(List.of(
                        CalculationUtils.createStandardTax("160.00", "30.40")))
                .monetarySummation(CalculationUtils.createMonetarySummation("25.00", "0.05"))
                .build();

        MonetarySummation result = SUT.calculate(invoice);

        assertEquals(new BigDecimal("150.00"), result.getLineExtensionAmount().getAmount());
        assertEquals(new BigDecimal("10.00"), result.getAllowanceTotalAmount().getAmount());
        assertEquals(new BigDecimal("20.00"), result.getChargeTotalAmount().getAmount());
        assertEquals(new BigDecimal("160.00"), result.getTaxExclusiveAmount().getAmount());
        assertEquals(new BigDecimal("30.40"), result.getTaxAmount().getAmount());
        assertEquals(new BigDecimal("190.40"), result.getTaxInclusiveAmount().getAmount());
        assertEquals(new BigDecimal("165.45"), result.getPayableAmount().getAmount());
    }

    @Test
    public void shouldCalculateMultipleInvoiceLines() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createStandardLine( "10.00"),
                        CalculationUtils.createStandardLine( "20.00"),
                        CalculationUtils.createStandardLine( "30.00"),
                        CalculationUtils.createStandardLine( "40.00")))
                .build();

        MonetarySummation result = SUT.calculate(invoice);

        assertEquals(new BigDecimal("100.00"), result.getLineExtensionAmount().getAmount());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenInvoiceIsNull() {
        SUT.calculate(null);
    }

    @Test
    public void shouldRoundCalculatedTotalsOnlyAtTheEnd() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createStandardLine( "33.335"),
                        CalculationUtils.createStandardLine( "33.335"),
                        CalculationUtils.createStandardLine( "33.335")))
                .taxes(List.of(
                        CalculationUtils.createStandardTax("100.01", "19.00")))
                .build();

        MonetarySummation result = SUT.calculate(invoice);

        assertEquals(new BigDecimal("100.01"), result.getLineExtensionAmount().getAmount());
    }
}
