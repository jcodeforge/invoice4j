package validation.genericode;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jxr.validation.genericode.XrGenericodeValidator;
import org.junit.Test;

public class XrGenericodeValidatorTest {

    private final XrGenericodeValidator SUT =
            new XrGenericodeValidator();

    @Test
    public void shouldAcceptValidInvoice() {
        Invoice invoice = TestInvoiceFactory.createCompleteInvoice();

        SUT.validate(invoice);
    }

    @Test
    public void shouldAcceptEuroCurrency() {
        Invoice invoice = Invoice.builder()
                .from(TestInvoiceFactory.createCompleteInvoice())
                .currency(CurrencyCode.EUR)
                .build();

        SUT.validate(invoice);
    }

    @Test
    public void shouldAcceptUsdCurrency() {
        Invoice invoice = Invoice.builder()
                .from(TestInvoiceFactory.createCompleteInvoice())
                .currency(CurrencyCode.USD)
                .build();

        SUT.validate(invoice);
    }
}