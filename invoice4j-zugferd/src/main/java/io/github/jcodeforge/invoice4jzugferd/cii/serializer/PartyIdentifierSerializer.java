package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiConfigurationOptions;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiProfile;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;
import java.util.Objects;

public final class PartyIdentifierSerializer implements XmlSerializer<PartyIdentifier> {

    private final CiiConfigurationOptions options;

    public PartyIdentifierSerializer(CiiConfigurationOptions options) {
        this.options = Objects.requireNonNull(
                options,
                "options must not be null"
        );
    }

    @Override
    public void serialize(
            XmlWriter writer,
            PartyIdentifier identifier) {

        if (identifier == null) {
            return;
        }

        /*
         * ZUGFeRD BASIC does not support GlobalID
         * in the affected TradeParty structures.
         */
        if (options.getProfile() == CiiProfile.ZUGFERD_BASIC
                && identifier.getScheme().isGlobalIdentifier()) {
            return;
        }

        String elementName =
                identifier.getScheme().isGlobalIdentifier()
                        ? "GlobalID"
                        : "ID";

        writer.startElement(
                XmlNamespaces.RAM,
                elementName
        );

        writer.writeAttribute(
                "schemeID",
                identifier.getScheme().getCode()
        );

        writer.writeCharacters(
                identifier.getValue()
        );

        writer.endElement();
    }
}