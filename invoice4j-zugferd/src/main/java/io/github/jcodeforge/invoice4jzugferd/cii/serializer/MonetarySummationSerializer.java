package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetarySummation;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiConfigurationOptions;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiProfile;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;

import java.util.Objects;

public final class MonetarySummationSerializer implements XmlSerializer<MonetarySummation> {

    private final CiiConfigurationOptions options;

    public MonetarySummationSerializer(CiiConfigurationOptions options) {
        this.options = Objects.requireNonNull(options, "options must not be null");
    }

    @Override
    public void serialize(XmlWriter writer, MonetarySummation monetarySummation) {
        if (monetarySummation == null) {
            return;
        }

        writer.startElement(
                XmlNamespaces.RAM,
                "SpecifiedTradeSettlementHeaderMonetarySummation"
        );

        if (options.getProfile() == CiiProfile.ZUGFERD_MINIMUM) {
            writer.writeElement(
                    XmlNamespaces.RAM,
                    "TaxBasisTotalAmount",
                    monetarySummation
                            .getTaxExclusiveAmount()
                            .getAmount()
                            .toPlainString()
            );

            writer.startElement(
                    XmlNamespaces.RAM,
                    "TaxTotalAmount"
            );

            writer.writeAttribute(
                    "currencyID",
                    monetarySummation
                            .getTaxAmount()
                            .getCurrency()
                            .getCode()
            );

            writer.writeCharacters(
                    monetarySummation
                            .getTaxAmount()
                            .getAmount()
                            .toPlainString()
            );

            writer.endElement();

            writer.writeElement(
                    XmlNamespaces.RAM,
                    "GrandTotalAmount",
                    monetarySummation
                            .getTaxInclusiveAmount()
                            .getAmount()
                            .toPlainString()
            );

            writer.writeElement(
                    XmlNamespaces.RAM,
                    "DuePayableAmount",
                    monetarySummation
                            .getPayableAmount()
                            .getAmount()
                            .toPlainString()
            );

        } else {
            writer.writeElement(XmlNamespaces.RAM, "LineTotalAmount", monetarySummation.getLineExtensionAmount()
                    .getAmount().toPlainString());
            writer.writeElement(XmlNamespaces.RAM, "ChargeTotalAmount", monetarySummation.getChargeTotalAmount()
                    .getAmount().toPlainString());
            writer.writeElement(XmlNamespaces.RAM, "AllowanceTotalAmount", monetarySummation.getAllowanceTotalAmount()
                    .getAmount().toPlainString());
            writer.writeElement(XmlNamespaces.RAM, "TaxBasisTotalAmount", monetarySummation.getTaxExclusiveAmount()
                    .getAmount().toPlainString());

            // BT-110
            writer.startElement(XmlNamespaces.RAM, "TaxTotalAmount");
            writer.writeAttribute("currencyID", monetarySummation.getTaxAmount().getCurrency().getCode());
            writer.writeCharacters(monetarySummation.getTaxAmount().getAmount().toPlainString());

            writer.endElement();

            writer.writeElement(XmlNamespaces.RAM, "GrandTotalAmount", monetarySummation.getTaxInclusiveAmount()
                    .getAmount().toPlainString());
            writer.writeElement(XmlNamespaces.RAM, "TotalPrepaidAmount", monetarySummation.getPrepaidAmount()
                    .getAmount().toPlainString());
            writer.writeElement(XmlNamespaces.RAM, "DuePayableAmount", monetarySummation.getPayableAmount()
                    .getAmount().toPlainString());
        }

        writer.endElement();
    }
}