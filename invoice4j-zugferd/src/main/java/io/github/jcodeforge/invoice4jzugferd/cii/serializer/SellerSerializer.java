package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Seller;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiConfigurationOptions;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiProfile;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;
import java.util.Objects;

public final class SellerSerializer implements XmlSerializer<Seller> {

    private final PartyIdentifierSerializer partyIdentifierSerializer;

    private final ElectronicAddressSerializer electronicAddressSerializer = new ElectronicAddressSerializer();

    private final AddressSerializer addressSerializer = new AddressSerializer();

    private final ContactSerializer contactSerializer = new ContactSerializer();

    private final TaxIdentifierSerializer taxIdentifierSerializer = new TaxIdentifierSerializer();

    private final CiiConfigurationOptions options;

    public SellerSerializer(CiiConfigurationOptions options) {
        this.options = Objects.requireNonNull(options, "options must not be null");
        this.partyIdentifierSerializer = new PartyIdentifierSerializer(options);
    }

    public void serialize(XmlWriter writer, Seller seller) {
        if (seller == null) {
            return;
        }

        System.out.println("Profile: " + options.getProfile());
        System.out.println("Contact: " + seller.getContact());

        writer.startElement(XmlNamespaces.RAM, "SellerTradeParty");

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


        // Address
        addressSerializer.serialize(
                writer,
                seller.getAddress()
        );

        // Electronic address
        electronicAddressSerializer.serialize(
                writer,
                seller.getElectronicAddress()
        );

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

        // Contact
        /*
        if (options.getProfile() != CiiProfile.ZUGFERD_BASIC) {
            contactSerializer.serialize(
                    writer,
                    seller.getContact()
            );
        }

         */

        writer.endElement();
    }
}