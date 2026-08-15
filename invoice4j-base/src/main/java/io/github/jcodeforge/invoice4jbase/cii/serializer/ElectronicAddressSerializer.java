package io.github.jcodeforge.invoice4jbase.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.ElectronicAddress;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class ElectronicAddressSerializer implements XmlSerializer<ElectronicAddress> {

    @Override
    public void serialize(XmlWriter writer, ElectronicAddress electronicAddress) {
        if (electronicAddress == null) {
            return;
        }

        writer.startElement(XmlNamespaces.RAM, "URIUniversalCommunication");
        writer.startElement(XmlNamespaces.RAM, "URIID");
        writer.writeAttribute("schemeID", electronicAddress.getScheme().getCode());
        writer.writeCharacters(electronicAddress.getValue());
        writer.endElement();
        writer.endElement();
    }
}