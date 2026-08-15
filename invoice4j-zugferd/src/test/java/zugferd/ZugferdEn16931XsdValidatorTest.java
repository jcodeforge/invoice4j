package zugferd;

import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.exceptions.XsdValidationException;
import io.github.jcodeforge.invoice4jzugferd.ZugferdInvoiceWriter;
import io.github.jcodeforge.invoice4jzugferd.ZugferdProfile;
import io.github.jcodeforge.invoice4jzugferd.validation.ZugferdEn16931XsdValidator;
import org.junit.Test;
import java.io.File;
import java.io.InputStream;

public class ZugferdEn16931XsdValidatorTest {

    private final ZugferdEn16931XsdValidator SUT = new ZugferdEn16931XsdValidator();

    @Test
    public void shouldValidateEn16931Invoice() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());

        String xml = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.EN16931)
                .build()
                .writeToString(invoice);

        SUT.validate(xml);
    }

    @Test
    public void shouldValidateCompleteEn16931Invoice() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.EN16931)
                .build()
                .writeToString(invoice);

        SUT.validate(xml);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullXml() {
        SUT.validate((String) null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullInputStream() {
        SUT.validate((InputStream) null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullFile() {
        SUT.validate((File) null);
    }

    @Test(expected = XsdValidationException.class)
    public void shouldRejectInvalidXml() {
        SUT.validate("<invalid>");
    }
}
