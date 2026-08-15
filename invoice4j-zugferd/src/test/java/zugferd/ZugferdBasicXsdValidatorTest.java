package zugferd;

import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.exceptions.XsdValidationException;
import io.github.jcodeforge.invoice4jzugferd.zugferd.validation.ZugferdBasicXsdValidator;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdInvoiceWriter;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdProfile;
import org.junit.Test;

public class ZugferdBasicXsdValidatorTest {

    private final ZugferdBasicXsdValidator SUT = new ZugferdBasicXsdValidator();

    @Test
    public void shouldValidateBasicInvoice() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());

        String xml = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.BASIC)
                .build()
                .writeToString(invoice);

        SUT.validate(xml);
    }

    @Test(expected = XsdValidationException.class)
    public void shouldRejectInvalidBasicXml() {
        String invalidXml = "...";
        SUT.validate(invalidXml);
    }

    @Test
    public void shouldValidateCompleteBasicInvoice() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.BASIC)
                .build()
                .writeToString(invoice);

        SUT.validate(xml);
    }
}
