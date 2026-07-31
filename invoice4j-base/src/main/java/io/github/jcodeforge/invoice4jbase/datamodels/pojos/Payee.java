package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

public class Payee extends Party {

    /**
     * BT-60
     * Payee identifier.
     */
    private String identifier;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
