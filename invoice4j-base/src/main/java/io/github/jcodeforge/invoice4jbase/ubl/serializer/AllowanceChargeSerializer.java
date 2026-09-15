package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.AllowanceCharge;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class AllowanceChargeSerializer implements XmlSerializer<AllowanceCharge> {

    @Override
    public void serialize(XmlWriter writer, AllowanceCharge allowanceCharge) {
        if (allowanceCharge == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "AllowanceCharge"
        );

        // ChargeIndicator
        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "ChargeIndicator",
                Boolean.toString(allowanceCharge.isCharge())
        );

        // AllowanceChargeReasonCode
        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "AllowanceChargeReasonCode",
                allowanceCharge.getReasonCode()
        );

        // AllowanceChargeReason
        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "AllowanceChargeReason",
                allowanceCharge.getReason()
        );

        // MultiplierFactorNumeric
        if (allowanceCharge.getPercentage() != null) {
            writer.writeElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "MultiplierFactorNumeric",
                    allowanceCharge.getPercentage().toPlainString()
            );
        }

        // Amount
        writer.startElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "Amount"
        );

        writer.writeAttribute(
                "currencyID",
                allowanceCharge.getAmount()
                        .getCurrency()
                        .getCode()
        );

        writer.writeCharacters(
                allowanceCharge.getAmount()
                        .getAmount()
                        .toPlainString()
        );

        writer.endElement();

        // BaseAmount
        if (allowanceCharge.getBaseAmount() != null) {
            writer.startElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "BaseAmount"
            );

            writer.writeAttribute(
                    "currencyID",
                    allowanceCharge.getBaseAmount()
                            .getCurrency()
                            .getCode()
            );

            writer.writeCharacters(
                    allowanceCharge.getBaseAmount()
                            .getAmount()
                            .toPlainString()
            );

            writer.endElement();
        }

        // TaxCategory
        if (allowanceCharge.getTaxCategory() != null) {
            writer.startElement(
                    "cac",
                    XmlNamespaces.UBL_CAC,
                    "TaxCategory"
            );

            writer.writeElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "ID",
                    allowanceCharge.getTaxCategory().getCode()
            );

            if (allowanceCharge.getTaxRate() != null) {
                writer.writeElement(
                        "cbc",
                        XmlNamespaces.UBL_CBC,
                        "Percent",
                        allowanceCharge.getTaxRate().toPlainString()
                );
            }

            // TaxScheme - required by UBL TaxCategory
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
        }

        writer.endElement(); // AllowanceCharge
    }
}