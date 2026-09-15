package validation;

import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.exceptions.XsdValidationException;
import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jxr.XrUblInvoiceWriter;
import io.github.jcodeforge.invoice4jxr.validation.Ubl21XsdValidator;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class XrUbl21XsdValidatorTest {

    private final Ubl21XsdValidator SUT = new Ubl21XsdValidator();

    @Test
    public void shouldValidateAgainstUbl21Schema() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = XrUblInvoiceWriter.builder()
                .validate(false)
                .build()
                .writeToString(invoice);

        SUT.validate(xml);
    }

    @Test(expected = XsdValidationException.class)
    public void shouldRejectInvalidXml() {
        String xml = "<invalid/>";

        InputStream in = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));

        SUT.validate(in);
    }

    @Test
    public void shouldValidateFromString() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());

        String xml = XrUblInvoiceWriter.builder()
                .validate(false)
                .build()
                .writeToString(invoice);

        SUT.validate(xml);
    }

    @Test
    public void shouldValidateFromInputStream() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());

        String xml = XrUblInvoiceWriter.builder()
                .validate(false)
                .build()
                .writeToString(invoice);

        InputStream in = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));

        SUT.validate(in);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullFile() {
        SUT.validate((File) null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullInputStream() {
        SUT.validate((InputStream) null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullString() {
        SUT.validate((String) null);
    }
}
