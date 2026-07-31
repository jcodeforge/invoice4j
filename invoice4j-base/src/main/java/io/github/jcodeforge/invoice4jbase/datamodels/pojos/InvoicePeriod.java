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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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
}
