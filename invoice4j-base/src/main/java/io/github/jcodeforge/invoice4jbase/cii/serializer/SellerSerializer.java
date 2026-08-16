package io.github.jcodeforge.invoice4jbase.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Seller;
import io.github.jcodeforge.invoice4jbase.cii.CiiConfigurationOptions;
import io.github.jcodeforge.invoice4jbase.cii.CiiProfile;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;
import java.util.Objects;

public final class SellerSerializer implements XmlSerializer<Seller> {

    private final PartyIdentifierSerializer partyIdentifierSerializer;

    private final ElectronicAddressSerializer electronicAddressSerializer = new ElectronicAddressSerializer();

    private final AddressSerializer addressSerializer;

    private final ContactSerializer contactSerializer = new ContactSerializer();

    private final TaxIdentifierSerializer taxIdentifierSerializer = new TaxIdentifierSerializer();

    private final CiiConfigurationOptions options;

    public SellerSerializer(CiiConfigurationOptions options) {
        this.options = Objects.requireNonNull(options, "options must not be null");
        this.partyIdentifierSerializer = new PartyIdentifierSerializer(options);
        this.addressSerializer = new AddressSerializer(options);
    }

    public void serialize(XmlWriter writer, Seller seller) {
        if (seller == null) {
            return;
        }

        writer.startElement(XmlNamespaces.RAM, "SellerTradeParty");

        if (options.getProfile() == CiiProfile.ZUGFERD_MINIMUM) {
            // MINIMUM: BT-27 must precede BT-29
            writer.writeElement(
                    XmlNamespaces.RAM,
                    "Name",
                    seller.getName()
            );

        } else {
            // Existing order for the other profiles
            // BT-29
            for (PartyIdentifier identifier : seller.getIdentifiers()) {
                partyIdentifierSerializer.serialize(writer, identifier);
            }

            // BT-27
            writer.writeElement(
                    XmlNamespaces.RAM,
                    "Name",
                    seller.getName()
            );
        }

        // BT-30
        if (seller.getLegalRegistrationIdentifier() != null) {
            writer.startElement(
                    XmlNamespaces.RAM,
                    "SpecifiedLegalOrganization"
            );

            writer.writeElement(
                    XmlNamespaces.RAM,
                    "ID",
                    seller.getLegalRegistrationIdentifier()
            );

            writer.endElement();
        }

        // Contact
        if (options.getProfile() == CiiProfile.XRECHNUNG) {
            contactSerializer.serialize(
                    writer,
                    seller.getContact()
            );
        }

        // Address
        addressSerializer.serialize(
                writer,
                seller.getAddress()
        );

        if (options.getProfile() != CiiProfile.ZUGFERD_MINIMUM) {
            // Electronic address
            electronicAddressSerializer.serialize(
                    writer,
                    seller.getElectronicAddress()
            );
        }

        // BT-31
        taxIdentifierSerializer.serialize(
                writer,
                seller.getVatIdentifier()
        );

        // BT-32
        if (seller.getTaxRegistrationIdentifier() != null) {
            writer.startElement(
                    XmlNamespaces.RAM,
                    "SpecifiedTaxRegistration"
            );

            writer.writeElement(
                    XmlNamespaces.RAM,
                    "ID",
                    seller.getTaxRegistrationIdentifier()
            );

            writer.endElement();
        }

        writer.endElement();
    }
}