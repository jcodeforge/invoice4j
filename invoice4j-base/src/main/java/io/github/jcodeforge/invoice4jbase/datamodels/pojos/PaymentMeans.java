package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.PaymentMeansCode;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;

/**
 * BG-16
 * Payment information.
 *
 * Contains payment instructions for settling the invoice.
 */
public class PaymentMeans {

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

    private PaymentMeans() {
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

        private final PaymentMeans paymentMeans;

        private Builder() {
            this.paymentMeans = new PaymentMeans();
        }

        public Builder meansCode(PaymentMeansCode meansCode) {
            paymentMeans.meansCode = meansCode;
            return this;
        }

        public Builder meansDescription(String meansDescription) {
            paymentMeans.meansDescription = meansDescription;
            return this;
        }

        public Builder remittanceInformation(String remittanceInformation) {
            paymentMeans.remittanceInformation = remittanceInformation;
            return this;
        }

        public Builder accountIdentifier(String accountIdentifier) {
            paymentMeans.accountIdentifier = accountIdentifier;
            return this;
        }

        public Builder accountName(String accountName) {
            paymentMeans.accountName = accountName;
            return this;
        }

        public Builder serviceProviderIdentifier(String serviceProviderIdentifier) {
            paymentMeans.serviceProviderIdentifier = serviceProviderIdentifier;
            return this;
        }

        public Builder paymentCardIdentifier(String paymentCardIdentifier) {
            paymentMeans.paymentCardIdentifier = paymentCardIdentifier;
            return this;
        }

        public Builder paymentCardHolderName(String paymentCardHolderName) {
            paymentMeans.paymentCardHolderName = paymentCardHolderName;
            return this;
        }

        public Builder mandateReference(String mandateReference) {
            paymentMeans.mandateReference = mandateReference;
            return this;
        }

        public Builder bankAccount(BankAccount bankAccount) {
            paymentMeans.bankAccount = bankAccount;
            return this;
        }

        public PaymentMeans build() {
            if (paymentMeans.meansCode == null) {
                throw new InvoiceValidationException("BT-81 Payment means type code is required.");
            }
            if (paymentMeans.meansDescription != null && paymentMeans.meansDescription.isBlank()) {
                throw new InvoiceValidationException("BT-82 Payment means description must not be blank.");
            }
            if (paymentMeans.remittanceInformation != null && paymentMeans.remittanceInformation.isBlank()) {
                throw new InvoiceValidationException("BT-83 Remittance information must not be blank.");
            }
            if (paymentMeans.accountIdentifier != null && paymentMeans.accountIdentifier.isBlank()) {
                throw new InvoiceValidationException("BT-84 Payment account identifier must not be blank.");
            }
            if (paymentMeans.accountName != null && paymentMeans.accountName.isBlank()) {
                throw new InvoiceValidationException("BT-85 Payment account name must not be blank.");
            }
            if (paymentMeans.serviceProviderIdentifier != null && paymentMeans.serviceProviderIdentifier.isBlank()) {
                throw new InvoiceValidationException("BT-86 Payment service provider identifier must not be blank.");
            }
            if (paymentMeans.paymentCardIdentifier != null && paymentMeans.paymentCardIdentifier.isBlank()) {
                throw new InvoiceValidationException("BT-87 Payment card identifier must not be blank.");
            }
            if (paymentMeans.paymentCardHolderName != null && paymentMeans.paymentCardHolderName.isBlank()) {
                throw new InvoiceValidationException("BT-88 Payment card holder name must not be blank.");
            }
            if (paymentMeans.mandateReference != null && paymentMeans.mandateReference.isBlank()) {
                throw new InvoiceValidationException("BT-89 Mandate reference must not be blank.");
            }
            // Credit transfer requires bank account
            if ((paymentMeans.meansCode == PaymentMeansCode.CREDIT_TRANSFER
                    || paymentMeans.meansCode == PaymentMeansCode.SEPA_CREDIT_TRANSFER) && paymentMeans.bankAccount == null) {
                throw new InvoiceValidationException("A bank account is required for credit transfer payment.");
            }
            // Direct debit requires bank account and mandate reference
            if (paymentMeans.meansCode == PaymentMeansCode.SEPA_DIRECT_DEBIT) {
                if (paymentMeans.bankAccount == null) {
                    throw new InvoiceValidationException("A bank account is required for SEPA direct debit.");
                }
                if (paymentMeans.mandateReference == null || paymentMeans.mandateReference.isBlank()) {
                    throw new InvoiceValidationException("BT-89 Mandate reference is required for SEPA direct debit.");
                }
            }
            // Card payment requires card identifier
            if (paymentMeans.meansCode == PaymentMeansCode.BANK_CARD) {
                if (paymentMeans.paymentCardIdentifier == null || paymentMeans.paymentCardIdentifier.isBlank()) {
                    throw new InvoiceValidationException("BT-87 Payment card identifier is required for card payments.");
                }
            }
            if (paymentMeans.meansCode == PaymentMeansCode.BANK_CARD && paymentMeans.bankAccount != null) {
                throw new InvoiceValidationException("Bank account must not be specified for bank card payments.");
            }
            if (paymentMeans.meansCode == PaymentMeansCode.CASH && paymentMeans.bankAccount != null) {
                throw new InvoiceValidationException("Bank account is not applicable for cash payments.");
            }
            if (paymentMeans.meansCode == PaymentMeansCode.CASH && paymentMeans.paymentCardIdentifier != null) {
                throw new InvoiceValidationException("Payment card information is not applicable for cash payments.");
            }
            if (paymentMeans.meansCode == PaymentMeansCode.CREDIT_TRANSFER && paymentMeans.remittanceInformation != null
                    && paymentMeans.remittanceInformation.length() > 140) {
                throw new InvoiceValidationException("BT-83 Remittance information exceeds maximum length.");
            }

            return paymentMeans;
        }
    }
}
