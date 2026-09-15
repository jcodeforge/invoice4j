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

        // TaxableAmount
        writer.startElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "TaxableAmount"
        );
        writer.writeAttribute(
                "currencyID",
                tax.getTaxableAmount().getCurrency().getCode()
        );
        writer.writeCharacters(
                tax.getTaxableAmount().getAmount().toPlainString()
        );
        writer.endElement();

        // TaxAmount
        writer.startElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "TaxAmount"
        );
        writer.writeAttribute(
                "currencyID",
                tax.getTaxAmount().getCurrency().getCode()
        );
        writer.writeCharacters(
                tax.getTaxAmount().getAmount().toPlainString()
        );
        writer.endElement();

        // TaxCategory
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

        // TaxScheme
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