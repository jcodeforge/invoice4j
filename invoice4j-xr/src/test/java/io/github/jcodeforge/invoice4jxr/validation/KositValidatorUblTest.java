package io.github.jcodeforge.invoice4jxr.validation;

import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jxr.XrUblInvoiceWriter;
import org.junit.Test;

import static org.junit.Assert.*;

public class KositValidatorUblTest {

    private final KositValidator SUT = new KositValidator();

    @Test
    public void shouldValidateXRechnungUbl() throws Exception {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = XrUblInvoiceWriter.builder()
                .validate(false)
                .prettyPrint(true)
                .build()
                .writeToString(invoice);

        ValidationResult result = SUT.validate(xml);

        assertNotNull(result);
    }

    @Test
    public void shouldAcceptValidXRechnungUbl() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = XrUblInvoiceWriter.builder()
                .validate(false)
                .build()
                .writeToString(invoice);

        ValidationResult result = SUT.validate(xml);

        assertTrue("UBL XRechnung should be valid: " + result, result.isValid());
    }
}