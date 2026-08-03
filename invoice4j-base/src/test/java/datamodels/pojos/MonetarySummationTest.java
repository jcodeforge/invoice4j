package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetarySummation;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.math.BigDecimal;
import org.junit.Test;

import static org.junit.Assert.*;

public class MonetarySummationTest {

    @Test
    public void shouldCreateMonetarySummation() {
        MonetarySummation summation = CalculationUtils.createMonetarySummation();

        assertEquals(new BigDecimal("100.00"), summation.getLineExtensionAmount().getAmount());
        assertEquals(new BigDecimal("19.00"), summation.getTaxAmount().getAmount());
        assertEquals(new BigDecimal("119.00"), summation.getPayableAmount().getAmount());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingLineExtensionAmount() {
        MonetarySummation.builder()
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .payableAmount(CalculationUtils.createEUMoney("119.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingTaxAmount() {
        MonetarySummation.builder()
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .payableAmount(CalculationUtils.createEUMoney("119.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingPayableAmount() {
        MonetarySummation.builder()
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativeLineExtensionAmount() {
        MonetarySummation.builder()
                .lineExtensionAmount(CalculationUtils.createEUMoney("-1.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .payableAmount(CalculationUtils.createEUMoney("119.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativeTaxAmount() {
        MonetarySummation.builder()
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("-1.00"))
                .payableAmount(CalculationUtils.createEUMoney("119.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativePayableAmount() {
        MonetarySummation.builder()
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .payableAmount(CalculationUtils.createEUMoney("-1.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativeAllowanceTotalAmount() {
        MonetarySummation.builder()
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .allowanceTotalAmount(CalculationUtils.createEUMoney("-1.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .payableAmount(CalculationUtils.createEUMoney("119.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativeChargeTotalAmount() {
        MonetarySummation.builder()
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .chargeTotalAmount(CalculationUtils.createEUMoney("-1.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .payableAmount(CalculationUtils.createEUMoney("119.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativeTaxExclusiveAmount() {
        MonetarySummation.builder()
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .taxExclusiveAmount(CalculationUtils.createEUMoney("-1.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .payableAmount(CalculationUtils.createEUMoney("119.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativeTaxInclusiveAmount() {
        MonetarySummation.builder()
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .taxInclusiveAmount(CalculationUtils.createEUMoney("-1.00"))
                .payableAmount(CalculationUtils.createEUMoney("119.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativePrepaidAmount() {
        MonetarySummation.builder()
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .prepaidAmount(CalculationUtils.createEUMoney("-1.00"))
                .payableAmount(CalculationUtils.createEUMoney("119.00"))
                .build();
    }

    @Test
    public void shouldAllowNegativeRoundingAmount() {
        MonetarySummation summation = MonetarySummation.builder()
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .roundingAmount(CalculationUtils.createEUMoney("-0.01"))
                .payableAmount(CalculationUtils.createEUMoney("118.99"))
                .build();

        assertEquals(new BigDecimal("-0.01"), summation.getRoundingAmount().getAmount());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectDifferentCurrencyForAllowanceTotalAmount() {
        MonetarySummation.builder()
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .allowanceTotalAmount(CalculationUtils.createUsdMoney("10.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .payableAmount(CalculationUtils.createEUMoney("119.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectDifferentCurrencyForChargeTotalAmount() {
        MonetarySummation.builder()
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .chargeTotalAmount(CalculationUtils.createUsdMoney("10.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .payableAmount(CalculationUtils.createEUMoney("119.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectDifferentCurrencyForTaxExclusiveAmount() {
        MonetarySummation.builder()
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .taxExclusiveAmount(CalculationUtils.createUsdMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .payableAmount(CalculationUtils.createEUMoney("119.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectDifferentCurrencyForTaxInclusiveAmount() {
        MonetarySummation.builder()
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .taxInclusiveAmount(CalculationUtils.createUsdMoney("119.00"))
                .payableAmount(CalculationUtils.createEUMoney("119.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectDifferentCurrencyForPrepaidAmount() {
        MonetarySummation.builder()
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .prepaidAmount(CalculationUtils.createUsdMoney("20.00"))
                .payableAmount(CalculationUtils.createEUMoney("119.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectDifferentCurrencyForRoundingAmount() {
        MonetarySummation.builder()
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .roundingAmount(CalculationUtils.createUsdMoney("0.01"))
                .payableAmount(CalculationUtils.createEUMoney("119.01"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectDifferentCurrencyForPayableAmount() {
        MonetarySummation.builder()
                .lineExtensionAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .payableAmount(CalculationUtils.createUsdMoney("119.00"))
                .build();
    }
}