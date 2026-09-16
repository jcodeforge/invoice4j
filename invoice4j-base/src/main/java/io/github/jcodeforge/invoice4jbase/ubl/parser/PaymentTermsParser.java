package io.github.jcodeforge.invoice4jbase.ubl.parser;

import io.github.jcodeforge.invoice4jbase.xml.XmlParser;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PaymentTerms;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public final class PaymentTermsParser implements XmlParser<PaymentTerms> {

    @Override
    public PaymentTerms parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return PaymentTerms.builder()
                .description(reader.readString(
                        basePath + "/cbc:Note"))
                .build();
    }
}