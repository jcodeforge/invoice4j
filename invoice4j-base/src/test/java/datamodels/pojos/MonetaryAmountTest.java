package datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.math.BigDecimal;
import org.junit.Test;

import static org.junit.Assert.*;

public class MonetaryAmountTest {

    @Test
    public void shouldCreateMonetaryAmount() {
        MonetaryAmount amount = MonetaryAmount.builder()
                .amount(new BigDecimal("123.45"))
                .currency(CurrencyCode.EUR)
                .build();

        assertEquals(new BigDecimal("123.45"), amount.getAmount());
        assertEquals(CurrencyCode.EUR, amount.getCurrency());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNullAmount() {
        MonetaryAmount.builder()
                .currency(CurrencyCode.EUR)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNullCurrency() {
        MonetaryAmount.builder()
                .amount(new BigDecimal("10.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMoreThanThreeFractionDigits() {
        MonetaryAmount.builder()
                .amount(new BigDecimal("10.1234"))
                .currency(CurrencyCode.EUR)
                .build();
    }

    @Test
    public void shouldAllowExactlyThreeFractionDigits() {
        MonetaryAmount amount = MonetaryAmount.builder()
                .amount(new BigDecimal("10.123"))
                .currency(CurrencyCode.EUR)
                .build();

        assertEquals(new BigDecimal("10.123"), amount.getAmount());
    }

    @Test
    public void shouldAllowZeroAmount() {
        MonetaryAmount amount = MonetaryAmount.builder()
                .amount(BigDecimal.ZERO)
                .currency(CurrencyCode.EUR)
                .build();

        assertEquals(BigDecimal.ZERO, amount.getAmount());
    }

    @Test
    public void shouldAllowNegativeAmount() {
        MonetaryAmount amount = MonetaryAmount.builder()
                .amount(new BigDecimal("-10.00"))
                .currency(CurrencyCode.EUR)
                .build();

        assertEquals(new BigDecimal("-10.00"), amount.getAmount());
    }
}
