package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.UnitCode;
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
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .priceDiscount(new BigDecimal("5.00"))
                .grossPrice(CalculationUtils.createEUMoney("105.00"))
                .build();

        assertEquals("1", line.getId());
        assertEquals("Notebook", line.getItemName());
        assertEquals(BigDecimal.ONE, line.getQuantity());
        assertEquals(UnitCode.ONE, line.getUnitCode());
        assertEquals(new BigDecimal("100.00"), line.getNetPrice().getAmount());
        assertEquals(TaxCategoryCode.STANDARD, line.getTaxCategory());
        assertEquals(new BigDecimal("19"), line.getTaxRate());
        assertEquals(new BigDecimal("100.00"), line.getLineExtensionAmount().getAmount());
        assertEquals(new BigDecimal("5.00"), line.getPriceDiscount());
        assertEquals(new BigDecimal("105.00"), line.getGrossPrice().getAmount());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingId() {
        InvoiceLine.builder()
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
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
                .netPrice(CalculationUtils.createEUMoney("100.00"))
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
                .netPrice(CalculationUtils.createEUMoney("100.00"))
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
                .netPrice(CalculationUtils.createEUMoney("100.00"))
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
                .netPrice(CalculationUtils.createEUMoney("100.00"))
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
                .netPrice(CalculationUtils.createEUMoney("-100.00"))
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
                .netPrice(CalculationUtils.createEUMoney("100.00"))
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
                .netPrice(CalculationUtils.createEUMoney("100.00"))
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
                .netPrice(CalculationUtils.createEUMoney("100.00"))
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
                .netPrice(CalculationUtils.createEUMoney("100.00"))
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
                .netPrice(CalculationUtils.createEUMoney("100.00"))
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
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .priceDiscount(new BigDecimal("-1"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativeGrossPrice() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("95.00"))
                .grossPrice(CalculationUtils.createEUMoney("-100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(
                        CalculationUtils.createEUMoney("95.00"))
                .build();
    }

    @Test
    public void shouldCreateInvoiceLineWithPriceDiscountAndGrossPrice() {
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
                .lineExtensionAmount(
                        CalculationUtils.createEUMoney("95.00"))
                .build();

        assertEquals(new BigDecimal("95.00"), line.getNetPrice().getAmount());
        assertEquals(new BigDecimal("5.00"), line.getPriceDiscount());
        assertEquals(new BigDecimal("100.00"), line.getGrossPrice().getAmount());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectZeroBaseQuantity() {
        InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
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
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .baseQuantity(new BigDecimal("-1"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankNote() {
        InvoiceLine.builder()
                .id("1")
                .note("   ")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(
                        CalculationUtils.createEUMoney("100.00"))
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
                .netPrice(CalculationUtils.createEUMoney("100.00"))
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
                .netPrice(CalculationUtils.createEUMoney("100.00"))
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
                .sellerAssignedIdentifier("   ")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
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
                .buyerAssignedIdentifier("   ")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
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
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }
}