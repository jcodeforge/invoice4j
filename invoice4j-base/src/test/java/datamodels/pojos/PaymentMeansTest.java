package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.PaymentMeansCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PaymentMeans;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import org.junit.Test;

import static org.junit.Assert.*;

public class PaymentMeansTest {

    @Test
    public void shouldCreateCreditTransferPayment() {
        PaymentMeans payment = CalculationUtils.createPaymentMeans();

        assertEquals(PaymentMeansCode.CREDIT_TRANSFER, payment.getMeansCode());
        assertNotNull(payment.getBankAccount());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingMeansCode() {
        PaymentMeans.builder().build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankMeansDescription() {
        PaymentMeans.builder()
                .meansCode(PaymentMeansCode.CASH)
                .meansDescription("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankRemittanceInformation() {
        PaymentMeans.builder()
                .meansCode(PaymentMeansCode.CASH)
                .remittanceInformation("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankAccountIdentifier() {
        PaymentMeans.builder()
                .meansCode(PaymentMeansCode.CASH)
                .accountIdentifier("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankAccountName() {
        PaymentMeans.builder()
                .meansCode(PaymentMeansCode.CASH)
                .accountName("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankServiceProviderIdentifier() {
        PaymentMeans.builder()
                .meansCode(PaymentMeansCode.CASH)
                .serviceProviderIdentifier("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectCreditTransferWithoutBankAccount() {
        PaymentMeans.builder()
                .meansCode(PaymentMeansCode.CREDIT_TRANSFER)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectSepaCreditTransferWithoutBankAccount() {
        PaymentMeans.builder()
                .meansCode(PaymentMeansCode.SEPA_CREDIT_TRANSFER)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectSepaDirectDebitWithoutBankAccount() {
        PaymentMeans.builder()
                .meansCode(PaymentMeansCode.SEPA_DIRECT_DEBIT)
                .mandateReference("MANDATE-1")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectSepaDirectDebitWithoutMandateReference() {
        PaymentMeans.builder()
                .meansCode(PaymentMeansCode.SEPA_DIRECT_DEBIT)
                .bankAccount(CalculationUtils.createBankAccount())
                .build();
    }

    @Test
    public void shouldCreateSepaDirectDebitPayment() {
        PaymentMeans payment = PaymentMeans.builder()
                .meansCode(PaymentMeansCode.SEPA_DIRECT_DEBIT)
                .bankAccount(CalculationUtils.createBankAccount())
                .mandateReference("MANDATE-1")
                .build();

        assertEquals(PaymentMeansCode.SEPA_DIRECT_DEBIT, payment.getMeansCode());
        assertEquals("MANDATE-1", payment.getMandateReference());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBankCardWithoutCardIdentifier() {
        PaymentMeans.builder()
                .meansCode(PaymentMeansCode.BANK_CARD)
                .build();
    }

    @Test
    public void shouldCreateBankCardPayment() {
        PaymentMeans payment = PaymentMeans.builder()
                .meansCode(PaymentMeansCode.BANK_CARD)
                .paymentCardIdentifier("****1234")
                .paymentCardHolderName("John Doe")
                .build();

        assertEquals(PaymentMeansCode.BANK_CARD, payment.getMeansCode());
        assertEquals("****1234", payment.getPaymentCardIdentifier());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBankCardWithBankAccount() {
        PaymentMeans.builder()
                .meansCode(PaymentMeansCode.BANK_CARD)
                .paymentCardIdentifier("****1234")
                .bankAccount(CalculationUtils.createBankAccount())
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectCashWithBankAccount() {
        PaymentMeans.builder()
                .meansCode(PaymentMeansCode.CASH)
                .bankAccount(CalculationUtils.createBankAccount())
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectCashWithPaymentCardInformation() {
        PaymentMeans.builder()
                .meansCode(PaymentMeansCode.CASH)
                .paymentCardIdentifier("****1234")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectCreditTransferWithTooLongRemittanceInformation() {
        String remittance = "A".repeat(141);

        PaymentMeans.builder()
                .meansCode(PaymentMeansCode.CREDIT_TRANSFER)
                .bankAccount(CalculationUtils.createBankAccount())
                .remittanceInformation(remittance)
                .build();
    }

    @Test
    public void shouldAllowMaximumLengthRemittanceInformation() {
        String remittance = "A".repeat(140);

        PaymentMeans payment = PaymentMeans.builder()
                .meansCode(PaymentMeansCode.CREDIT_TRANSFER)
                .bankAccount(CalculationUtils.createBankAccount())
                .remittanceInformation(remittance)
                .build();

        assertEquals(remittance, payment.getRemittanceInformation());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankPaymentCardIdentifier() {
        PaymentMeans.builder()
                .meansCode(PaymentMeansCode.BANK_CARD)
                .paymentCardIdentifier("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankPaymentCardHolderName() {
        PaymentMeans.builder()
                .meansCode(PaymentMeansCode.BANK_CARD)
                .paymentCardIdentifier("****1234")
                .paymentCardHolderName("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankMandateReference() {
        PaymentMeans.builder()
                .meansCode(PaymentMeansCode.SEPA_DIRECT_DEBIT)
                .bankAccount(CalculationUtils.createBankAccount())
                .mandateReference("   ")
                .build();
    }
}
