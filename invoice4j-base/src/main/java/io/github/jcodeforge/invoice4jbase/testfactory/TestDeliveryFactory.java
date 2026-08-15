package io.github.jcodeforge.invoice4jbase.testfactory;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Delivery;
import java.time.LocalDate;

public final class TestDeliveryFactory {

    public static Delivery createDelivery() {
        return Delivery.builder()
                .shipTo(TestPartyFactory.createShipTo())
                .actualDeliveryDate(LocalDate.of(2026, 1, 15))
                .deliveryPeriodStartDate(LocalDate.of(2026, 1, 1))
                .deliveryPeriodEndDate(LocalDate.of(2026, 1, 31))
                .build();
    }
}
