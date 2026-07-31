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


    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
