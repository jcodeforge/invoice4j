package io.github.jcodeforge.invoice4jzugferd.cii.parser;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Tax;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlReader;

public final class TaxParser implements XmlParser<Tax> {

    @Override
    public Tax parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return Tax.builder()
                .taxAmount(readTaxAmount(reader, basePath))
                .taxableAmount(readTaxableAmount(reader, basePath))
                .categoryCode(readCategoryCode(reader, basePath))
                .rate(reader.readDecimal(basePath + "/ram:RateApplicablePercent"))
                .exemptionReason(reader.readString(basePath + "/ram:ExemptionReason"))
                .build();
    }

    private MonetaryAmount readTaxAmount(XmlReader reader, String basePath) {
        if (reader.isEmpty(basePath + "/ram:CalculatedAmount")) {
            return null;
        }

        return MonetaryAmount.builder()
                .amount(reader.readDecimal(basePath + "/ram:CalculatedAmount"))
                .currency(CurrencyCode.EUR)
                .build();
    }

    private MonetaryAmount readTaxableAmount(XmlReader reader, String basePath) {
        if (reader.isEmpty(basePath + "/ram:BasisAmount")) {
            return null;
        }

        return MonetaryAmount.builder()
                .amount(reader.readDecimal(basePath + "/ram:BasisAmount"))
                .currency(CurrencyCode.EUR)
                .build();
    }

    private TaxCategoryCode readCategoryCode(XmlReader reader, String basePath) {
        String code = reader.readString(basePath + "/ram:CategoryCode");

        return code == null ? null : TaxCategoryCode.fromCode(code);
    }
}