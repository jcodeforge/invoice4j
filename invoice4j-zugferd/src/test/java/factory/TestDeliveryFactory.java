package factory;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Delivery;
import java.time.LocalDate;

public final class TestDeliveryFactory {

    public static Delivery createDelivery() {
        return Delivery.builder()
                .actualDeliveryDate(LocalDate.of(2026, 1, 15))
                .deliveryPeriodStartDate(LocalDate.of(2026, 1, 1))
                .deliveryPeriodEndDate(LocalDate.of(2026, 1, 31))
                .address(TestPartyFactory.createAddress())
                .build();
    }
}
