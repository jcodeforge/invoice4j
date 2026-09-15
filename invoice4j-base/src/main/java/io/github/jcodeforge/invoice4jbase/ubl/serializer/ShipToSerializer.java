package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.ShipTo;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class ShipToSerializer implements XmlSerializer<ShipTo> {

    private final PartyIdentifierSerializer partyIdentifierSerializer = new PartyIdentifierSerializer();

    private final DeliveryAddressSerializer addressSerializer = new DeliveryAddressSerializer();

    @Override
    public void serialize(XmlWriter writer, ShipTo shipTo) {
        if (shipTo == null) {
            return;
        }

        writeDeliveryLocation(writer, shipTo);

        writeDeliveryParty(writer, shipTo);
    }

    /**
     * BG-15
     *
     * Deliver-to postal address.
     */
    private void writeDeliveryLocation(XmlWriter writer, ShipTo shipTo) {
        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "DeliveryLocation"
        );

        addressSerializer.serialize(writer, shipTo.getAddress());

        writer.endElement();
    }

    /**
     * BG-13 / BT-70 / BT-71
     *
     * Deliver-to party.
     */
    private void writeDeliveryParty(XmlWriter writer, ShipTo shipTo) {
        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "DeliveryParty"
        );

        writePartyIdentifiers(writer, shipTo);
        writePartyName(writer, shipTo);

        writer.endElement();
    }

    /**
     * BT-71
     *
     * Deliver-to location identifier.
     */
    private void writePartyIdentifiers(XmlWriter writer, ShipTo shipTo) {
        if (shipTo.getIdentifiers() == null) {
            return;
        }

        for (PartyIdentifier identifier : shipTo.getIdentifiers()) {
            if (identifier == null) {
                continue;
            }

            partyIdentifierSerializer.serialize(writer, identifier);
        }
    }

    /**
     * BT-70
     *
     * Deliver-to party name.
     */
    private void writePartyName(XmlWriter writer, ShipTo shipTo) {
        if (shipTo.getName() == null || shipTo.getName().isBlank()) {
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
                shipTo.getName()
        );

        writer.endElement();
    }
}