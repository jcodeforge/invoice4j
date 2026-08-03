package calculation;

import io.github.jcodeforge.invoice4jbase.calculation.InvoiceLineCalculator;
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

    private InvoiceLineCalculator calculator;

    @Before
    public void setUp() {
        calculator = new InvoiceLineCalculator();
    }

    @Test
    public void shouldCalculateLineWithoutDiscounts() {
        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(new BigDecimal("2"))
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        InvoiceLine result = calculator.calculate(line);

        assertEquals(new BigDecimal("200.00"), result.getLineExtensionAmount().getAmount());

        assertEquals(CurrencyCode.EUR, result.getLineExtensionAmount().getCurrency());
    }

    @Test
    public void shouldCalculateLineWithFixedPriceDiscount() {
        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createMoney("100.00"))
                .priceDiscount(new BigDecimal("15.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        InvoiceLine result = calculator.calculate(line);

        assertEquals(new BigDecimal("85.00"), result.getLineExtensionAmount().getAmount());
    }

    @Test
    public void shouldCalculateLineWithPercentagePriceDiscount() {
        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createMoney("100.00"))
                .priceDiscountPercentage(new BigDecimal("10"))
                .baseQuantity(BigDecimal.ONE)
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        InvoiceLine result = calculator.calculate(line);

        assertEquals(new BigDecimal("90.00"), result.getLineExtensionAmount().getAmount());
    }

    @Test
    public void shouldPreferFixedDiscountOverPercentageDiscount() {
        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createMoney("100.00"))
                .priceDiscount(new BigDecimal("20.00"))
                .priceDiscountPercentage(new BigDecimal("10"))
                .baseQuantity(BigDecimal.ONE)
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        InvoiceLine result = calculator.calculate(line);

        // Fixed discount (20.00) takes precedence over 10%
        assertEquals(new BigDecimal("80.00"), result.getLineExtensionAmount().getAmount());
    }

    @Test
    public void shouldCalculateLineWithLineAllowance() {
        AllowanceCharge allowance = AllowanceCharge.builder()
                .charge(false)
                .amount(CalculationUtils.createMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .addAllowanceCharge(allowance)
                .build();

        InvoiceLine result = calculator.calculate(line);

        assertEquals(new BigDecimal("90.00"), result.getLineExtensionAmount().getAmount());
    }

    @Test
    public void shouldCalculateLineWithLineCharge() {
        AllowanceCharge charge = AllowanceCharge.builder()
                .charge(true)
                .amount(CalculationUtils.createMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .addAllowanceCharge(charge)
                .build();

        InvoiceLine result = calculator.calculate(line);

        assertEquals(new BigDecimal("110.00"), result.getLineExtensionAmount().getAmount());
    }

    @Test
    public void shouldCalculateLineWithMultipleAllowances() {
        AllowanceCharge allowance1 = AllowanceCharge.builder()
                .charge(false)
                .amount(CalculationUtils.createMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        AllowanceCharge allowance2 = AllowanceCharge.builder()
                .charge(false)
                .amount(CalculationUtils.createMoney("5.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .addAllowanceCharge(allowance1)
                .addAllowanceCharge(allowance2)
                .build();

        InvoiceLine result = calculator.calculate(line);

        assertEquals(new BigDecimal("85.00"), result.getLineExtensionAmount().getAmount());
    }

    @Test
    public void shouldCalculateLineWithMultipleCharges() {
        AllowanceCharge charge1 = AllowanceCharge.builder()
                .charge(true)
                .amount(CalculationUtils.createMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        AllowanceCharge charge2 = AllowanceCharge.builder()
                .charge(true)
                .amount(CalculationUtils.createMoney("5.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .addAllowanceCharge(charge1)
                .addAllowanceCharge(charge2)
                .build();

        InvoiceLine result = calculator.calculate(line);

        assertEquals(new BigDecimal("115.00"), result.getLineExtensionAmount().getAmount());
    }

    @Test
    public void shouldPreserveInvoiceLineProperties() {
        InvoiceLine line = InvoiceLine.builder()
                .id("42")
                .objectIdentifier("OBJ-1")
                .buyerAccountingReference("ACC-1")
                .itemName("Notebook")
                .description("Business notebook")
                .sellerItemIdentifier("SELL-1")
                .buyerItemIdentifier("BUY-1")
                .itemClassificationIdentifier("12345678")
                .quantity(new BigDecimal("2"))
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        InvoiceLine result = calculator.calculate(line);

        assertEquals(line.getId(), result.getId());
        assertEquals(line.getObjectIdentifier(), result.getObjectIdentifier());
        assertEquals(line.getBuyerAccountingReference(), result.getBuyerAccountingReference());
        assertEquals(line.getItemName(), result.getItemName());
        assertEquals(line.getDescription(), result.getDescription());
        assertEquals(line.getSellerItemIdentifier(), result.getSellerItemIdentifier());
        assertEquals(line.getBuyerItemIdentifier(), result.getBuyerItemIdentifier());
        assertEquals(line.getItemClassificationIdentifier(), result.getItemClassificationIdentifier());
        assertEquals(line.getQuantity(), result.getQuantity());
        assertEquals(line.getUnitCode(), result.getUnitCode());
        assertEquals(line.getUnitPrice(), result.getUnitPrice());
        assertEquals(line.getTaxCategory(), result.getTaxCategory());
        assertEquals(line.getTaxRate(), result.getTaxRate());
    }

    @Test
    public void shouldReturnNewInvoiceLineInstance() {
        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        InvoiceLine result = calculator.calculate(line);

        assertNotSame(line, result);
    }

    @Test
    public void shouldKeepOriginalInvoiceLineUnchanged() {
        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        assertNull(line.getLineExtensionAmount());

        InvoiceLine result = calculator.calculate(line);

        assertNull(line.getLineExtensionAmount());

        assertNotNull(result.getLineExtensionAmount());
        assertEquals(new BigDecimal("100.00"), result.getLineExtensionAmount().getAmount());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenInvoiceLineIsNull() {
        calculator.calculate(null);
    }

    @Test
    public void shouldCalculateLineWithZeroAmountAllowance() {
        AllowanceCharge allowance = AllowanceCharge.builder()
                .charge(false)
                .amount(CalculationUtils.createMoney("0.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .addAllowanceCharge(allowance)
                .build();

        InvoiceLine result = calculator.calculate(line);

        assertEquals(new BigDecimal("100.00"), result.getLineExtensionAmount().getAmount());
    }

    @Test
    public void shouldCalculateLineWithZeroAmountCharge() {
        AllowanceCharge charge = AllowanceCharge.builder()
                .charge(true)
                .amount(CalculationUtils.createMoney("0.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .addAllowanceCharge(charge)
                .build();

        InvoiceLine result = calculator.calculate(line);

        assertEquals(new BigDecimal("100.00"), result.getLineExtensionAmount().getAmount());
    }

    @Test
    public void shouldRoundHalfUpCorrectly() {
        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(new BigDecimal("3"))
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createMoney("33.335"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        InvoiceLine result = calculator.calculate(line);

        assertEquals(new BigDecimal("100.01"), result.getLineExtensionAmount().getAmount());
    }
}
