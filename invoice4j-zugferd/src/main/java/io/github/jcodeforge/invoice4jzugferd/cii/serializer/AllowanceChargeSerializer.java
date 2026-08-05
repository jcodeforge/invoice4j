package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.AllowanceCharge;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;

public class AllowanceChargeSerializer implements XmlSerializer<AllowanceCharge> {

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

        writer.writeOptionalElement(XmlNamespaces.RAM, "ReasonCode", allowanceCharge.getReasonCode());
        writer.writeOptionalElement(XmlNamespaces.RAM, "Reason", allowanceCharge.getReason());
        writer.writeElement(XmlNamespaces.RAM, "ActualAmount", allowanceCharge.getAmount().getAmount()
                .toPlainString());

        if (allowanceCharge.getBaseAmount() != null) {
            writer.writeElement(XmlNamespaces.RAM, "BasisAmount", allowanceCharge.getBaseAmount().getAmount()
                    .toPlainString());
        }

        if (allowanceCharge.getPercentage() != null) {
            writer.writeElement(XmlNamespaces.RAM, "CalculationPercent", allowanceCharge.getPercentage()
                    .toPlainString());
        }

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