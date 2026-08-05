package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;

public class PartyIdentifierSerializer implements XmlSerializer<PartyIdentifier> {

    @Override
    public void serialize(XmlWriter writer, PartyIdentifier identifier) {
        if (identifier == null) {
            return;
        }

        String elementName = identifier.getScheme().isGlobalIdentifier() ? "GlobalID" : "ID";
        writer.startElement(XmlNamespaces.RAM, elementName);
        writer.writeAttribute("schemeID", identifier.getScheme().getCode());
        writer.writeCharacters(identifier.getValue());

        writer.endElement();
    }
}