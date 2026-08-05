package factory;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.PaymentMeansCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.BankAccount;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PaymentMeans;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PaymentTerms;

import java.time.LocalDate;

public final class TestPaymentFactory {

    public static PaymentMeans createPaymentMeans() {
        return PaymentMeans.builder()
                .meansCode(PaymentMeansCode.CREDIT_TRANSFER)
                .meansDescription("SEPA Credit Transfer")
                .remittanceInformation("Invoice INV-2026-0001")
                .bankAccount(createBankAccount())
                .build();
    }

    public static BankAccount createBankAccount() {
        return BankAccount.builder()
                .iban("DE89370400440532013000")
                .bic("COBADEFFXXX")
                .accountName("JCodeForge GmbH")
                .build();
    }

    public static PaymentTerms createPaymentTerms() {
        return PaymentTerms.builder()
                .description("Payment due within 30 days.")
                .dueDate(LocalDate.of(2026, 1, 31))
                .build();
    }
}
