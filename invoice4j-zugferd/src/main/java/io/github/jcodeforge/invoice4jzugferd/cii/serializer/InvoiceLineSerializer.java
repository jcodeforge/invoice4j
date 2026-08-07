package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoiceLine;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;

public final class InvoiceLineSerializer implements XmlSerializer<InvoiceLine> {

    @Override
    public void serialize(XmlWriter writer, InvoiceLine line) {
        if (line == null) {
            return;
        }

        writer.startElement(XmlNamespaces.RAM, "IncludedSupplyChainTradeLineItem");

        /*
         * AssociatedDocumentLineDocument
         */
        writer.startElement(XmlNamespaces.RAM, "AssociatedDocumentLineDocument");
        writer.writeElement(XmlNamespaces.RAM, "LineID", line.getId());
        writer.endElement();

        /*
         * SpecifiedTradeProduct
         */
        writer.startElement(XmlNamespaces.RAM, "SpecifiedTradeProduct");
        writer.writeElement(XmlNamespaces.RAM, "Name", line.getItemName());
        writer.writeOptionalElement(XmlNamespaces.RAM, "Description", line.getDescription());
        writer.writeOptionalElement(XmlNamespaces.RAM, "SellerAssignedID", line.getSellerAssignedIdentifier());
        writer.writeOptionalElement(XmlNamespaces.RAM, "BuyerAssignedID", line.getBuyerAssignedIdentifier());
        writer.endElement();

        /*
         * SpecifiedLineTradeAgreement
         */
        writer.startElement(XmlNamespaces.RAM, "SpecifiedLineTradeAgreement");

        if (line.getNetPrice() != null) {
            writer.startElement(XmlNamespaces.RAM, "NetPriceProductTradePrice");
            writer.writeElement(
                    XmlNamespaces.RAM,
                    "ChargeAmount",
                    line.getNetPrice().getAmount().toPlainString());
            writer.endElement();
        }

        writer.endElement();

        /*
         * SpecifiedLineTradeDelivery
         */
        writer.startElement(XmlNamespaces.RAM, "SpecifiedLineTradeDelivery");

        writer.startElement(XmlNamespaces.RAM, "BilledQuantity");
        writer.writeAttribute("unitCode", line.getUnitCode().getCode());
        writer.writeCharacters(line.getQuantity().toPlainString());
        writer.endElement();

        writer.endElement();

        /*
         * SpecifiedLineTradeSettlement
         */
        writer.startElement(XmlNamespaces.RAM, "SpecifiedLineTradeSettlement");

        writer.startElement(XmlNamespaces.RAM, "ApplicableTradeTax");
        writer.writeElement(XmlNamespaces.RAM, "TypeCode", "VAT");
        writer.writeElement(XmlNamespaces.RAM, "CategoryCode", line.getTaxCategory().getCode());
        writer.writeElement(XmlNamespaces.RAM, "RateApplicablePercent", line.getTaxRate().toPlainString());
        writer.endElement();

        if (line.getLineExtensionAmount() != null) {
            writer.startElement(XmlNamespaces.RAM, "SpecifiedTradeSettlementLineMonetarySummation");
            writer.writeElement(
                    XmlNamespaces.RAM,
                    "LineTotalAmount",
                    line.getLineExtensionAmount().getAmount().toPlainString());
            writer.endElement();
        }

        writer.endElement();

        writer.endElement();
    }
}