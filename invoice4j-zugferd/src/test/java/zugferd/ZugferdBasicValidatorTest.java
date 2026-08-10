package zugferd;

import factory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.exceptions.XsdValidationException;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdBasicXsdValidator;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdInvoiceWriter;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdProfile;
import org.junit.Test;

public class ZugferdBasicValidatorTest {

    private final ZugferdBasicXsdValidator SUT = new ZugferdBasicXsdValidator();

    @Test
    public void shouldValidateBasicInvoice() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

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
}
