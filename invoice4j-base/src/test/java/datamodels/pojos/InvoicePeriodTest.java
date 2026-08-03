package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoicePeriod;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.*;

public class InvoicePeriodTest {

    @Test
    public void shouldCreateInvoicePeriod() {
        InvoicePeriod period = CalculationUtils.createInvoicePeriod();

        assertEquals(LocalDate.of(2026, 1, 1), period.getStartDate());
        assertEquals(LocalDate.of(2026, 1, 31), period.getEndDate());
    }

    @Test
    public void shouldBeDefined() {
        InvoicePeriod period = CalculationUtils.createInvoicePeriod();

        assertTrue(period.isDefined());
        assertFalse(period.isEmpty());
    }

    @Test
    public void shouldCalculateLengthInDays() {
        InvoicePeriod period = InvoicePeriod.builder()
                .startDate(LocalDate.of(2026, 1, 1))
                .endDate(LocalDate.of(2026, 1, 31))
                .build();

        assertEquals(31, period.lengthInDays());
    }

    @Test
    public void shouldContainDate() {
        InvoicePeriod period = CalculationUtils.createInvoicePeriod();

        assertTrue(period.contains(LocalDate.of(2026, 1, 15)));
    }

    @Test
    public void shouldNotContainDateBeforePeriod() {
        InvoicePeriod period = CalculationUtils.createInvoicePeriod();

        assertFalse(period.contains(LocalDate.of(2025, 12, 31)));
    }

    @Test
    public void shouldNotContainDateAfterPeriod() {
        InvoicePeriod period = CalculationUtils.createInvoicePeriod();

        assertFalse(period.contains(LocalDate.of(2026, 2, 1)));
    }

    @Test
    public void shouldReturnFalseForNullDate() {
        InvoicePeriod period = CalculationUtils.createInvoicePeriod();

        assertFalse(period.contains(null));
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingStartDate() {
        InvoicePeriod.builder()
                .endDate(LocalDate.of(2026, 1, 31))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingEndDate() {
        InvoicePeriod.builder()
                .startDate(LocalDate.of(2026, 1, 1))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectStartDateAfterEndDate() {
        InvoicePeriod.builder()
                .startDate(LocalDate.of(2026, 2, 1))
                .endDate(LocalDate.of(2026, 1, 31))
                .build();
    }

    @Test
    public void shouldCreateEmptyInvoicePeriod() {
        InvoicePeriod period = InvoicePeriod.builder()
                .build();

        assertTrue(period.isEmpty());
        assertFalse(period.isDefined());
        assertEquals(0, period.lengthInDays());
    }
}