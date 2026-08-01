package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

/**
 * Bank account information used for payments.
 */
public class BankAccount {

    /**
     * IBAN.
     */
    private String iban;

    /**
     * BIC / SWIFT code.
     */
    private String bic;

    /**
     * Account holder name.
     */
    private String accountName;

    private BankAccount() {
    }

    public String getIban() {
        return iban;
    }

    public String getBic() {
        return bic;
    }

    public String getAccountName() {
        return accountName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final BankAccount bankAccount;

        private Builder() {
            this.bankAccount = new BankAccount();
        }

        public Builder iban(String iban) {
            bankAccount.iban = iban;
            return this;
        }

        public Builder bic(String bic) {
            bankAccount.bic = bic;
            return this;
        }

        public Builder accountName(String accountName) {
            bankAccount.accountName = accountName;
            return this;
        }

        public BankAccount build() {
            return bankAccount;
        }
    }
}
