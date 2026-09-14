package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class PartyIdentifierSerializer implements XmlSerializer<PartyIdentifier> {

    @Override
    public void serialize(
            XmlWriter writer,
            PartyIdentifier identifier) {

        if (identifier == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "PartyIdentification"
        );

        writer.startElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "ID"
        );

        writer.writeAttribute(
                "schemeID",
                identifier.getScheme().getCode()
        );

        writer.writeCharacters(identifier.getValue());

        writer.endElement();
        writer.endElement();
    }
}