package io.github.jcodeforge.invoice4jbase.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Payee;
import io.github.jcodeforge.invoice4jbase.cii.CiiConfigurationOptions;
import io.github.jcodeforge.invoice4jbase.cii.CiiProfile;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;
import java.util.Objects;

public final class PayeeSerializer implements XmlSerializer<Payee> {

    private final PartyIdentifierSerializer partyIdentifierSerializer;

    private final ElectronicAddressSerializer electronicAddressSerializer = new ElectronicAddressSerializer();

    private final AddressSerializer addressSerializer;

    private final CiiConfigurationOptions options;

    public PayeeSerializer(CiiConfigurationOptions options) {
        this.options = Objects.requireNonNull(options, "options must not be null");
        this.partyIdentifierSerializer = new PartyIdentifierSerializer(options);
        this.addressSerializer = new AddressSerializer(options);
    }

    @Override
    public void serialize(XmlWriter writer, Payee payee) {

        if (payee == null) {
            return;
        }

        writer.startElement(
                XmlNamespaces.RAM,
                "PayeeTradeParty"
        );

        // BT-60
        for (PartyIdentifier identifier : payee.getIdentifiers()) {
            partyIdentifierSerializer.serialize(
                    writer,
                    identifier
            );
        }

        // BT-59
        writer.writeElement(
                XmlNamespaces.RAM,
                "Name",
                payee.getName()
        );

        /*
         * Trading name is not supported by ZUGFeRD BASIC.
         */
        if (options.getProfile() != CiiProfile.ZUGFERD_BASIC
                && options.getProfile() != CiiProfile.XRECHNUNG) {
            writer.writeOptionalElement(
                    XmlNamespaces.RAM,
                    "Description",
                    payee.getTradingName()
            );
        }

        /*
         * Address
         */
        if (options.getProfile() != CiiProfile.XRECHNUNG) {
            addressSerializer.serialize(
                    writer,
                    payee.getAddress()
            );
        }

        /*
         * Electronic address
         */
        if (options.getProfile() != CiiProfile.XRECHNUNG) {
            electronicAddressSerializer.serialize(
                    writer,
                    payee.getElectronicAddress()
            );
        }

        writer.endElement();
    }
}