package io.github.jcodeforge.invoice4jbase.cii.parser;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.UnitCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoiceLine;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public final class InvoiceLineParser implements XmlParser<InvoiceLine> {

    @Override
    public InvoiceLine parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return InvoiceLine.builder()
                .id(reader.readString(
                        basePath
                                + "/ram:AssociatedDocumentLineDocument"
                                + "/ram:LineID"))
                .itemName(reader.readString(
                        basePath
                                + "/ram:SpecifiedTradeProduct"
                                + "/ram:Name"))
                .quantity(reader.readDecimal(
                        basePath
                                + "/ram:SpecifiedLineTradeDelivery"
                                + "/ram:BilledQuantity"))
                .unitCode(readUnitCode(reader, basePath))
                .netPrice(readNetPrice(reader, basePath))
                .taxCategory(readTaxCategory(reader, basePath))
                .taxRate(reader.readDecimal(
                        basePath
                                + "/ram:SpecifiedLineTradeSettlement"
                                + "/ram:ApplicableTradeTax"
                                + "/ram:RateApplicablePercent"))
                .lineExtensionAmount(readLineExtensionAmount(reader, basePath))
                .build();
    }

    private UnitCode readUnitCode(XmlReader reader, String basePath) {
        String code = reader.readAttribute(
                basePath
                        + "/ram:SpecifiedLineTradeDelivery"
                        + "/ram:BilledQuantity",
                "unitCode");

        return code == null ? null : UnitCode.fromCode(code);
    }

    private TaxCategoryCode readTaxCategory(XmlReader reader, String basePath) {
        String code = reader.readString(
                basePath
                        + "/ram:SpecifiedLineTradeSettlement"
                        + "/ram:ApplicableTradeTax"
                        + "/ram:CategoryCode");

        return code == null ? null : TaxCategoryCode.fromCode(code);
    }

    private MonetaryAmount readNetPrice(XmlReader reader, String basePath) {
        String expression =
                basePath
                        + "/ram:SpecifiedLineTradeAgreement"
                        + "/ram:NetPriceProductTradePrice"
                        + "/ram:ChargeAmount";

        if (reader.isEmpty(expression)) {
            return null;
        }

        return MonetaryAmount.builder()
                .amount(reader.readDecimal(expression))
                .currency(CurrencyCode.EUR)
                .build();
    }

    private MonetaryAmount readLineExtensionAmount(XmlReader reader, String basePath) {
        String expression =
                basePath
                        + "/ram:SpecifiedLineTradeSettlement"
                        + "/ram:SpecifiedTradeSettlementLineMonetarySummation"
                        + "/ram:LineTotalAmount";

        if (reader.isEmpty(expression)) {
            return null;
        }

        return MonetaryAmount.builder()
                .amount(reader.readDecimal(expression))
                .currency(CurrencyCode.EUR)
                .build();
    }
}