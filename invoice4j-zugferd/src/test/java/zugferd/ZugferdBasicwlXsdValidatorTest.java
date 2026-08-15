package zugferd;

import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.exceptions.XsdValidationException;
import io.github.jcodeforge.invoice4jzugferd.ZugferdInvoiceWriter;
import io.github.jcodeforge.invoice4jzugferd.ZugferdProfile;
import io.github.jcodeforge.invoice4jzugferd.validation.ZugferdBasicwlXsdValidator;
import org.junit.Test;

public class ZugferdBasicwlXsdValidatorTest {

    private final ZugferdBasicwlXsdValidator SUT = new ZugferdBasicwlXsdValidator();

    @Test
    public void shouldValidateBasicwlInvoice() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());

        String xml = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.BASIC_WL)
                .build()
                .writeToString(invoice);

        System.out.println(xml);

        SUT.validate(xml);
    }

    @Test(expected = XsdValidationException.class)
    public void shouldRejectInvalidBasicwlXml() {
        String invalidXml = "...";
        SUT.validate(invalidXml);
    }

    @Test
    public void shouldValidateCompleteBasicwlInvoice() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.BASIC_WL)
                .build()
                .writeToString(invoice);

        SUT.validate(xml);
    }
}
