package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Seller;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class SellerSerializer implements XmlSerializer<Seller> {

    private final PartyIdentifierSerializer partyIdentifierSerializer = new PartyIdentifierSerializer();

    private final ElectronicAddressSerializer electronicAddressSerializer =
            new ElectronicAddressSerializer();

    private final AddressSerializer addressSerializer = new AddressSerializer();

    private final ContactSerializer contactSerializer = new ContactSerializer();

    private final TaxIdentifierSerializer taxIdentifierSerializer =
            new TaxIdentifierSerializer();

    @Override
    public void serialize(XmlWriter writer, Seller seller) {
        if (seller == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "AccountingSupplierParty"
        );

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "Party"
        );

        writeEndpoint(writer, seller);

        writePartyIdentifiers(writer, seller);

        writePartyName(writer, seller);

        writeAddress(writer, seller);

        writeVatIdentifier(writer, seller);

        writeLegalEntity(writer, seller);

        writeContact(writer, seller);

        writer.endElement(); // Party

        writer.endElement(); // AccountingSupplierParty
    }

    /**
     * BT-34
     *
     * Seller electronic address.
     */
    private void writeEndpoint(
            XmlWriter writer,
            Seller seller) {

        if (seller.getElectronicAddress() == null) {
            return;
        }

        electronicAddressSerializer.serialize(writer, seller.getElectronicAddress());
    }

    /**
     * BG-4 / BT-29
     *
     * Seller identifiers.
     */
    private void writePartyIdentifiers(
            XmlWriter writer,
            Seller seller) {

        if (seller.getIdentifiers() == null) {
            return;
        }

        for (PartyIdentifier identifier : seller.getIdentifiers()) {
            if (identifier == null) {
                continue;
            }

            partyIdentifierSerializer.serialize(writer, identifier);
        }
    }

    /**
     * BT-27
     *
     * Seller name.
     */
    private void writePartyName(
            XmlWriter writer,
            Seller seller) {

        if (seller.getName() == null || seller.getName().isBlank()) {
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
                seller.getName()
        );

        writer.endElement();
    }

    /**
     * BG-5
     *
     * Seller postal address.
     */
    private void writeAddress(XmlWriter writer, Seller seller) {
        if (seller.getAddress() == null) {
            return;
        }

        addressSerializer.serialize(writer, seller.getAddress());
    }

    /**
     * BT-31
     *
     * Seller VAT identifier.
     */
    private void writeVatIdentifier(XmlWriter writer, Seller seller) {
        if (seller.getVatIdentifier() == null) {
            return;
        }

        /*
         * BT-31
         */
        taxIdentifierSerializer.serialize(writer, seller.getVatIdentifier());
    }

    /**
     * BT-30
     *
     * Seller legal registration identifier.
     */
    private void writeLegalEntity(
            XmlWriter writer,
            Seller seller) {

        if (seller.getLegalRegistrationIdentifier() == null
                && seller.getLegalInformation() == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "PartyLegalEntity"
        );

        /*
         * Seller legal name / registration name.
         *
         * The exact legal-information getter will be added
         * once the LegalInformation model is mapped.
         */
        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "CompanyID",
                seller.getLegalRegistrationIdentifier()
        );

        writer.endElement();
    }

    /**
     * BG-6
     *
     * Seller contact information.
     */
    private void writeContact(XmlWriter writer, Seller seller) {
        if (seller.getContact() == null) {
            return;
        }

        contactSerializer.serialize(writer, seller.getContact());
    }
}