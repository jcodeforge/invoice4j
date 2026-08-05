package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.ElectronicAddress;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;

public class ElectronicAddressSerializer implements XmlSerializer<ElectronicAddress> {

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