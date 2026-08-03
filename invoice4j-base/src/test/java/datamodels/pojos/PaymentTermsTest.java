package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PaymentTerms;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.*;

public class PaymentTermsTest {

    @Test
    public void shouldCreatePaymentTerms() {
        PaymentTerms paymentTerms = CalculationUtils.createPaymentTerms();

        assertEquals("Payment due within 30 days.", paymentTerms.getDescription());
        assertEquals(LocalDate.of(2026, 1, 31), paymentTerms.getDueDate());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankDescription() {
        PaymentTerms.builder()
                .description("   ")
                .build();
    }

    @Test
    public void shouldAllowNullDescription() {
        PaymentTerms paymentTerms = PaymentTerms.builder()
                .dueDate(LocalDate.of(2026, 1, 31))
                .build();

        assertNull(paymentTerms.getDescription());
    }

    @Test
    public void shouldAllowNullDueDate() {
        PaymentTerms paymentTerms = PaymentTerms.builder()
                .description("Payment due within 30 days.")
                .build();

        assertNull(paymentTerms.getDueDate());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectEmptyPaymentTerms() {
        PaymentTerms.builder()
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingPaymentInstructions() {
        PaymentTerms.builder()
                .build();
    }

    @Test
    public void shouldAllowOnlyDescription() {
        PaymentTerms paymentTerms = PaymentTerms.builder()
                .description("Payment due within 30 days.")
                .build();

        assertEquals("Payment due within 30 days.", paymentTerms.getDescription());
        assertNull(paymentTerms.getDueDate());
    }

    @Test
    public void shouldAllowOnlyDueDate() {
        LocalDate dueDate = LocalDate.of(2026, 1, 31);

        PaymentTerms paymentTerms = PaymentTerms.builder()
                .dueDate(dueDate)
                .build();

        assertEquals(dueDate, paymentTerms.getDueDate());
        assertNull(paymentTerms.getDescription());
    }

    @Test
    public void shouldAllowOnlyDiscount() {
        PaymentTerms paymentTerms = PaymentTerms.builder()
                .discount(CalculationUtils.createPaymentDiscount())
                .build();

        assertNotNull(paymentTerms.getDiscount());
        assertNull(paymentTerms.getDueDate());
        assertNull(paymentTerms.getDescription());
    }

    @Test
    public void shouldAllowDescriptionAndDueDate() {
        LocalDate dueDate = LocalDate.of(2026, 1, 31);

        PaymentTerms paymentTerms = PaymentTerms.builder()
                .description("Payment due within 30 days.")
                .dueDate(dueDate)
                .build();

        assertEquals("Payment due within 30 days.", paymentTerms.getDescription());
        assertEquals(dueDate, paymentTerms.getDueDate());
    }

    @Test
    public void shouldAllowAllPaymentInstructions() {
        PaymentTerms paymentTerms = PaymentTerms.builder()
                .description("Payment due within 30 days.")
                .dueDate(LocalDate.of(2026, 1, 31))
                .discount(CalculationUtils.createPaymentDiscount())
                .build();

        assertNotNull(paymentTerms.getDiscount());
        assertEquals("Payment due within 30 days.", paymentTerms.getDescription());
        assertEquals(LocalDate.of(2026, 1, 31), paymentTerms.getDueDate());
    }
}