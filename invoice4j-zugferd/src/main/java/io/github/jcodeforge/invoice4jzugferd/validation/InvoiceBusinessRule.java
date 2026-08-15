package io.github.jcodeforge.invoice4jzugferd.validation;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;

public interface InvoiceBusinessRule {

    void validate(Invoice invoice);
}
