package io.github.jcodeforge.invoice4jzugferd.zugferd.validation;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import java.util.List;
import java.util.Objects;

public class ZugferdEn16931Validator {

    private final List<InvoiceBusinessRule> rules = List.of();

    public void validate(Invoice invoice) {
        Objects.requireNonNull(invoice, "invoice must not be null");

        for (InvoiceBusinessRule rule : rules) {
            rule.validate(invoice);
        }
    }
}
