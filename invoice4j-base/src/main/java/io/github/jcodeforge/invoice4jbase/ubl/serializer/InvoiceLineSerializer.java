package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.AllowanceCharge;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoiceLine;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.ItemProperty;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class InvoiceLineSerializer implements XmlSerializer<InvoiceLine> {

    private final AllowanceChargeSerializer allowanceChargeSerializer = new AllowanceChargeSerializer();

    private final ItemPropertySerializer itemPropertySerializer = new ItemPropertySerializer();

    @Override
    public void serialize(XmlWriter writer, InvoiceLine line) {
        if (line == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "InvoiceLine"
        );

        writeId(writer, line);
        writeNote(writer, line);
        writeAccountingCost(writer, line);
        writeQuantity(writer, line);
        writeLineExtensionAmount(writer, line);
        writeAllowanceCharges(writer, line);
        writeItem(writer, line);
        writePrice(writer, line);

        writer.endElement();
    }

    private void writeId(XmlWriter writer, InvoiceLine line) {
        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "ID",
                line.getId()
        );
    }

    private void writeNote(XmlWriter writer, InvoiceLine line) {
        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "Note",
                line.getNote()
        );
    }

    private void writeAccountingCost(XmlWriter writer, InvoiceLine line) {
        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "AccountingCost",
                line.getBuyerAccountingReference()
        );
    }

    private void writeQuantity(XmlWriter writer, InvoiceLine line) {
        writer.startElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "InvoicedQuantity"
        );

        writer.writeAttribute(
                "unitCode",
                line.getUnitCode().getCode()
        );

        writer.writeCharacters(
                line.getQuantity().toPlainString()
        );

        writer.endElement();
    }

    private void writeLineExtensionAmount(XmlWriter writer, InvoiceLine line) {
        if (line.getLineExtensionAmount() == null) {
            return;
        }

        writer.startElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "LineExtensionAmount"
        );

        if (line.getLineExtensionAmount().getCurrency() != null) {
            writer.writeAttribute(
                    "currencyID",
                    line.getLineExtensionAmount()
                            .getCurrency()
                            .getCode()
            );
        }

        writer.writeCharacters(
                line.getLineExtensionAmount()
                        .getAmount()
                        .toPlainString()
        );

        writer.endElement();
    }

    private void writeAllowanceCharges(XmlWriter writer, InvoiceLine line) {
        if (line.getAllowanceCharges() == null) {
            return;
        }

        for (AllowanceCharge allowanceCharge : line.getAllowanceCharges()) {
            if (allowanceCharge != null) {
                allowanceChargeSerializer.serialize(writer, allowanceCharge);
            }
        }
    }

    private void writeItem(XmlWriter writer, InvoiceLine line) {
        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "Item"
        );

        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "Description",
                line.getDescription()
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "Name",
                line.getItemName()
        );

        writeSellerItemIdentification(writer, line);
        writeBuyerItemIdentification(writer, line);
        writeClassification(writer, line);
        writeTaxCategory(writer, line);
        writeProperties(writer, line);

        writer.endElement();
    }

    private void writeSellerItemIdentification(XmlWriter writer, InvoiceLine line) {
        if (line.getSellerAssignedIdentifier() == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "SellersItemIdentification"
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "ID",
                line.getSellerAssignedIdentifier()
        );

        writer.endElement();
    }

    private void writeBuyerItemIdentification(XmlWriter writer, InvoiceLine line) {
        if (line.getBuyerAssignedIdentifier() == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "BuyersItemIdentification"
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "ID",
                line.getBuyerAssignedIdentifier()
        );

        writer.endElement();
    }

    private void writeClassification(XmlWriter writer, InvoiceLine line) {
        if (line.getItemClassificationIdentifier() == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "CommodityClassification"
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "ItemClassificationCode",
                line.getItemClassificationIdentifier()
        );

        writer.endElement();
    }

    private void writeTaxCategory(XmlWriter writer, InvoiceLine line) {
        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "ClassifiedTaxCategory"
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "ID",
                line.getTaxCategory().getCode()
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "Percent",
                line.getTaxRate().toPlainString()
        );

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

        writer.endElement();
        writer.endElement();
    }

    private void writeProperties(XmlWriter writer, InvoiceLine line) {
        if (line.getProperties() == null) {
            return;
        }

        for (ItemProperty property : line.getProperties()) {
            if (property != null) {
                itemPropertySerializer.serialize(writer, property);
            }
        }
    }

    private void writePrice(XmlWriter writer, InvoiceLine line) {
        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "Price"
        );

        writePriceAmount(writer, line);
        writeBaseQuantity(writer, line);
        writePriceDiscount(writer, line);

        writer.endElement();
    }

    private void writePriceAmount(XmlWriter writer, InvoiceLine line) {
        writer.startElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "PriceAmount"
        );

        if (line.getNetPrice().getCurrency() != null) {
            writer.writeAttribute(
                    "currencyID",
                    line.getNetPrice()
                            .getCurrency()
                            .getCode()
            );
        }

        writer.writeCharacters(
                line.getNetPrice()
                        .getAmount()
                        .toPlainString()
        );

        writer.endElement();
    }

    /**
     * Writes BT-147 (price discount) and BT-148 (gross price).
     *
     * <p>The price-level allowance is always an allowance,
     * therefore ChargeIndicator is false.</p>
     */
    private void writePriceDiscount(XmlWriter writer, InvoiceLine line) {
        if (line.getPriceDiscount() == null && line.getGrossPrice() == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "AllowanceCharge"
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "ChargeIndicator",
                "false"
        );

        if (line.getPriceDiscount() != null) {
            writer.startElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "Amount"
            );

            if (line.getNetPrice().getCurrency() != null) {
                writer.writeAttribute(
                        "currencyID",
                        line.getNetPrice()
                                .getCurrency()
                                .getCode()
                );
            }

            writer.writeCharacters(
                    line.getPriceDiscount().toPlainString()
            );

            writer.endElement();
        }

        if (line.getGrossPrice() != null) {
            writer.startElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "BaseAmount"
            );

            if (line.getGrossPrice().getCurrency() != null) {
                writer.writeAttribute(
                        "currencyID",
                        line.getGrossPrice()
                                .getCurrency()
                                .getCode()
                );
            }

            writer.writeCharacters(
                    line.getGrossPrice()
                            .getAmount()
                            .toPlainString()
            );

            writer.endElement();
        }

        writer.endElement();
    }

    private void writeBaseQuantity(XmlWriter writer, InvoiceLine line) {
        if (line.getBaseQuantity() == null) {
            return;
        }

        writer.startElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "BaseQuantity"
        );

        writer.writeAttribute(
                "unitCode",
                line.getUnitCode().getCode()
        );

        writer.writeCharacters(
                line.getBaseQuantity().toPlainString()
        );

        writer.endElement();
    }
}