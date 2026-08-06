package io.github.jcodeforge.invoice4jzugferd.cii.parser;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PaymentTerms;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlReader;

public final class PaymentTermsParser implements XmlParser<PaymentTerms> {

    @Override
    public PaymentTerms parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return PaymentTerms.builder()
                .description(reader.readString(basePath + "/ram:Description"))
                .dueDate(reader.readDate(
                        basePath
                                + "/ram:DueDateDateTime"
                                + "/udt:DateTimeString"))
                .build();
    }
}