package io.github.jcodeforge.invoice4jzugferd.zugferd.validation;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;

public interface InvoiceBusinessRule {

    void validate(Invoice invoice);
}
