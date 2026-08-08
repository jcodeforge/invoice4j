package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetarySummation;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;

public final class MonetarySummationSerializer implements XmlSerializer<MonetarySummation> {

    @Override
    public void serialize(XmlWriter writer, MonetarySummation monetarySummation) {
        if (monetarySummation == null) {
            return;
        }

        writer.startElement(XmlNamespaces.RAM, "SpecifiedTradeSettlementHeaderMonetarySummation");
        writer.writeElement(XmlNamespaces.RAM, "LineTotalAmount", monetarySummation.getLineExtensionAmount()
                .getAmount().toPlainString());
        writer.writeElement(XmlNamespaces.RAM, "ChargeTotalAmount", monetarySummation.getChargeTotalAmount()
                .getAmount().toPlainString());
        writer.writeElement(XmlNamespaces.RAM, "AllowanceTotalAmount", monetarySummation.getAllowanceTotalAmount()
                .getAmount().toPlainString());
        writer.writeElement(XmlNamespaces.RAM, "TaxBasisTotalAmount", monetarySummation.getTaxExclusiveAmount()
                .getAmount().toPlainString());
        writer.writeElement(XmlNamespaces.RAM, "TaxTotalAmount", monetarySummation.getTaxAmount().getAmount()
                .toPlainString());

        if (monetarySummation.getRoundingAmount() != null) {
            writer.writeElement(XmlNamespaces.RAM, "RoundingAmount", monetarySummation.getRoundingAmount()
                    .getAmount().toPlainString());
        }

        writer.writeElement(XmlNamespaces.RAM, "GrandTotalAmount", monetarySummation.getTaxInclusiveAmount()
                .getAmount().toPlainString());
        writer.writeElement(XmlNamespaces.RAM, "TotalPrepaidAmount", monetarySummation.getPrepaidAmount()
                .getAmount().toPlainString());
        writer.writeElement(XmlNamespaces.RAM, "DuePayableAmount", monetarySummation.getPayableAmount()
                .getAmount().toPlainString());

        writer.endElement();
    }
}