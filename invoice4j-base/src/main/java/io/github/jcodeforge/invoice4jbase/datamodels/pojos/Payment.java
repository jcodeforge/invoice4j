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


    public PaymentMeansCode getMeansCode() {
        return meansCode;
    }

    public void setMeansCode(PaymentMeansCode meansCode) {
        this.meansCode = meansCode;
    }

    public String getMeansDescription() {
        return meansDescription;
    }

    public void setMeansDescription(String meansDescription) {
        this.meansDescription = meansDescription;
    }

    public String getRemittanceInformation() {
        return remittanceInformation;
    }

    public void setRemittanceInformation(String remittanceInformation) {
        this.remittanceInformation = remittanceInformation;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getServiceProviderIdentifier() {
        return serviceProviderIdentifier;
    }

    public void setServiceProviderIdentifier(String serviceProviderIdentifier) {
        this.serviceProviderIdentifier = serviceProviderIdentifier;
    }

    public String getPaymentCardIdentifier() {
        return paymentCardIdentifier;
    }

    public void setPaymentCardIdentifier(String paymentCardIdentifier) {
        this.paymentCardIdentifier = paymentCardIdentifier;
    }

    public String getPaymentCardHolderName() {
        return paymentCardHolderName;
    }

    public void setPaymentCardHolderName(String paymentCardHolderName) {
        this.paymentCardHolderName = paymentCardHolderName;
    }

    public String getMandateReference() {
        return mandateReference;
    }

    public void setMandateReference(String mandateReference) {
        this.mandateReference = mandateReference;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
