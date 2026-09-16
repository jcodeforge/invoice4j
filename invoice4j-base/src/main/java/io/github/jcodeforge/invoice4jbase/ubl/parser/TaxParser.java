package io.github.jcodeforge.invoice4jbase.ubl.parser;

import io.github.jcodeforge.invoice4jbase.xml.XmlParser;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Tax;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

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
                .rate(reader.readDecimal(
                        basePath + "/cac:TaxCategory/cbc:Percent"))
                .exemptionReason(reader.readString(
                        basePath + "/cac:TaxCategory/cbc:TaxExemptionReason"))
                .build();
    }

    private MonetaryAmount readTaxAmount(XmlReader reader, String basePath) {
        if (reader.isEmpty(basePath + "/cbc:TaxAmount")) {
            return null;
        }

        return MonetaryAmount.builder()
                .amount(reader.readDecimal(
                        basePath + "/cbc:TaxAmount"))
                .currency(readCurrency(
                        reader,
                        basePath + "/cbc:TaxAmount"))
                .build();
    }

    private MonetaryAmount readTaxableAmount(XmlReader reader, String basePath) {
        if (reader.isEmpty(basePath + "/cbc:TaxableAmount")) {
            return null;
        }

        return MonetaryAmount.builder()
                .amount(reader.readDecimal(
                        basePath + "/cbc:TaxableAmount"))
                .currency(readCurrency(
                        reader,
                        basePath + "/cbc:TaxableAmount"))
                .build();
    }

    private CurrencyCode readCurrency(XmlReader reader, String path) {
        String code = reader.readAttribute(path, "currencyID");

        return code == null ? null : CurrencyCode.fromCode(code);
    }

    private TaxCategoryCode readCategoryCode(XmlReader reader, String basePath) {
        String code = reader.readString(
                basePath + "/cac:TaxCategory/cbc:ID");

        return code == null ? null : TaxCategoryCode.fromCode(code);
    }
}