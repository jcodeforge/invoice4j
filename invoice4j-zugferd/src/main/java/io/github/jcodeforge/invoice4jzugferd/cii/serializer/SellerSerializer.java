package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Seller;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;

public final class SellerSerializer implements XmlSerializer<Seller> {

    private final PartyIdentifierSerializer partyIdentifierSerializer = new PartyIdentifierSerializer();

    private final ElectronicAddressSerializer electronicAddressSerializer = new ElectronicAddressSerializer();

    private final AddressSerializer addressSerializer = new AddressSerializer();

    private final ContactSerializer contactSerializer = new ContactSerializer();

    private final TaxIdentifierSerializer taxIdentifierSerializer = new TaxIdentifierSerializer();

    @Override
    public void serialize(XmlWriter writer, Seller seller) {
        if (seller == null) {
            return;
        }

        writer.startElement(XmlNamespaces.RAM, "SellerTradeParty");
        // BT-27
        writer.writeElement(XmlNamespaces.RAM, "Name", seller.getName());
        writer.writeOptionalElement(XmlNamespaces.RAM, "Description", seller.getTradingName());
        // BT-29
        for (PartyIdentifier identifier : seller.getIdentifiers()) {
            partyIdentifierSerializer.serialize(writer, identifier);
        }

        // BT-31
        taxIdentifierSerializer.serialize(writer, seller.getVatIdentifier());

        // BT-30
        if (seller.getLegalRegistrationIdentifier() != null) {
            writer.startElement(XmlNamespaces.RAM, "SpecifiedLegalOrganization");
            writer.writeElement(XmlNamespaces.RAM, "ID", seller.getLegalRegistrationIdentifier());
            writer.endElement();
        }

        // BT-32
        if (seller.getTaxRegistrationIdentifier() != null) {
            writer.startElement(XmlNamespaces.RAM, "SpecifiedTaxRegistration");
            writer.writeElement(XmlNamespaces.RAM, "ID", seller.getTaxRegistrationIdentifier());
            writer.endElement();
        }

        electronicAddressSerializer.serialize(writer, seller.getElectronicAddress());
        addressSerializer.serialize(writer, seller.getAddress());
        contactSerializer.serialize(writer, seller.getContact());

        // BT-33
        writer.writeOptionalElement(XmlNamespaces.RAM, "Description", seller.getLegalInformation());

        writer.endElement();
    }
}