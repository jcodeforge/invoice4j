package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.ShipTo;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;

public class ShipToSerializer implements XmlSerializer<ShipTo> {

    private final PartyIdentifierSerializer partyIdentifierSerializer = new PartyIdentifierSerializer();

    private final ElectronicAddressSerializer electronicAddressSerializer = new ElectronicAddressSerializer();

    private final AddressSerializer addressSerializer = new AddressSerializer();

    private final ContactSerializer contactSerializer = new ContactSerializer();

    @Override
    public void serialize(XmlWriter writer, ShipTo shipTo) {
        if (shipTo == null) {
            return;
        }

        writer.startElement(XmlNamespaces.RAM, "ShipToTradeParty");
        // BT-70
        writer.writeElement(XmlNamespaces.RAM, "Name", shipTo.getName());
        writer.writeOptionalElement(XmlNamespaces.RAM, "Description", shipTo.getTradingName());

        // BT-71
        for (PartyIdentifier identifier : shipTo.getIdentifiers()) {
            partyIdentifierSerializer.serialize(writer, identifier);
        }

        electronicAddressSerializer.serialize(writer, shipTo.getElectronicAddress());
        addressSerializer.serialize(writer, shipTo.getAddress());
        contactSerializer.serialize(writer, shipTo.getContact());

        writer.endElement();
    }
}