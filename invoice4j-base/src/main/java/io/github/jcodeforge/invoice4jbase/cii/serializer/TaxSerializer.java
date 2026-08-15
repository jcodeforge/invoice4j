package io.github.jcodeforge.invoice4jbase.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Tax;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class TaxSerializer implements XmlSerializer<Tax> {

    @Override
    public void serialize(XmlWriter writer, Tax tax) {
        if (tax == null) {
            return;
        }

        writer.startElement(XmlNamespaces.RAM, "ApplicableTradeTax");
        writer.writeElement(XmlNamespaces.RAM, "CalculatedAmount", tax.getTaxAmount().getAmount().toPlainString());
        writer.writeElement(XmlNamespaces.RAM, "TypeCode", "VAT");
        writer.writeElement(XmlNamespaces.RAM, "BasisAmount", tax.getTaxableAmount().getAmount().toPlainString());
        writer.writeElement(XmlNamespaces.RAM, "CategoryCode", tax.getCategoryCode().getCode());
        writer.writeElement(XmlNamespaces.RAM, "RateApplicablePercent", tax.getRate().toPlainString());
        writer.writeOptionalElement(XmlNamespaces.RAM, "ExemptionReason", tax.getExemptionReason());
        writer.endElement();
    }
}