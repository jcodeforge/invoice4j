package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Payee;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;

public final class PayeeSerializer implements XmlSerializer<Payee> {

    private final PartyIdentifierSerializer partyIdentifierSerializer = new PartyIdentifierSerializer();

    private final ElectronicAddressSerializer electronicAddressSerializer = new ElectronicAddressSerializer();

    private final AddressSerializer addressSerializer = new AddressSerializer();

    private final ContactSerializer contactSerializer = new ContactSerializer();

    @Override
    public void serialize(XmlWriter writer, Payee payee) {
        if (payee == null) {
            return;
        }

        writer.startElement(XmlNamespaces.RAM, "PayeeTradeParty");
        // BT-59
        writer.writeElement(XmlNamespaces.RAM, "Name", payee.getName());
        writer.writeOptionalElement(XmlNamespaces.RAM, "Description", payee.getTradingName());
        // BT-60
        for (PartyIdentifier identifier : payee.getIdentifiers()) {
            partyIdentifierSerializer.serialize(writer, identifier);
        }

        electronicAddressSerializer.serialize(writer, payee.getElectronicAddress());
        addressSerializer.serialize(writer, payee.getAddress());
        contactSerializer.serialize(writer, payee.getContact());

        writer.endElement();
    }
}