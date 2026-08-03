package calculation;

import io.github.jcodeforge.invoice4jbase.calculation.Money;
import org.junit.Test;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class MoneyTest {

    @Test
    public void shouldAdd() {
        BigDecimal result = Money.add(new BigDecimal("10.50"), new BigDecimal("5.25"));
        assertEquals(new BigDecimal("15.75"), result);
    }

    @Test
    public void shouldSubtract() {
        BigDecimal result = Money.subtract(new BigDecimal("10.50"), new BigDecimal("5.25"));
        assertEquals(new BigDecimal("5.25"), result);
    }

    @Test
    public void shouldMultiply() {
        BigDecimal result = Money.multiply(new BigDecimal("10.50"), new BigDecimal("3"));
        assertEquals(new BigDecimal("31.50"), result);
    }

    @Test
    public void shouldDivide() {
        BigDecimal result = Money.divide(new BigDecimal("10"), new BigDecimal("4"));
        assertEquals(new BigDecimal("2.50000000"), result);
    }

    @Test
    public void shouldCalculatePercentage() {
        BigDecimal result = Money.percentage(new BigDecimal("100"), new BigDecimal("19"));
        assertEquals(new BigDecimal("19.00000000"), result);
    }

    @Test
    public void shouldCalculateZeroPercentage() {
        BigDecimal result = Money.percentage(new BigDecimal("100"), BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO.setScale(8), result);
    }

    @Test
    public void shouldCalculateHundredPercent() {
        BigDecimal result = Money.percentage(new BigDecimal("100"), new BigDecimal("100"));
        assertEquals(new BigDecimal("100.00000000"), result);
    }

    /**
     * Ensures that percentage calculations are not rounded prematurely.
     */
    @Test
    public void shouldNotRoundIntermediatePercentageCalculation() {
        BigDecimal result = Money.percentage(new BigDecimal("33.335"), new BigDecimal("10"));
        assertEquals(new BigDecimal("3.33350000"), result);
    }

    @Test
    public void shouldRoundHalfUp() {
        BigDecimal result = Money.round(new BigDecimal("100.005"));
        assertEquals(new BigDecimal("100.01"), result);
    }

    @Test
    public void shouldRoundDown() {
        BigDecimal result = Money.round(new BigDecimal("100.004"));
        assertEquals(new BigDecimal("100.00"), result);
    }

    @Test
    public void shouldRoundWholeNumber() {
        BigDecimal result = Money.round(new BigDecimal("100"));
        assertEquals(new BigDecimal("100.00"), result);
    }

    @Test
    public void shouldRoundZero() {
        BigDecimal result = Money.round(BigDecimal.ZERO);
        assertEquals(new BigDecimal("0.00"), result);
    }

    @Test
    public void shouldRoundNegativeHalfUp() {
        BigDecimal result = Money.round(new BigDecimal("-100.005"));
        assertEquals(new BigDecimal("-100.01"), result);
    }

    @Test
    public void shouldRoundNegativeDown() {
        BigDecimal result = Money.round(new BigDecimal("-100.004"));
        assertEquals(new BigDecimal("-100.00"), result);
    }

    @Test
    public void shouldNotRoundIntermediateArithmeticResults() {
        BigDecimal result = Money.subtract(new BigDecimal("33.335"), BigDecimal.ZERO);
        assertEquals(new BigDecimal("33.335"), result);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenAddLeftIsNull() {
        Money.add(null, BigDecimal.ONE);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenAddRightIsNull() {
        Money.add(BigDecimal.ONE, null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenSubtractLeftIsNull() {
        Money.subtract(null, BigDecimal.ONE);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenSubtractRightIsNull() {
        Money.subtract(BigDecimal.ONE, null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenMultiplyLeftIsNull() {
        Money.multiply(null, BigDecimal.ONE);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenMultiplyRightIsNull() {
        Money.multiply(BigDecimal.ONE, null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenDivideLeftIsNull() {
        Money.divide(null, BigDecimal.ONE);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenDivideRightIsNull() {
        Money.divide(BigDecimal.ONE, null);
    }

    @Test(expected = ArithmeticException.class)
    public void shouldThrowArithmeticExceptionWhenDivideByZero() {
        Money.divide(BigDecimal.ONE, BigDecimal.ZERO);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenPercentageBaseIsNull() {
        Money.percentage(null, BigDecimal.TEN);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenPercentageIsNull() {
        Money.percentage(BigDecimal.TEN, null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenRoundValueIsNull() {
        Money.round(null);
    }
}
