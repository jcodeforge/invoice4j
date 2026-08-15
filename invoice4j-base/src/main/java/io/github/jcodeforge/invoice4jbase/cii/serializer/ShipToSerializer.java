package io.github.jcodeforge.invoice4jbase.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.ShipTo;
import io.github.jcodeforge.invoice4jbase.cii.CiiConfigurationOptions;
import io.github.jcodeforge.invoice4jbase.cii.CiiProfile;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

import java.util.Objects;

public final class ShipToSerializer implements XmlSerializer<ShipTo> {

    private final PartyIdentifierSerializer partyIdentifierSerializer;

    private final ElectronicAddressSerializer electronicAddressSerializer =
            new ElectronicAddressSerializer();

    private final AddressSerializer addressSerializer;

    private final ContactSerializer contactSerializer =
            new ContactSerializer();

    private final CiiConfigurationOptions options;

    public ShipToSerializer(CiiConfigurationOptions options) {
        this.options = Objects.requireNonNull(options, "options must not be null");
        this.partyIdentifierSerializer = new PartyIdentifierSerializer(options);
        this.addressSerializer = new AddressSerializer(options);
    }

    @Override
    public void serialize(XmlWriter writer, ShipTo shipTo) {
        if (shipTo == null) {
            return;
        }

        writer.startElement(
                XmlNamespaces.RAM,
                "ShipToTradeParty"
        );

        // BT-70
        writer.writeElement(
                XmlNamespaces.RAM,
                "Name",
                shipTo.getName()
        );

        // Trading name is not supported by ZUGFeRD BASIC
        if (options.getProfile() != CiiProfile.ZUGFERD_BASIC) {
            writer.writeOptionalElement(
                    XmlNamespaces.RAM,
                    "Description",
                    shipTo.getTradingName()
            );
        }

        // BT-71
        for (PartyIdentifier identifier : shipTo.getIdentifiers()) {
            partyIdentifierSerializer.serialize(
                    writer,
                    identifier
            );
        }

        electronicAddressSerializer.serialize(
                writer,
                shipTo.getElectronicAddress()
        );

        addressSerializer.serialize(
                writer,
                shipTo.getAddress()
        );

        if (options.getProfile() != CiiProfile.ZUGFERD_BASIC) {
            contactSerializer.serialize(
                    writer,
                    shipTo.getContact()
            );
        }

        writer.endElement();
    }
}