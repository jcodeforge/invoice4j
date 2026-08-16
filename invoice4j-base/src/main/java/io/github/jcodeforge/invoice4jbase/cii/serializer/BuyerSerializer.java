package io.github.jcodeforge.invoice4jbase.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Buyer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jbase.cii.CiiConfigurationOptions;
import io.github.jcodeforge.invoice4jbase.cii.CiiProfile;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;
import java.util.Objects;

public final class BuyerSerializer implements XmlSerializer<Buyer> {

    private final PartyIdentifierSerializer partyIdentifierSerializer;

    private final ElectronicAddressSerializer electronicAddressSerializer = new ElectronicAddressSerializer();

    private final AddressSerializer addressSerializer;

    private final TaxIdentifierSerializer taxIdentifierSerializer = new TaxIdentifierSerializer();

    private final CiiConfigurationOptions options;

    public BuyerSerializer(CiiConfigurationOptions options) {
        this.options = Objects.requireNonNull(options, "options must not be null");
        this.partyIdentifierSerializer = new PartyIdentifierSerializer(options);
        this.addressSerializer = new AddressSerializer(options);
    }

    @Override
    public void serialize(XmlWriter writer, Buyer buyer) {
        if (buyer == null) {
            return;
        }

        writer.startElement(XmlNamespaces.RAM, "BuyerTradeParty");

        if (options.getProfile() == CiiProfile.ZUGFERD_MINIMUM) {
            writer.writeElement(
                    XmlNamespaces.RAM,
                    "Name",
                    buyer.getName()
            );

            for (PartyIdentifier identifier : buyer.getIdentifiers()) {
                partyIdentifierSerializer.serialize(writer, identifier);
            }

        } else {
            // Existing order for the other profiles
            for (PartyIdentifier identifier : buyer.getIdentifiers()) {
                partyIdentifierSerializer.serialize(writer, identifier);
            }

            // BT-27
            writer.writeElement(
                    XmlNamespaces.RAM,
                    "Name",
                    buyer.getName()
            );
        }

        /*
         * ZUGFeRD BASIC does not allow Description
         * after Name in BuyerTradeParty.
         */
        if (options.getProfile() != CiiProfile.ZUGFERD_BASIC
                && options.getProfile() != CiiProfile.ZUGFERD_BASIC_WL
                && options.getProfile() != CiiProfile.ZUGFERD_MINIMUM
                && options.getProfile() != CiiProfile.XRECHNUNG) {

            writer.writeOptionalElement(XmlNamespaces.RAM, "Description", buyer.getTradingName());
        }

        // BT-47
        if (buyer.getLegalRegistrationIdentifier() != null) {
            writer.startElement(XmlNamespaces.RAM, "SpecifiedLegalOrganization");
            writer.writeElement(XmlNamespaces.RAM, "ID", buyer.getLegalRegistrationIdentifier());

            writer.endElement();
        }

        // BT-48
        taxIdentifierSerializer.serialize(writer, buyer.getVatIdentifier());

        addressSerializer.serialize(writer, buyer.getAddress());


        // Electronic address
        if (options.getProfile() != CiiProfile.ZUGFERD_MINIMUM) {
            electronicAddressSerializer.serialize(
                    writer,
                    buyer.getElectronicAddress()
            );
        }

        writer.endElement();
    }
}