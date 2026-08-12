package zugferd;

import factory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.exceptions.XsdValidationException;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdInvoiceWriter;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdProfile;
import io.github.jcodeforge.invoice4jzugferd.zugferd.validation.ZugferdExtendedXsdValidator;
import org.junit.Test;

public class ZugferdExtendedXsdValidatorTest {

    private final ZugferdExtendedXsdValidator SUT = new ZugferdExtendedXsdValidator();

    @Test
    public void shouldValidateExtendedInvoice() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());

        String xml = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.EXTENDED)
                .build()
                .writeToString(invoice);

        SUT.validate(xml);
    }

    @Test(expected = XsdValidationException.class)
    public void shouldRejectInvalidExtendedXml() {
        String invalidXml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<rsm:CrossIndustryInvoice " +
                        "xmlns:rsm=\"urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100\">" +
                        "</rsm:CrossIndustryInvoice>";

        SUT.validate(invalidXml);
    }

    @Test
    public void shouldValidateCompleteExtendedInvoice() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.EXTENDED)
                .build()
                .writeToString(invoice);

        SUT.validate(xml);
    }
}
