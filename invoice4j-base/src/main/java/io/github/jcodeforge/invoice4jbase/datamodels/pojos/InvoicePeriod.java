package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import java.time.LocalDate;

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
            return period;
        }
    }
}
