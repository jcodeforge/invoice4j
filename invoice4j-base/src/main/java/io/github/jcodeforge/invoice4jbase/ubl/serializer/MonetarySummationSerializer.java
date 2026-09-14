package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetarySummation;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class MonetarySummationSerializer implements XmlSerializer<MonetarySummation> {

    @Override
    public void serialize(XmlWriter writer, MonetarySummation monetarySummation) {
        if (monetarySummation == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "LegalMonetaryTotal"
        );

        writeAmount(
                writer,
                "LineExtensionAmount",
                monetarySummation.getLineExtensionAmount()
        );

        writeAmount(
                writer,
                "AllowanceTotalAmount",
                monetarySummation.getAllowanceTotalAmount()
        );

        writeAmount(
                writer,
                "ChargeTotalAmount",
                monetarySummation.getChargeTotalAmount()
        );

        writeAmount(
                writer,
                "TaxExclusiveAmount",
                monetarySummation.getTaxExclusiveAmount()
        );

        writeAmount(
                writer,
                "TaxInclusiveAmount",
                monetarySummation.getTaxInclusiveAmount()
        );

        writeAmount(
                writer,
                "PrepaidAmount",
                monetarySummation.getPrepaidAmount()
        );

        writeAmount(
                writer,
                "PayableAmount",
                monetarySummation.getPayableAmount()
        );

        writer.endElement(); // LegalMonetaryTotal
    }

    private void writeAmount(XmlWriter writer, String elementName, MonetaryAmount amount) {
        if (amount == null) {
            return;
        }

        writer.startElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                elementName
        );

        if (amount.getCurrency() != null) {
            writer.writeAttribute(
                    "currencyID",
                    amount.getCurrency().getCode()
            );
        }

        writer.writeCharacters(amount.getAmount().toPlainString());

        writer.endElement();
    }
}