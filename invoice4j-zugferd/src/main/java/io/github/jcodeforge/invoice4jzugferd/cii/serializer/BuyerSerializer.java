package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Buyer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;

public final class BuyerSerializer implements XmlSerializer<Buyer> {

    private final PartyIdentifierSerializer partyIdentifierSerializer = new PartyIdentifierSerializer();

    private final ElectronicAddressSerializer electronicAddressSerializer = new ElectronicAddressSerializer();

    private final AddressSerializer addressSerializer = new AddressSerializer();

    private final ContactSerializer contactSerializer = new ContactSerializer();

    private final TaxIdentifierSerializer taxIdentifierSerializer = new TaxIdentifierSerializer();

    @Override
    public void serialize(XmlWriter writer, Buyer buyer) {
        if (buyer == null) {
            return;
        }

        // BT-45
        for (PartyIdentifier identifier : buyer.getIdentifiers()) {
            partyIdentifierSerializer.serialize(writer, identifier);
        }

        writer.startElement(XmlNamespaces.RAM, "BuyerTradeParty");
        // BT-44
        writer.writeElement(XmlNamespaces.RAM, "Name", buyer.getName());
        writer.writeOptionalElement(XmlNamespaces.RAM, "Description", buyer.getTradingName());

        // BT-47
        if (buyer.getLegalRegistrationIdentifier() != null) {
            writer.startElement(XmlNamespaces.RAM, "SpecifiedLegalOrganization");
            writer.writeElement(XmlNamespaces.RAM, "ID",buyer.getLegalRegistrationIdentifier());
            writer.endElement();
        }

        // BT-48
        taxIdentifierSerializer.serialize(writer, buyer.getVatIdentifier());

        contactSerializer.serialize(writer, buyer.getContact());
        addressSerializer.serialize(writer, buyer.getAddress());
        electronicAddressSerializer.serialize(writer, buyer.getElectronicAddress());

        writer.endElement();
    }
}