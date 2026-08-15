package zugferd;

import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetarySummation;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import io.github.jcodeforge.invoice4jzugferd.validation.BrCo15Validator;
import io.github.jcodeforge.invoice4jzugferd.validation.ZugferdBasicValidator;
import org.junit.Test;

import java.math.BigDecimal;

public class ZugferdBasicValidatorTest {

    private final ZugferdBasicValidator SUT = new ZugferdBasicValidator();

    @Test
    public void shouldValidateMinimalInvoice() {
        Invoice invoice = TestInvoiceFactory.createMinimalInvoice();
        SUT.validate(invoice);
    }

    @Test
    public void shouldValidateCompleteInvoice() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());
        SUT.validate(invoice);
    }

    @Test
    public void shouldValidateBrCo15() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        new BrCo15Validator().validate(invoice);
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectInvalidBrCo15() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        MonetarySummation invalidSummation = MonetarySummation.builder()
                .taxExclusiveAmount(invoice.getMonetarySummation().getTaxExclusiveAmount())
                .taxAmount(invoice.getMonetarySummation().getTaxAmount())
                .taxInclusiveAmount(
                        MonetaryAmount.builder()
                                .amount(new BigDecimal("215.00"))
                                .currency(CurrencyCode.EUR)
                                .build()
                )
                .build();

        Invoice invalid = Invoice.builder().from(invoice).monetarySummation(invalidSummation).build();


        new BrCo15Validator().validate(invalid);
    }
}
