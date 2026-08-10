package zugferd;

import factory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiInvoiceWriter;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiProfile;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdBasicXsdValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZugferdBasicValidatorTest {

    private final ZugferdBasicXsdValidator SUT = new ZugferdBasicXsdValidator();

    @Test
    public void shouldValidateBasicInvoice() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = CiiInvoiceWriter.builder()
                .profile(CiiProfile.ZUGFERD_BASIC)
                .build()
                .writeToString(invoice);

        SUT.validate(xml);
    }

    @Test
    public void shouldNotSerializeUnsupportedBasicElements() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = CiiInvoiceWriter.builder()
                .profile(CiiProfile.ZUGFERD_BASIC)
                .build()
                .writeToString(invoice);

        assertFalse(xml.contains("PayeeTradeParty"));
        assertFalse(xml.contains("SellerOrderReferencedDocument"));
        assertFalse(xml.contains("AdditionalReferencedDocument"));
        assertFalse(xml.contains("SpecifiedProcuringProject"));
    }
}
