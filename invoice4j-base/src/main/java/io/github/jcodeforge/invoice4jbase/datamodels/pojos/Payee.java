package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

/**
 * Payee party.
 *
 * Represents the party receiving payment when different
 * from the seller.
 */
public class Payee extends Party {

    /**
     * BT-60
     *
     * Payee identifier.
     */
    private PartyIdentifier identifier;

    public PartyIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(PartyIdentifier identifier) {
        this.identifier = identifier;
    }
}
