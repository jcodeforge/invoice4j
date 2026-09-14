package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.ItemProperty;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class ItemPropertySerializer implements XmlSerializer<ItemProperty> {

    @Override
    public void serialize(XmlWriter writer, ItemProperty property) {
        if (property == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "AdditionalItemProperty"
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "Name",
                property.getName()
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "Value",
                property.getValue()
        );

        writer.endElement();
    }
}