package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;

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
            if (iban == null) {
                bankAccount.iban = null;
                return this;
            }

            String normalized = iban .replaceAll("\\s+", "") .toUpperCase();
            bankAccount.iban = normalized.isBlank() ? null : normalized;

            return this;
        }

        public Builder bic(String bic) {
            if (bic == null) {
                bankAccount.bic = null;
                return this;
            }

            String normalized = bic.replaceAll("\\s+", "").trim().toUpperCase();
            bankAccount.bic = normalized.isBlank() ? null : normalized;

            return this;
        }

        public Builder accountName(String accountName) {
            if (accountName == null) {
                bankAccount.accountName = null;
                return this;
            }

            String normalized = accountName.trim();
            bankAccount.accountName = normalized.isBlank() ? null : normalized;

            return this;
        }

        public BankAccount build() {
            if (bankAccount.iban == null && bankAccount.bic == null) {
                throw new InvoiceValidationException("A bank account must contain at least an IBAN or a BIC.");
            }
            if (bankAccount.iban != null) {
                if (bankAccount.iban.isBlank()) {
                    throw new InvoiceValidationException("IBAN must not be blank.");
                }
                if (bankAccount.iban.length() < 15 || bankAccount.iban.length() > 34) {
                    throw new InvoiceValidationException("IBAN length must be between 15 and 34 characters.");
                }
                if (!bankAccount.iban.matches("[A-Z0-9]+")) {
                    throw new InvoiceValidationException("IBAN contains invalid characters.");
                }
            }
            if (bankAccount.accountName != null && bankAccount.accountName.isBlank()) {
                throw new InvoiceValidationException("Account holder name must not be blank.");
            }

            return bankAccount;
        }
    }
}
