package io.github.jcodeforge.invoice4jbase.ubl.parser;

import io.github.jcodeforge.invoice4jbase.xml.XmlParser;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.AllowanceCharge;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public final class AllowanceChargeParser implements XmlParser<AllowanceCharge> {

    @Override
    public AllowanceCharge parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        Boolean charge = reader.readBoolean(
                basePath + "/cbc:ChargeIndicator");

        return AllowanceCharge.builder()
                .charge(Boolean.TRUE.equals(charge))
                .reason(reader.readString(
                        basePath + "/cbc:AllowanceChargeReason"))
                .reasonCode(reader.readString(
                        basePath + "/cbc:AllowanceChargeReasonCode"))
                .amount(readAmount(reader, basePath))
                .taxCategory(readCategoryCode(reader, basePath))
                .taxRate(reader.readDecimal(
                        basePath
                                + "/cac:TaxCategory"
                                + "/cbc:Percent"))
                .build();
    }

    private MonetaryAmount readAmount(
            XmlReader reader,
            String basePath) {

        String expression = basePath + "/cbc:Amount";

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

    private TaxCategoryCode readCategoryCode(
            XmlReader reader,
            String basePath) {

        String code = reader.readString(
                basePath
                        + "/cac:TaxCategory"
                        + "/cbc:ID");

        return code == null ? null : TaxCategoryCode.fromCode(code);
    }
}