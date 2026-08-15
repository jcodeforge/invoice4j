import io.github.jcodeforge.invoice4jbase.exceptions.XsdValidationException;
import io.github.jcodeforge.invoice4jbase.validation.Cii16BXsdValidator;
import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jxr.XrCiiInvoiceWriter;
import io.github.jcodeforge.invoice4jxr.XrProfile;
import org.junit.Test;

public class XrCii16BXsdValidatorTest {

    private final Cii16BXsdValidator SUT = new Cii16BXsdValidator();

    @Test
    public void shouldGenerateXsdValidXRechnungCii() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = XrCiiInvoiceWriter.builder()
                .profile(XrProfile.XRECHNUNG)
                .prettyPrint(true)
                .build()
                .writeToString(invoice);

        SUT.validate(xml);
    }

    @Test(expected = XsdValidationException.class)
    public void shouldRejectInvalidXRechnungCii() {
        String xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <rsm:CrossIndustryInvoice
                    xmlns:rsm="urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100"
                    xmlns:ram="urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100">

                <rsm:ExchangedDocumentContext>
                    <ram:GuidelineSpecifiedDocumentContextParameter>
                        <ram:ID>invalid</ram:ID>
                    </ram:GuidelineSpecifiedDocumentContextParameter>
                </rsm:ExchangedDocumentContext>

            </rsm:CrossIndustryInvoice>
            """;

        SUT.validate(xml);
    }
}
