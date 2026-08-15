package zugferd;

import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.exceptions.XsdValidationException;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdInvoiceWriter;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdProfile;
import io.github.jcodeforge.invoice4jzugferd.zugferd.validation.ZugferdMinimumXsdValidator;
import org.junit.Test;

public class ZugferdMinimumXsdValidatorTest {

    private final ZugferdMinimumXsdValidator SUT = new ZugferdMinimumXsdValidator();

    @Test
    public void shouldValidateMinimumInvoice() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());

        String xml = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.MINIMUM)
                .build()
                .writeToString(invoice);

        SUT.validate(xml);
    }

    @Test(expected = XsdValidationException.class)
    public void shouldRejectInvalidMinimumXml() {
        String invalidXml = "...";
        SUT.validate(invalidXml);
    }

    @Test
    public void shouldValidateCompleteMinimumInvoice() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.MINIMUM)
                .build()
                .writeToString(invoice);

        SUT.validate(xml);
    }
}
