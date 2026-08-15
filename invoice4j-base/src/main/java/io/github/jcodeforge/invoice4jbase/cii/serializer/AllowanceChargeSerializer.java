package io.github.jcodeforge.invoice4jbase.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.AllowanceCharge;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class AllowanceChargeSerializer implements XmlSerializer<AllowanceCharge> {

    @Override
    public void serialize(XmlWriter writer, AllowanceCharge allowanceCharge) {
        if (allowanceCharge == null) {
            return;
        }

        writer.startElement(XmlNamespaces.RAM, "SpecifiedTradeAllowanceCharge");
        // true = charge, false = allowance
        writer.startElement(XmlNamespaces.RAM, "ChargeIndicator");
        writer.writeElement(XmlNamespaces.UDT, "Indicator", Boolean.toString(allowanceCharge.isCharge()));
        writer.endElement();

        if (allowanceCharge.getPercentage() != null) {
            writer.writeElement(XmlNamespaces.RAM, "CalculationPercent", allowanceCharge.getPercentage()
                    .toPlainString());
        }

        if (allowanceCharge.getBaseAmount() != null) {
            writer.writeElement(XmlNamespaces.RAM, "BasisAmount", allowanceCharge.getBaseAmount().getAmount()
                    .toPlainString());
        }

        writer.writeElement(XmlNamespaces.RAM, "ActualAmount", allowanceCharge.getAmount().getAmount()
                .toPlainString());
        writer.writeOptionalElement(XmlNamespaces.RAM, "Reason", allowanceCharge.getReason());
        writer.writeOptionalElement(XmlNamespaces.RAM, "ReasonCode", allowanceCharge.getReasonCode());

        // VAT information
        writer.startElement(XmlNamespaces.RAM, "CategoryTradeTax");
        writer.writeElement(XmlNamespaces.RAM, "TypeCode", "VAT");
        writer.writeElement(XmlNamespaces.RAM, "CategoryCode", allowanceCharge.getTaxCategory().getCode());
        writer.writeElement(XmlNamespaces.RAM, "RateApplicablePercent", allowanceCharge.getTaxRate()
                .toPlainString());
        writer.endElement();
        writer.endElement();
    }
}