package io.github.jcodeforge.invoice4jzugferd.cii.parser;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.AllowanceCharge;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlReader;

public final class AllowanceChargeParser implements XmlParser<AllowanceCharge> {

    @Override
    public AllowanceCharge parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        Boolean charge = reader.readBoolean(
                basePath
                        + "/ram:ChargeIndicator"
                        + "/udt:Indicator");

        return AllowanceCharge.builder()
                .charge(Boolean.TRUE.equals(charge))
                .reason(reader.readString(basePath + "/ram:Reason"))
                .reasonCode(reader.readString(basePath + "/ram:ReasonCode"))
                .amount(readAmount(reader, basePath))
                .taxCategory(readCategoryCode(reader, basePath))
                .taxRate(reader.readDecimal(
                        basePath
                                + "/ram:CategoryTradeTax"
                                + "/ram:RateApplicablePercent"))
                .build();
    }

    private MonetaryAmount readAmount(XmlReader reader, String basePath) {
        String expression = basePath + "/ram:ActualAmount";

        if (reader.isEmpty(expression)) {
            return null;
        }

        return MonetaryAmount.builder()
                .amount(reader.readDecimal(expression))
                .currency(CurrencyCode.EUR)
                .build();
    }

    private TaxCategoryCode readCategoryCode(XmlReader reader, String basePath) {
        String code = reader.readString(
                basePath
                        + "/ram:CategoryTradeTax"
                        + "/ram:CategoryCode");

        return code == null ? null : TaxCategoryCode.fromCode(code);
    }
}