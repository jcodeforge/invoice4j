package io.github.jcodeforge.invoice4jbase.calculation;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.UnitCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.AllowanceCharge;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoiceLine;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.math.BigDecimal;

public class InvoiceLineCalculatorTest {

    private InvoiceLineCalculator SUT;

    @Before
    public void setUp() {
        SUT = new InvoiceLineCalculator();
    }

    @Test
    public void shouldCalculateLineWithoutDiscounts() {
        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(new BigDecimal("2"))
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        InvoiceLine result = SUT.calculate(line);

        assertEquals(new BigDecimal("200.00"), result.getLineExtensionAmount().getAmount());

        assertEquals(CurrencyCode.EUR, result.getLineExtensionAmount().getCurrency());
    }

    @Test
    public void shouldPreserveFixedPriceDiscount() {
        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .priceDiscount(new BigDecimal("15.00"))
                .grossPrice(CalculationUtils.createEUMoney("115.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        InvoiceLine result = SUT.calculate(line);

        assertEquals(new BigDecimal("100.00"), result.getLineExtensionAmount().getAmount());
        assertEquals(new BigDecimal("15.00"), result.getPriceDiscount());
        assertEquals(new BigDecimal("115.00"), result.getGrossPrice().getAmount());
    }

    @Test
    public void shouldPreservePriceDiscountAndGrossPrice() {
        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("95.00"))
                .priceDiscount(new BigDecimal("5.00"))
                .grossPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        InvoiceLine result = SUT.calculate(line);

        assertEquals(
                new BigDecimal("5.00"),
                result.getPriceDiscount()
        );

        assertEquals(CalculationUtils.createEUMoney("100.00"), result.getGrossPrice());
        assertEquals(new BigDecimal("95.00"), result.getLineExtensionAmount().getAmount());
    }

    @Test
    public void shouldCalculateLineWithLineAllowance() {
        AllowanceCharge allowance = AllowanceCharge.builder()
                .charge(false)
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .reason("Discount")
                .build();

        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .addAllowanceCharge(allowance)
                .build();

        InvoiceLine result = SUT.calculate(line);

        assertEquals(new BigDecimal("90.00"), result.getLineExtensionAmount().getAmount());
    }

    @Test
    public void shouldCalculateLineWithLineCharge() {
        AllowanceCharge charge = AllowanceCharge.builder()
                .charge(true)
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .reason("Discount")
                .build();

        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .addAllowanceCharge(charge)
                .build();

        InvoiceLine result = SUT.calculate(line);

        assertEquals(new BigDecimal("110.00"), result.getLineExtensionAmount().getAmount());
    }

    @Test
    public void shouldCalculateLineWithMultipleAllowances() {
        AllowanceCharge allowance1 = AllowanceCharge.builder()
                .charge(false)
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .reason("Discount")
                .build();

        AllowanceCharge allowance2 = AllowanceCharge.builder()
                .charge(false)
                .amount(CalculationUtils.createEUMoney("5.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .reason("Discount")
                .build();

        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .addAllowanceCharge(allowance1)
                .addAllowanceCharge(allowance2)
                .build();

        InvoiceLine result = SUT.calculate(line);

        assertEquals(new BigDecimal("85.00"), result.getLineExtensionAmount().getAmount());
    }

    @Test
    public void shouldCalculateLineWithMultipleCharges() {
        AllowanceCharge charge1 = AllowanceCharge.builder()
                .charge(true)
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .reason("Discount")
                .build();

        AllowanceCharge charge2 = AllowanceCharge.builder()
                .charge(true)
                .amount(CalculationUtils.createEUMoney("5.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .reason("Discount")
                .build();

        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .addAllowanceCharge(charge1)
                .addAllowanceCharge(charge2)
                .build();

        InvoiceLine result = SUT.calculate(line);

        assertEquals(new BigDecimal("115.00"), result.getLineExtensionAmount().getAmount());
    }

    @Test
    public void shouldPreserveInvoiceLineProperties() {
        InvoiceLine line = InvoiceLine.builder()
                .id("42")
                .note("OBJ-1")
                .buyerAccountingReference("ACC-1")
                .itemName("Notebook")
                .description("Business notebook")
                .sellerAssignedIdentifier("SELL-1")
                .buyerAssignedIdentifier("BUY-1")
                .itemClassificationIdentifier("12345678")
                .quantity(new BigDecimal("2"))
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        InvoiceLine result = SUT.calculate(line);

        assertEquals(line.getPriceDiscount(), result.getPriceDiscount());
        assertEquals(line.getGrossPrice(), result.getGrossPrice());
        assertEquals(line.getBaseQuantity(), result.getBaseQuantity());
        assertEquals(line.getAllowanceCharges(), result.getAllowanceCharges());
        assertEquals(line.getProperties(), result.getProperties());
    }

    @Test
    public void shouldReturnNewInvoiceLineInstance() {
        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        InvoiceLine result = SUT.calculate(line);

        assertNotSame(line, result);
    }

    @Test
    public void shouldKeepOriginalInvoiceLineUnchanged() {
        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        assertNull(line.getLineExtensionAmount());

        InvoiceLine result = SUT.calculate(line);

        assertNull(line.getLineExtensionAmount());

        assertNotNull(result.getLineExtensionAmount());
        assertEquals(new BigDecimal("100.00"), result.getLineExtensionAmount().getAmount());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenInvoiceLineIsNull() {
        SUT.calculate(null);
    }

    @Test
    public void shouldCalculateLineWithZeroAmountAllowance() {
        AllowanceCharge allowance = AllowanceCharge.builder()
                .charge(false)
                .amount(CalculationUtils.createEUMoney("0.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .reason("Discount")
                .build();

        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .addAllowanceCharge(allowance)
                .build();

        InvoiceLine result = SUT.calculate(line);

        assertEquals(new BigDecimal("100.00"), result.getLineExtensionAmount().getAmount());
    }

    @Test
    public void shouldCalculateLineWithZeroAmountCharge() {
        AllowanceCharge charge = AllowanceCharge.builder()
                .charge(true)
                .amount(CalculationUtils.createEUMoney("0.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .reason("Discount")
                .build();

        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .addAllowanceCharge(charge)
                .build();

        InvoiceLine result = SUT.calculate(line);

        assertEquals(new BigDecimal("100.00"), result.getLineExtensionAmount().getAmount());
    }

    @Test
    public void shouldRoundHalfUpCorrectly() {
        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(new BigDecimal("3"))
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("33.335"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        InvoiceLine result = SUT.calculate(line);

        assertEquals(new BigDecimal("100.01"), result.getLineExtensionAmount().getAmount());
    }
}
