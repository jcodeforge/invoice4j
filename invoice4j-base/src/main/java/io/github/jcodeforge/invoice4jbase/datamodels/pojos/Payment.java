package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.PaymentMeansCode;

/**
 * BG-16
 * Payment information.
 *
 * Contains payment instructions for settling the invoice.
 */
public class Payment {

    /**
     * BT-81
     * Payment means type code.
     *
     * Examples:
     * - 10 Cash
     * - 30 Credit transfer
     * - 48 Bank card
     * - 49 Direct debit
     * - 58 SEPA credit transfer
     */
    private PaymentMeansCode meansCode;

    /**
     * BT-82
     * Payment means textual description.
     */
    private String meansDescription;

    /**
     * BT-83
     * Remittance information.
     *
     * Reference or text that should be included
     * with the payment.
     */
    private String remittanceInformation;

    /**
     * BT-84
     * Payment account identifier.
     *
     * Usually IBAN.
     */
    private String accountIdentifier;

    /**
     * BT-85
     * Payment account name.
     */
    private String accountName;

    /**
     * BT-86
     * Payment service provider identifier.
     *
     * Usually BIC.
     */
    private String serviceProviderIdentifier;

    /**
     * BT-87
     * Payment card information.
     *
     * Used when payment is made by card.
     */
    private String paymentCardIdentifier;

    /**
     * BT-88
     * Payment card holder name.
     */
    private String paymentCardHolderName;

    /**
     * BT-89
     * Mandate reference.
     *
     * Used for direct debit payments.
     */
    private String mandateReference;

    /**
     * BG-18
     * Credit transfer payment account.
     */
    private BankAccount bankAccount;

    private Payment() {
    }

    public PaymentMeansCode getMeansCode() {
        return meansCode;
    }

    public String getMeansDescription() {
        return meansDescription;
    }

    public String getRemittanceInformation() {
        return remittanceInformation;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getServiceProviderIdentifier() {
        return serviceProviderIdentifier;
    }

    public String getPaymentCardIdentifier() {
        return paymentCardIdentifier;
    }

    public String getPaymentCardHolderName() {
        return paymentCardHolderName;
    }

    public String getMandateReference() {
        return mandateReference;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Payment payment;

        private Builder() {
            this.payment = new Payment();
        }

        public Builder meansCode(PaymentMeansCode meansCode) {
            payment.meansCode = meansCode;
            return this;
        }

        public Builder meansDescription(String meansDescription) {
            payment.meansDescription = meansDescription;
            return this;
        }

        public Builder remittanceInformation(String remittanceInformation) {
            payment.remittanceInformation = remittanceInformation;
            return this;
        }

        public Builder accountIdentifier(String accountIdentifier) {
            payment.accountIdentifier = accountIdentifier;
            return this;
        }

        public Builder accountName(String accountName) {
            payment.accountName = accountName;
            return this;
        }

        public Builder serviceProviderIdentifier(String serviceProviderIdentifier) {
            payment.serviceProviderIdentifier = serviceProviderIdentifier;
            return this;
        }

        public Builder paymentCardIdentifier(String paymentCardIdentifier) {
            payment.paymentCardIdentifier = paymentCardIdentifier;
            return this;
        }

        public Builder paymentCardHolderName(String paymentCardHolderName) {
            payment.paymentCardHolderName = paymentCardHolderName;
            return this;
        }

        public Builder mandateReference(String mandateReference) {
            payment.mandateReference = mandateReference;
            return this;
        }

        public Builder bankAccount(BankAccount bankAccount) {
            payment.bankAccount = bankAccount;
            return this;
        }

        public Payment build() {
            return payment;
        }
    }
}
