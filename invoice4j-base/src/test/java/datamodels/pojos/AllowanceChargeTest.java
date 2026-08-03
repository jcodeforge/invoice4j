package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.AllowanceCharge;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.math.BigDecimal;
import org.junit.Test;

import static org.junit.Assert.*;

public class AllowanceChargeTest {

    @Test
    public void shouldCreateAllowance() {
        AllowanceCharge allowance = AllowanceCharge.builder()
                .charge(false)
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        assertFalse(allowance.isCharge());
        assertEquals(new BigDecimal("10.00"), allowance.getAmount().getAmount());
        assertEquals(TaxCategoryCode.STANDARD, allowance.getTaxCategory());
        assertEquals(new BigDecimal("19"), allowance.getTaxRate());
    }

    @Test
    public void shouldCreateCharge() {
        AllowanceCharge charge = AllowanceCharge.builder()
                .charge(true)
                .amount(CalculationUtils.createEUMoney("15.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        assertTrue(charge.isCharge());
        assertEquals(new BigDecimal("15.00"), charge.getAmount().getAmount());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingAmount() {
        AllowanceCharge.builder()
                .charge(false)
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativeAmount() {
        AllowanceCharge.builder()
                .charge(false)
                .amount(CalculationUtils.createEUMoney("-1.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingTaxCategory() {
        AllowanceCharge.builder()
                .charge(false)
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxRate(new BigDecimal("19"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingTaxRate() {
        AllowanceCharge.builder()
                .charge(false)
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativeTaxRate() {
        AllowanceCharge.builder()
                .charge(false)
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("-1"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectTaxRateGreaterThan100() {
        AllowanceCharge.builder()
                .charge(false)
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("101"))
                .build();
    }

    @Test
    public void shouldCreateAllowanceWithPercentage() {
        AllowanceCharge allowance = AllowanceCharge.builder()
                .charge(false)
                .percentage(new BigDecimal("10"))
                .baseAmount(CalculationUtils.createEUMoney("100.00"))
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        assertEquals(new BigDecimal("10"), allowance.getPercentage());
        assertEquals(new BigDecimal("100.00"), allowance.getBaseAmount().getAmount());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectPercentageWithoutBaseAmount() {
        AllowanceCharge.builder()
                .charge(false)
                .percentage(new BigDecimal("10"))
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativePercentage() {
        AllowanceCharge.builder()
                .charge(false)
                .percentage(new BigDecimal("-1"))
                .baseAmount(CalculationUtils.createEUMoney("100.00"))
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectPercentageGreaterThan100() {
        AllowanceCharge.builder()
                .charge(false)
                .percentage(new BigDecimal("101"))
                .baseAmount(CalculationUtils.createEUMoney("100.00"))
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativeBaseAmount() {
        AllowanceCharge.builder()
                .charge(false)
                .baseAmount(CalculationUtils.createEUMoney("-100.00"))
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectDifferentCurrencies() {
        MonetaryAmount baseAmount = MonetaryAmount.builder()
                .amount(new BigDecimal("100.00"))
                .currency(CurrencyCode.USD)
                .build();

        AllowanceCharge.builder()
                .charge(false)
                .baseAmount(baseAmount)
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();
    }

    @Test
    public void shouldAcceptReasonAndReasonCode() {
        AllowanceCharge allowance = AllowanceCharge.builder()
                .charge(false)
                .reason("Promotional discount")
                .reasonCode("95")
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        assertEquals("Promotional discount", allowance.getReason());
        assertEquals("95", allowance.getReasonCode());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankReason() {
        AllowanceCharge.builder()
                .charge(false)
                .reason("   ")
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankReasonCode() {
        AllowanceCharge.builder()
                .charge(false)
                .reasonCode("   ")
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();
    }

    @Test
    public void shouldAllowZeroPercentage() {
        AllowanceCharge allowance = AllowanceCharge.builder()
                .charge(false)
                .percentage(BigDecimal.ZERO)
                .baseAmount(CalculationUtils.createEUMoney("100.00"))
                .amount(CalculationUtils.createEUMoney("0.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        assertEquals(BigDecimal.ZERO, allowance.getPercentage());
    }

    @Test
    public void shouldAllowPercentageOfOneHundred() {
        AllowanceCharge allowance = AllowanceCharge.builder()
                .charge(false)
                .percentage(new BigDecimal("100"))
                .baseAmount(CalculationUtils.createEUMoney("100.00"))
                .amount(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        assertEquals(new BigDecimal("100"), allowance.getPercentage());
    }

    @Test
    public void shouldTrimReason() {
        AllowanceCharge allowance = AllowanceCharge.builder()
                .charge(false)
                .reason("  Discount  ")
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        assertEquals("Discount", allowance.getReason());
    }

    @Test
    public void shouldTrimReasonCode() {
        AllowanceCharge allowance = AllowanceCharge.builder()
                .charge(false)
                .reasonCode(" 95 ")
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        assertEquals("95", allowance.getReasonCode());
    }

    @Test
    public void shouldAllowNullReason() {
        AllowanceCharge allowance = AllowanceCharge.builder()
                .charge(false)
                .reason(null)
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        assertNull(allowance.getReason());
    }

    @Test
    public void shouldAllowNullReasonCode() {
        AllowanceCharge allowance = AllowanceCharge.builder()
                .charge(false)
                .reasonCode(null)
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        assertNull(allowance.getReasonCode());
    }

    @Test
    public void shouldAllowMissingPercentageAndBaseAmount() {
        AllowanceCharge allowance = AllowanceCharge.builder()
                .charge(false)
                .amount(CalculationUtils.createEUMoney("10.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

        assertNull(allowance.getPercentage());
        assertNull(allowance.getBaseAmount());
    }
}