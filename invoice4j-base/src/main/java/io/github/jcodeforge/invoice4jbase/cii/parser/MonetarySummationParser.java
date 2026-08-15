package io.github.jcodeforge.invoice4jbase.cii.parser;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetarySummation;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public final class MonetarySummationParser implements XmlParser<MonetarySummation> {

    @Override
    public MonetarySummation parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return MonetarySummation.builder()
                .lineExtensionAmount(readAmount(reader, basePath + "/ram:LineTotalAmount"))
                .chargeTotalAmount(readAmount(reader, basePath + "/ram:ChargeTotalAmount"))
                .allowanceTotalAmount(readAmount(reader, basePath + "/ram:AllowanceTotalAmount"))
                .taxExclusiveAmount(readAmount(reader, basePath + "/ram:TaxBasisTotalAmount"))
                .taxAmount(readAmount(reader, basePath + "/ram:TaxTotalAmount"))
                .taxInclusiveAmount(readAmount(reader, basePath + "/ram:GrandTotalAmount"))
                .prepaidAmount(readAmount(reader, basePath + "/ram:TotalPrepaidAmount"))
                .roundingAmount(readAmount(reader, basePath + "/ram:RoundingAmount"))
                .payableAmount(readAmount(reader, basePath + "/ram:DuePayableAmount"))
                .build();
    }

    private MonetaryAmount readAmount(XmlReader reader, String expression) {
        if (reader.isEmpty(expression)) {
            return null;
        }

        return MonetaryAmount.builder()
                .amount(reader.readDecimal(expression))
                .currency(CurrencyCode.EUR)
                .build();
    }
}