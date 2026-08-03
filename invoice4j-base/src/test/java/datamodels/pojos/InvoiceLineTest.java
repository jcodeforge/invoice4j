package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.UnitCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoiceLine;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.math.BigDecimal;

import org.junit.Test;

import static org.junit.Assert.*;

public class InvoiceLineTest {

    @Test
    public void shouldCreateInvoiceLine() {
        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();

        assertEquals("1", line.getId());
        assertEquals("Notebook", line.getItemName());
        assertEquals(BigDecimal.ONE, line.getQuantity());
        assertEquals(UnitCode.ONE, line.getUnitCode());
        assertEquals(new BigDecimal("100.00"), line.getUnitPrice().getAmount());
        assertEquals(TaxCategoryCode.STANDARD, line.getTaxCategory());
        assertEquals(new BigDecimal("19"), line.getTaxRate());
        assertEquals(new BigDecimal("100.00"), line.getLineExtensionAmount().getAmount());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingId() {
        InvoiceLine.builder()
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingItemName() {
        InvoiceLine.builder()
                .id("1")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingQuantity() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingUnitCode() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingUnitPrice() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingTaxCategory() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativeUnitPrice() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("-100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativeLineExtensionAmount() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("-100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativeVatRate() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("-1"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectVatRateGreaterThan100() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("101"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectZeroQuantity() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ZERO)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativeQuantity() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(new BigDecimal("-1"))
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativePriceDiscount() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .priceDiscount(new BigDecimal("-1"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativePriceDiscountPercentage() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .priceDiscountPercentage(new BigDecimal("-1"))
                .baseQuantity(BigDecimal.ONE)
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectPriceDiscountPercentageGreaterThan100() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .priceDiscountPercentage(new BigDecimal("101"))
                .baseQuantity(BigDecimal.ONE)
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingBaseQuantityWhenDiscountPercentageIsSpecified() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .priceDiscountPercentage(new BigDecimal("10"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("90.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectZeroBaseQuantity() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .baseQuantity(BigDecimal.ZERO)
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativeBaseQuantity() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .baseQuantity(new BigDecimal("-1"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankObjectIdentifier() {
        InvoiceLine.builder()
                .id("1")
                .objectIdentifier("   ")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankBuyerAccountingReference() {
        InvoiceLine.builder()
                .id("1")
                .buyerAccountingReference("   ")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankDescription() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .description("   ")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankSellerItemIdentifier() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .sellerItemIdentifier("   ")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankBuyerItemIdentifier() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .buyerItemIdentifier("   ")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankItemClassificationIdentifier() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .itemClassificationIdentifier("   ")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }
}