package io.github.jcodeforge.invoice4jbase.testfactory;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoicePeriod;
import java.time.LocalDate;

public final class TestInvoicePeriodFactory {

    public static InvoicePeriod createInvoicePeriod() {
        return InvoicePeriod.builder()
                .startDate(LocalDate.of(2026, 1, 1))
                .endDate(LocalDate.of(2026, 1, 31))
                .build();
    }
}
