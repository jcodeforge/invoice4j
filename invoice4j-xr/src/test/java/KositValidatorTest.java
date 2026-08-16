import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jxr.XrCiiInvoiceWriter;
import io.github.jcodeforge.invoice4jxr.validation.KositValidator;
import io.github.jcodeforge.invoice4jxr.validation.ValidationResult;
import org.junit.Test;

import static org.junit.Assert.*;

public class KositValidatorTest {

    private final KositValidator SUT = new KositValidator();

    @Test
    public void shouldValidateXRechnungCii() throws Exception {
        String xml = XrCiiInvoiceWriter.builder()
                .validate(false)
                .prettyPrint(true)
                .build()
                .writeToString(new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice()));

        ValidationResult result = SUT.validate(xml);

        assertNotNull(result);
    }

    @Test
    public void shouldAcceptValidXRechnungCii() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = XrCiiInvoiceWriter.builder()
                .validate(false)
                .build()
                .writeToString(invoice);

        ValidationResult result = SUT.validate(xml);

        System.out.println("valid = " + result.isValid());

        result.getMessages().forEach(message ->
                System.out.println(
                        message.getSeverity()
                                + ": "
                                + message.getMessage()
                                + " | rule="
                                + message.getRuleId()
                                + " | location="
                                + message.getLocation()
                )
        );

        assertTrue(result.isValid());
    }
}
