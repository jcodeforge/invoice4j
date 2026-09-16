package io.github.jcodeforge.invoice4jbase.ubl.parser;

import io.github.jcodeforge.invoice4jbase.xml.XmlParser;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.UnitCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoiceLine;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;
import java.math.BigDecimal;

public final class InvoiceLineParser implements XmlParser<InvoiceLine> {

    @Override
    public InvoiceLine parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return InvoiceLine.builder()
                .id(reader.readString(
                        basePath + "/cbc:ID"))
                .note(reader.readString(
                        basePath + "/cbc:Note"))
                .itemName(reader.readString(
                        basePath + "/cac:Item/cbc:Name"))
                .quantity(reader.readDecimal(
                        basePath + "/cbc:InvoicedQuantity"))
                .unitCode(readUnitCode(reader, basePath))
                .netPrice(readNetPrice(reader, basePath))
                .priceDiscount(readPriceDiscount(reader, basePath))
                .grossPrice(readGrossPrice(reader, basePath))
                .baseQuantity(readBaseQuantity(reader, basePath))
                .taxCategory(readTaxCategory(reader, basePath))
                .taxRate(reader.readDecimal(
                        basePath
                                + "/cac:Item"
                                + "/cac:ClassifiedTaxCategory"
                                + "/cbc:Percent"))
                .lineExtensionAmount(readLineExtensionAmount(
                        reader, basePath))
                .build();
    }

    private UnitCode readUnitCode(XmlReader reader, String basePath) {
        String code = reader.readAttribute(
                basePath + "/cbc:InvoicedQuantity",
                "unitCode");

        return code == null ? null : UnitCode.fromCode(code);
    }

    private BigDecimal readBaseQuantity(XmlReader reader, String basePath) {
        String expression = basePath + "/cac:Price/cbc:BaseQuantity";

        if (reader.isEmpty(expression)) {
            return null;
        }

        return reader.readDecimal(expression);
    }

    private TaxCategoryCode readTaxCategory(XmlReader reader, String basePath) {
        String code = reader.readString(
                basePath
                        + "/cac:Item"
                        + "/cac:ClassifiedTaxCategory"
                        + "/cbc:ID");

        return code == null ? null : TaxCategoryCode.fromCode(code);
    }

    private MonetaryAmount readNetPrice(XmlReader reader, String basePath) {
        String expression = basePath + "/cac:Price/cbc:PriceAmount";

        return readAmount(reader, expression);
    }

    private BigDecimal readPriceDiscount(XmlReader reader, String basePath) {
        String expression =
                basePath
                        + "/cac:Price"
                        + "/cac:AllowanceCharge"
                        + "[cbc:ChargeIndicator='false']"
                        + "/cbc:Amount";

        if (reader.isEmpty(expression)) {
            return null;
        }

        return reader.readDecimal(expression);
    }

    private MonetaryAmount readGrossPrice(XmlReader reader, String basePath) {
        String expression =
                basePath
                        + "/cac:Price"
                        + "/cac:AllowanceCharge"
                        + "[cbc:ChargeIndicator='false']"
                        + "/cbc:BaseAmount";

        return readAmount(reader, expression);
    }

    private MonetaryAmount readLineExtensionAmount(XmlReader reader, String basePath) {
        String expression = basePath + "/cbc:LineExtensionAmount";

        return readAmount(reader, expression);
    }

    private MonetaryAmount readAmount(XmlReader reader, String expression) {
        if (reader.isEmpty(expression)) {
            return null;
        }

        String currencyCode = reader.readAttribute(expression, "currencyID");

        return MonetaryAmount.builder()
                .amount(reader.readDecimal(expression))
                .currency(currencyCode == null
                        ? null
                        : CurrencyCode.fromCode(currencyCode))
                .build();
    }
}