package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PaymentDiscount;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.math.BigDecimal;
import org.junit.Test;

import static org.junit.Assert.*;

public class PaymentDiscountTest {

    @Test
    public void shouldCreatePaymentDiscount() {
        PaymentDiscount discount = CalculationUtils.createPaymentDiscount();

        assertEquals(new BigDecimal("2.00"), discount.getPercentage());
        assertEquals(new BigDecimal("2.00"), discount.getAmount().getAmount());
        assertEquals(Integer.valueOf(14), discount.getPaymentDays());
    }

    @Test
    public void shouldCreatePercentageDiscountOnly() {
        PaymentDiscount discount = PaymentDiscount.builder()
                .percentage(new BigDecimal("2.00"))
                .paymentDays(14)
                .build();

        assertEquals(new BigDecimal("2.00"), discount.getPercentage());
        assertNull(discount.getAmount());
        assertEquals(Integer.valueOf(14), discount.getPaymentDays());
    }

    @Test
    public void shouldCreateAmountDiscountOnly() {
        PaymentDiscount discount = PaymentDiscount.builder()
                .amount(CalculationUtils.createEUMoney("2.00"))
                .paymentDays(14)
                .build();

        assertNull(discount.getPercentage());
        assertEquals(new BigDecimal("2.00"), discount.getAmount().getAmount());
        assertEquals(Integer.valueOf(14), discount.getPaymentDays());
    }

    @Test
    public void shouldCreatePercentageAndAmountDiscount() {
        PaymentDiscount discount = PaymentDiscount.builder()
                .percentage(new BigDecimal("2.00"))
                .amount(CalculationUtils.createEUMoney("2.00"))
                .paymentDays(14)
                .build();

        assertEquals(new BigDecimal("2.00"), discount.getPercentage());
        assertEquals(new BigDecimal("2.00"), discount.getAmount().getAmount());
        assertEquals(Integer.valueOf(14), discount.getPaymentDays());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingPercentageAndAmount() {
        PaymentDiscount.builder()
                .paymentDays(14)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectZeroPercentage() {
        PaymentDiscount.builder()
                .percentage(BigDecimal.ZERO)
                .paymentDays(14)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativePercentage() {
        PaymentDiscount.builder()
                .percentage(new BigDecimal("-1"))
                .paymentDays(14)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectPercentageGreaterThan100() {
        PaymentDiscount.builder()
                .percentage(new BigDecimal("101"))
                .paymentDays(14)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectZeroAmount() {
        PaymentDiscount.builder()
                .amount(CalculationUtils.createEUMoney("0.00"))
                .paymentDays(14)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativeAmount() {
        PaymentDiscount.builder()
                .amount(CalculationUtils.createEUMoney("-1.00"))
                .paymentDays(14)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingPaymentDays() {
        PaymentDiscount.builder()
                .percentage(new BigDecimal("2.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectZeroPaymentDays() {
        PaymentDiscount.builder()
                .percentage(new BigDecimal("2.00"))
                .paymentDays(0)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativePaymentDays() {
        PaymentDiscount.builder()
                .percentage(new BigDecimal("2.00"))
                .paymentDays(-1)
                .build();
    }
}