package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Payee;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Seller;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class PayeeSerializer implements XmlSerializer<Payee> {

    private final PartyIdentifierSerializer partyIdentifierSerializer =
            new PartyIdentifierSerializer();

    @Override
    public void serialize(XmlWriter writer, Payee payee) {
        serialize(writer, payee, null);
    }

    /**
     * Serializes the payee with optional seller context.
     *
     * <p>For XRechnung, a payee identifier that is identical to a seller
     * identifier must not be written when the payee is otherwise identified
     * by a different name.</p>
     *
     *  todo introduce serialization context for this in the future ??
     */
    public void serialize(XmlWriter writer, Payee payee, Seller seller) {
        if (payee == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "PayeeParty"
        );

        writePartyIdentifiers(writer, payee, seller);

        writePartyName(writer, payee);

        writer.endElement();
    }

    /**
     * BT-60
     *
     * Payee identifiers.
     */
    private void writePartyIdentifiers(XmlWriter writer, Payee payee, Seller seller) {
        if (payee.getIdentifiers() == null) {
            return;
        }

        for (PartyIdentifier identifier : payee.getIdentifiers()) {
            if (identifier == null) {
                continue;
            }

            if (isSameAsSellerIdentifier(identifier, seller)) {
                continue;
            }

            partyIdentifierSerializer.serialize(writer, identifier);
        }
    }

    private boolean isSameAsSellerIdentifier(PartyIdentifier payeeIdentifier, Seller seller) {
        if (seller == null || seller.getIdentifiers() == null) {
            return false;
        }

        for (PartyIdentifier sellerIdentifier : seller.getIdentifiers()) {
            if (sellerIdentifier == null) {
                continue;
            }

            if (isSameIdentifier(payeeIdentifier, sellerIdentifier)) {
                return true;
            }
        }

        return false;
    }

    private boolean isSameIdentifier(PartyIdentifier first, PartyIdentifier second) {
        return first.getValue().equals(second.getValue()) && first.getScheme() == second.getScheme();
    }

    /**
     * BT-59
     *
     * Payee name.
     */
    private void writePartyName(XmlWriter writer, Payee payee) {
        if (payee.getName() == null || payee.getName().isBlank()) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "PartyName"
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "Name",
                payee.getName()
        );

        writer.endElement();
    }
}