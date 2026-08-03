package io.github.jcodeforge.invoice4jbase.calculation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Money {

    public static BigDecimal round(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal add(BigDecimal left, BigDecimal right) {
        return left.add(right);
    }

    public static BigDecimal subtract(BigDecimal left, BigDecimal right) {
        return left.subtract(right);
    }

    public static BigDecimal multiply(BigDecimal left, BigDecimal right) {
        return left.multiply(right);
    }

    public static BigDecimal divide(BigDecimal left, BigDecimal right) {
        if (right.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero.");
        }

        return left.divide(right, 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal percentage(BigDecimal base, BigDecimal percentage) {
        return base.multiply(percentage).divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP);
    }

    public static boolean isZero(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean isPositive(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isNegative(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) < 0;
    }

    public static int compare(BigDecimal left, BigDecimal right) {
        return left.compareTo(right);
    }
}
