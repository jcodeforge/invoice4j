package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Buyer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class BuyerSerializer implements XmlSerializer<Buyer> {

    private final PartyIdentifierSerializer partyIdentifierSerializer =
            new PartyIdentifierSerializer();

    private final ElectronicAddressSerializer electronicAddressSerializer =
            new ElectronicAddressSerializer();

    private final AddressSerializer addressSerializer =
            new AddressSerializer();

    private final TaxIdentifierSerializer taxIdentifierSerializer =
            new TaxIdentifierSerializer();

    @Override
    public void serialize(XmlWriter writer, Buyer buyer) {
        if (buyer == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "AccountingCustomerParty"
        );

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "Party"
        );

        writeEndpoint(writer, buyer);

        writePartyIdentifiers(writer, buyer);

        writePartyName(writer, buyer);

        writeAddress(writer, buyer);

        writeVatIdentifier(writer, buyer);

        writeLegalEntity(writer, buyer);

        writer.endElement(); // Party

        writer.endElement(); // AccountingCustomerParty
    }

    /**
     * BT-49
     *
     * Buyer electronic address.
     */
    private void writeEndpoint(
            XmlWriter writer,
            Buyer buyer) {

        if (buyer.getElectronicAddress() == null) {
            return;
        }

        electronicAddressSerializer.serialize(
                writer,
                buyer.getElectronicAddress()
        );
    }

    /**
     * BG-7 / BT-46
     *
     * Buyer identifiers.
     */
    private void writePartyIdentifiers(
            XmlWriter writer,
            Buyer buyer) {

        if (buyer.getIdentifiers() == null) {
            return;
        }

        for (PartyIdentifier identifier : buyer.getIdentifiers()) {
            if (identifier == null) {
                continue;
            }

            partyIdentifierSerializer.serialize(
                    writer,
                    identifier
            );
        }
    }

    /**
     * BT-45
     *
     * Buyer name.
     */
    private void writePartyName(
            XmlWriter writer,
            Buyer buyer) {

        if (buyer.getName() == null
                || buyer.getName().isBlank()) {
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
                buyer.getName()
        );

        writer.endElement();
    }

    /**
     * BG-8
     *
     * Buyer postal address.
     */
    private void writeAddress(
            XmlWriter writer,
            Buyer buyer) {

        if (buyer.getAddress() == null) {
            return;
        }

        addressSerializer.serialize(
                writer,
                buyer.getAddress()
        );
    }

    /**
     * BT-48
     *
     * Buyer VAT identifier.
     */
    private void writeVatIdentifier(
            XmlWriter writer,
            Buyer buyer) {

        if (buyer.getVatIdentifier() == null) {
            return;
        }

        taxIdentifierSerializer.serialize(
                writer,
                buyer.getVatIdentifier()
        );
    }

    /**
     * BT-47
     *
     * Buyer legal registration identifier.
     */
    private void writeLegalEntity(
            XmlWriter writer,
            Buyer buyer) {

        if (buyer.getLegalRegistrationIdentifier() == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "PartyLegalEntity"
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "CompanyID",
                buyer.getLegalRegistrationIdentifier()
        );

        writer.endElement();
    }
}