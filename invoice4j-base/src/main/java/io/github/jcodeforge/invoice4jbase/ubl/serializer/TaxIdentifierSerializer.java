package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.TaxIdentifier;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class TaxIdentifierSerializer implements XmlSerializer<TaxIdentifier> {

    @Override
    public void serialize(XmlWriter writer, TaxIdentifier taxIdentifier) {
        if (taxIdentifier == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "PartyTaxScheme"
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "CompanyID",
                taxIdentifier.getValue()
        );

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "TaxScheme"
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "ID",
                taxIdentifier.getType().getCode()
        );

        writer.endElement();
        writer.endElement();
    }
}