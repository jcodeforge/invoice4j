package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Payee;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiConfigurationOptions;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiProfile;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;
import java.util.Objects;

public final class PayeeSerializer implements XmlSerializer<Payee> {

    private final PartyIdentifierSerializer partyIdentifierSerializer;

    private final ElectronicAddressSerializer electronicAddressSerializer = new ElectronicAddressSerializer();

    private final AddressSerializer addressSerializer = new AddressSerializer();

    private final CiiConfigurationOptions options;

    public PayeeSerializer(CiiConfigurationOptions options) {
        this.options = Objects.requireNonNull(options, "options must not be null");
        this.partyIdentifierSerializer = new PartyIdentifierSerializer(options);
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
        if (options.getProfile() != CiiProfile.ZUGFERD_BASIC) {

            writer.writeOptionalElement(
                    XmlNamespaces.RAM,
                    "Description",
                    payee.getTradingName()
            );
        }

        /*
         * Address
         */
        addressSerializer.serialize(
                writer,
                payee.getAddress()
        );

        /*
         * Electronic address
         */
        electronicAddressSerializer.serialize(
                writer,
                payee.getElectronicAddress()
        );

        writer.endElement();
    }
}