package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.ElectronicAddress;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class ElectronicAddressSerializer implements XmlSerializer<ElectronicAddress> {

    @Override
    public void serialize(XmlWriter writer, ElectronicAddress electronicAddress) {
        if (electronicAddress == null) {
            return;
        }

        writer.startElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "EndpointID"
        );

        writer.writeAttribute(
                "schemeID",
                electronicAddress.getScheme().getCode()
        );

        writer.writeCharacters(
                electronicAddress.getValue()
        );

        writer.endElement();
    }
}