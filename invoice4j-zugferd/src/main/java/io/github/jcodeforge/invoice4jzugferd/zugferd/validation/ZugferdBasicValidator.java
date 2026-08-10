package io.github.jcodeforge.invoice4jzugferd.zugferd.validation;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import java.util.List;
import java.util.Objects;

public final class ZugferdBasicValidator {

    private final List<InvoiceBusinessRule> rules = List.of(new BrCo26Validator(), new BrS2Validator(),
            new BrCo15Validator());

    public void validate(Invoice invoice) {
        Objects.requireNonNull(invoice, "invoice must not be null");

        for (InvoiceBusinessRule rule : rules) {
            rule.validate(invoice);
        }
    }
}
