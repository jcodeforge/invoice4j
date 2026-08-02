package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class InvoicePeriod {
    /**
     * BT-73
     * Invoice period start date.
     */
    private LocalDate startDate;

    /**
     * BT-74
     * Invoice period end date.
     */
    private LocalDate endDate;

    private InvoicePeriod() {
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Returns whether both dates are present.
     */
    public boolean isDefined() {
        return startDate != null && endDate != null;
    }

    public boolean isEmpty() {
        return startDate == null && endDate == null;
    }

    public long lengthInDays() {
        if (!isDefined()) {
            return 0;
        }

        return ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    /**
     * Returns true if the period contains the given date.
     */
    public boolean contains(LocalDate date) {
        if (!isDefined() || date == null) {
            return false;
        }

        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final InvoicePeriod period;

        private Builder() {
            this.period = new InvoicePeriod();
        }

        public Builder startDate(LocalDate startDate) {
            period.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDate endDate) {
            period.endDate = endDate;
            return this;
        }

        public InvoicePeriod build() {
            if ((period.startDate == null) != (period.endDate == null)) {
                throw new InvoiceValidationException("BT-73 and BT-74 must either both be specified or both be omitted.");
            }
            if (period.startDate != null) {
                if (period.startDate.isAfter(period.endDate)) {
                    throw new InvoiceValidationException("BT-73 Invoice period start date must not be after BT-74 invoice period end date.");
                }
            }

            return period;
        }
    }
}
