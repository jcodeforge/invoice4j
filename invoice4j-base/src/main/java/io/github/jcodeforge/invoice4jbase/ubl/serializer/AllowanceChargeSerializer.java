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

        // true = charge, false = allowance
        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "ChargeIndicator",
                Boolean.toString(allowanceCharge.isCharge())
        );

        // Calculation percentage
        if (allowanceCharge.getPercentage() != null) {
            writer.writeElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "MultiplierFactorNumeric",
                    allowanceCharge.getPercentage().toPlainString()
            );
        }

        // Basis amount
        if (allowanceCharge.getBaseAmount() != null) {
            writer.writeElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "BaseAmount",
                    allowanceCharge.getBaseAmount()
                            .getAmount()
                            .toPlainString()
            );
        }

        // Actual amount
        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "Amount",
                allowanceCharge.getAmount()
                        .getAmount()
                        .toPlainString()
        );

        // Reason
        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "AllowanceChargeReason",
                allowanceCharge.getReason()
        );

        // Reason code
        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "AllowanceChargeReasonCode",
                allowanceCharge.getReasonCode()
        );

        // VAT category
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

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "Percent",
                allowanceCharge.getTaxRate().toPlainString()
        );

        writer.endElement(); // TaxCategory

        writer.endElement(); // AllowanceCharge
    }
}