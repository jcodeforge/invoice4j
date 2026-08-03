package calculation;

import io.github.jcodeforge.invoice4jbase.calculation.TaxCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.UnitCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoiceLine;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Tax;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TaxCalculatorTest {

    private TaxCalculator SUT;

    private Invoice invoice;

    @Before
    public void setUp() {
        SUT = new TaxCalculator();
        invoice = CalculationUtils.createSimpleInvoice().build();
    }

    @Test
    public void shouldCalculateSingleStandardVat() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        InvoiceLine.builder()
                                .id(CalculationUtils.nextLineId())
                                .itemName("Notebook")
                                .quantity(BigDecimal.ONE)
                                .unitCode(UnitCode.ONE)
                                .unitPrice(CalculationUtils.createMoney("100.00"))
                                .taxCategory(TaxCategoryCode.STANDARD)
                                .taxRate(new BigDecimal("19"))
                                .lineExtensionAmount(CalculationUtils.createMoney("100.00"))
                                .build()))
                .build();

        List<Tax> taxes = SUT.calculate(invoice);

        assertEquals(1, taxes.size());

        Tax tax = taxes.getFirst();

        assertEquals(TaxCategoryCode.STANDARD, tax.getCategoryCode());
        assertEquals(new BigDecimal("19"), tax.getRate());
        assertEquals(new BigDecimal("100.00"), tax.getTaxableAmount().getAmount());
        assertEquals(new BigDecimal("19.00"), tax.getTaxAmount().getAmount());
    }

    @Test
    public void shouldCalculateMultipleInvoiceLinesWithSameVatRate() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createInvoiceLine("100.00"),
                        CalculationUtils.createInvoiceLine("50.00")))
                .build();

        List<Tax> taxes = SUT.calculate(invoice);

        assertEquals(1, taxes.size());

        Tax tax = taxes.getFirst();

        assertEquals(new BigDecimal("150.00"), tax.getTaxableAmount().getAmount());
        assertEquals(new BigDecimal("28.50"), tax.getTaxAmount().getAmount());
    }

    @Test
    public void shouldCalculateMultipleVatRates() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createInvoiceLine("100.00"),
                        CalculationUtils.createReducedRateLine("50.00")))
                .build();

        List<Tax> taxes = SUT.calculate(invoice);

        assertEquals(2, taxes.size());
    }

    @Test
    public void shouldCalculateZeroRatedVat() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createZeroRatedLine("100.00")))
                .build();

        List<Tax> taxes = SUT.calculate(invoice);

        assertEquals(1, taxes.size());
        assertEquals(BigDecimal.ZERO.setScale(2), taxes.getFirst().getTaxAmount().getAmount());
    }

    @Test
    public void shouldCalculateExemptVat() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createExemptLine("100.00")))
                .build();

        List<Tax> taxes = SUT.calculate(invoice);

        assertEquals(1, taxes.size());

        Tax tax = taxes.getFirst();

        assertEquals(TaxCategoryCode.EXEMPT, tax.getCategoryCode());
        assertEquals(BigDecimal.ZERO.setScale(2), tax.getTaxAmount().getAmount());
        assertEquals(new BigDecimal("100.00"), tax.getTaxableAmount().getAmount());
    }

    @Test
    public void shouldCalculateReverseChargeVat() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createReverseChargeLine("100.00")))
                .build();

        List<Tax> taxes = SUT.calculate(invoice);

        assertEquals(1, taxes.size());

        Tax tax = taxes.getFirst();

        assertEquals(TaxCategoryCode.REVERSE_CHARGE, tax.getCategoryCode());
        assertEquals(BigDecimal.ZERO.setScale(2), tax.getTaxAmount().getAmount());
        assertEquals(new BigDecimal("100.00"), tax.getTaxableAmount().getAmount());
    }

    @Test
    public void shouldCalculateOutsideScopeVat() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createOutsideScopeLine("100.00")))
                .build();

        List<Tax> taxes = SUT.calculate(invoice);

        assertEquals(1, taxes.size());

        Tax tax = taxes.getFirst();

        assertEquals(TaxCategoryCode.OUTSIDE_SCOPE, tax.getCategoryCode());
        assertEquals(BigDecimal.ZERO.setScale(2), tax.getTaxAmount().getAmount());
        assertEquals(new BigDecimal("100.00"), tax.getTaxableAmount().getAmount());
    }

    @Test
    public void shouldCalculateReducedRateVat() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createReducedRateLine("100.00")))
                .build();

        List<Tax> taxes = SUT.calculate(invoice);

        assertEquals(1, taxes.size());

        Tax tax = taxes.getFirst();

        assertEquals(TaxCategoryCode.REDUCED_RATE, tax.getCategoryCode());
        assertEquals(new BigDecimal("7"), tax.getRate());
        assertEquals(new BigDecimal("7.00"), tax.getTaxAmount().getAmount());
    }

    @Test
    public void shouldCalculateDocumentAllowance() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createStandardLine("100.00")))
                .allowanceCharges(List.of(
                        CalculationUtils.createAllowance("19.00")))
                .build();

        List<Tax> taxes = SUT.calculate(invoice);

        assertEquals(1, taxes.size());

        Tax tax = taxes.getFirst();

        assertEquals(new BigDecimal("81.00"), tax.getTaxableAmount().getAmount());
        assertEquals(new BigDecimal("15.39"), tax.getTaxAmount().getAmount());
    }

    @Test
    public void shouldCalculateDocumentCharge() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createStandardLine("100.00")))
                .allowanceCharges(List.of(
                        CalculationUtils.createCharge("19.00")))
                .build();

        List<Tax> taxes = SUT.calculate(invoice);

        assertEquals(1, taxes.size());

        Tax tax = taxes.getFirst();

        assertEquals(new BigDecimal("119.00"), tax.getTaxableAmount().getAmount());
        assertEquals(new BigDecimal("22.61"), tax.getTaxAmount().getAmount());
    }

    @Test
    public void shouldCalculateAllowanceAndCharge() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createStandardLine("100.00")))
                .allowanceCharges(List.of(
                        CalculationUtils.createAllowance("10.00"),
                        CalculationUtils.createCharge("20.00")))
                .build();

        List<Tax> taxes = SUT.calculate(invoice);

        assertEquals(1, taxes.size());

        Tax tax = taxes.getFirst();

        assertEquals(new BigDecimal("110.00"), tax.getTaxableAmount().getAmount());
        assertEquals(new BigDecimal("20.90"), tax.getTaxAmount().getAmount());
    }

    @Test
    public void shouldCreateOneTaxPerCategoryAndRate() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createStandardLine("100.00"),
                        CalculationUtils.createStandardLine("50.00"),
                        CalculationUtils.createStandardLine("25.00")))
                .build();

        List<Tax> taxes = SUT.calculate(invoice);

        assertEquals(1, taxes.size());

        Tax tax = taxes.getFirst();

        assertEquals(new BigDecimal("175.00"), tax.getTaxableAmount().getAmount());
        assertEquals(new BigDecimal("33.25"), tax.getTaxAmount().getAmount());
    }

    @Test
    public void shouldRoundTaxAmount() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createStandardLine("100.03")))
                .build();

        List<Tax> taxes = SUT.calculate(invoice);

        assertEquals(1, taxes.size());

        Tax tax = taxes.getFirst();

        assertEquals(new BigDecimal("19.01"), tax.getTaxAmount().getAmount());
    }

    @Test
    public void shouldNotSetExemptionInformation() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createReverseChargeLine("100.00")))
                .build();

        List<Tax> taxes = SUT.calculate(invoice);

        Tax tax = taxes.getFirst();

        assertNull(tax.getExemptionReasonCode());
        assertNull(tax.getExemptionReason());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenInvoiceIsNull() {
        SUT.calculate(null);
    }

    @Test
    public void shouldGroupByCategoryAndRate() {
        Invoice invoice = Invoice.builder()
                .from(this.invoice)
                .lines(List.of(
                        CalculationUtils.createStandardLine( "100.00"),
                        CalculationUtils.createStandardLine( "50.00"),
                        CalculationUtils.createReducedRateLine("25.00")))
                .build();

        List<Tax> taxes = SUT.calculate(invoice);

        assertEquals(2, taxes.size());

        Tax standardTax = taxes.stream()
                .filter(t -> t.getCategoryCode() == TaxCategoryCode.STANDARD)
                .findFirst()
                .orElseThrow();

        Tax reducedTax = taxes.stream()
                .filter(t -> t.getCategoryCode() == TaxCategoryCode.REDUCED_RATE)
                .findFirst()
                .orElseThrow();

        assertEquals(new BigDecimal("150.00"), standardTax.getTaxableAmount().getAmount());
        assertEquals(new BigDecimal("28.50"), standardTax.getTaxAmount().getAmount());
        assertEquals(new BigDecimal("25.00"), reducedTax.getTaxableAmount().getAmount());
        assertEquals(new BigDecimal("1.75"), reducedTax.getTaxAmount().getAmount());
    }
}
