package io.github.jcodeforge.invoice4jbase.ubl.parser;

import io.github.jcodeforge.invoice4jbase.xml.XmlParser;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetarySummation;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public final class MonetarySummationParser implements XmlParser<MonetarySummation> {

    @Override
    public MonetarySummation parse(XmlReader reader, String basePath) {
        return parse(reader, basePath, null);
    }


    public MonetarySummation parse(XmlReader reader, String legalMonetaryTotalPath, String taxTotalPath) {
        if (!reader.exists(legalMonetaryTotalPath)) {
            return null;
        }

        return MonetarySummation.builder()
                .lineExtensionAmount(
                        readAmount(reader,
                                legalMonetaryTotalPath + "/cbc:LineExtensionAmount"))
                .allowanceTotalAmount(
                        readAmount(reader,
                                legalMonetaryTotalPath + "/cbc:AllowanceTotalAmount"))
                .chargeTotalAmount(
                        readAmount(reader,
                                legalMonetaryTotalPath + "/cbc:ChargeTotalAmount"))
                .taxExclusiveAmount(
                        readAmount(reader,
                                legalMonetaryTotalPath + "/cbc:TaxExclusiveAmount"))
                .taxAmount(taxTotalPath == null ? null : readAmount(reader, taxTotalPath + "/cbc:TaxAmount"))
                .taxInclusiveAmount(
                        readAmount(reader,
                                legalMonetaryTotalPath + "/cbc:TaxInclusiveAmount"))
                .prepaidAmount(
                        readAmount(reader,
                                legalMonetaryTotalPath + "/cbc:PrepaidAmount"))
                .roundingAmount(
                        readAmount(reader,
                                legalMonetaryTotalPath + "/cbc:PayableRoundingAmount"))
                .payableAmount(
                        readAmount(reader,
                                legalMonetaryTotalPath + "/cbc:PayableAmount"))
                .build();
    }

    private MonetaryAmount readAmount(XmlReader reader, String expression) {
        if (reader.isEmpty(expression)) {
            return null;
        }

        String currencyCode = reader.readAttribute(
                expression,
                "currencyID");

        return MonetaryAmount.builder()
                .amount(reader.readDecimal(expression))
                .currency(currencyCode == null
                        ? null
                        : CurrencyCode.fromCode(currencyCode))
                .build();
    }
}