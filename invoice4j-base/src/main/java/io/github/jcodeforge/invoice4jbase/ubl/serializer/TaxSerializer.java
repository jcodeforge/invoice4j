package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Tax;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class TaxSerializer implements XmlSerializer<Tax> {

    @Override
    public void serialize(XmlWriter writer, Tax tax) {
        if (tax == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "TaxSubtotal"
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "TaxableAmount",
                tax.getTaxableAmount()
                        .getAmount()
                        .toPlainString()
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "TaxAmount",
                tax.getTaxAmount()
                        .getAmount()
                        .toPlainString()
        );

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "TaxCategory"
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "ID",
                tax.getCategoryCode().getCode()
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "Percent",
                tax.getRate().toPlainString()
        );

        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "TaxExemptionReason",
                tax.getExemptionReason()
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
                "VAT"
        );

        writer.endElement(); // TaxScheme
        writer.endElement(); // TaxCategory
        writer.endElement(); // TaxSubtotal
    }
}