package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.TaxIdentifier;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;

public class TaxIdentifierSerializer implements XmlSerializer<TaxIdentifier> {

    @Override
    public void serialize(XmlWriter writer, TaxIdentifier taxIdentifier) {
        if (taxIdentifier == null) {
            return;
        }

        writer.startElement(XmlNamespaces.RAM, "SpecifiedTaxRegistration");
        writer.startElement(XmlNamespaces.RAM, "ID");
        writer.writeAttribute("schemeID", taxIdentifier.getType().getCode());
        writer.writeCharacters(taxIdentifier.getValue());
        writer.endElement();

        writer.endElement();
    }
}