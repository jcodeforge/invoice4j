package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Payee;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class PayeeSerializer implements XmlSerializer<Payee> {

    private final PartyIdentifierSerializer partyIdentifierSerializer =
            new PartyIdentifierSerializer();

    private final AddressSerializer addressSerializer =
            new AddressSerializer();

    private final ElectronicAddressSerializer electronicAddressSerializer =
            new ElectronicAddressSerializer();

    @Override
    public void serialize(XmlWriter writer, Payee payee) {
        if (payee == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "PayeeParty"
        );

        writePartyIdentifiers(writer, payee);

        writePartyName(writer, payee);

        writeAddress(writer, payee);

        writeEndpoint(writer, payee);

        writer.endElement();
    }

    /**
     * BT-60
     *
     * Payee identifiers.
     */
    private void writePartyIdentifiers(XmlWriter writer, Payee payee) {

        if (payee.getIdentifiers() == null) {
            return;
        }

        for (PartyIdentifier identifier : payee.getIdentifiers()) {
            if (identifier == null) {
                continue;
            }

            partyIdentifierSerializer.serialize(writer, identifier);
        }
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

    /**
     * Payee postal address.
     */
    private void writeAddress(XmlWriter writer, Payee payee) {
        if (payee.getAddress() == null) {
            return;
        }

        addressSerializer.serialize(writer, payee.getAddress());
    }

    /**
     * Payee electronic address.
     */
    private void writeEndpoint(XmlWriter writer, Payee payee) {
        if (payee.getElectronicAddress() == null) {
            return;
        }

        electronicAddressSerializer.serialize(writer, payee.getElectronicAddress());
    }
}