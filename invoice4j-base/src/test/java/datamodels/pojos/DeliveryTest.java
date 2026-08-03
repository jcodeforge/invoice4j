package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Delivery;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeliveryTest {

    @Test
    public void shouldCreateDelivery() {
        Delivery delivery = CalculationUtils.createDelivery();

        assertNotNull(delivery);
        assertEquals(LocalDate.of(2026, 1, 15), delivery.getActualDeliveryDate());
        assertEquals(LocalDate.of(2026, 1, 1), delivery.getDeliveryPeriodStartDate());
        assertEquals(LocalDate.of(2026, 1, 31), delivery.getDeliveryPeriodEndDate());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectOnlyDeliveryPeriodStartDate() {
        Delivery.builder()
                .deliveryPeriodStartDate(LocalDate.of(2026, 1, 1))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectOnlyDeliveryPeriodEndDate() {
        Delivery.builder()
                .deliveryPeriodEndDate(LocalDate.of(2026, 1, 31))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectDeliveryPeriodStartAfterEnd() {
        Delivery.builder()
                .actualDeliveryDate(LocalDate.of(2026, 1, 10))
                .deliveryPeriodStartDate(LocalDate.of(2026, 2, 1))
                .deliveryPeriodEndDate(LocalDate.of(2026, 1, 31))
                .address(CalculationUtils.createAddress())
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectActualDeliveryDateBeforeDeliveryPeriod() {
        Delivery.builder()
                .actualDeliveryDate(LocalDate.of(2025, 12, 31))
                .deliveryPeriodStartDate(LocalDate.of(2026, 1, 1))
                .deliveryPeriodEndDate(LocalDate.of(2026, 1, 31))
                .address(CalculationUtils.createAddress())
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectActualDeliveryDateAfterDeliveryPeriod() {
        Delivery.builder()
                .actualDeliveryDate(LocalDate.of(2026, 2, 1))
                .deliveryPeriodStartDate(LocalDate.of(2026, 1, 1))
                .deliveryPeriodEndDate(LocalDate.of(2026, 1, 31))
                .address(CalculationUtils.createAddress())
                .build();
    }
}