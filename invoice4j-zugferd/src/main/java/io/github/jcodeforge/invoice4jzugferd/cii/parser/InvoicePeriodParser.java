package io.github.jcodeforge.invoice4jzugferd.cii.parser;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoicePeriod;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlReader;

public final class InvoicePeriodParser implements XmlParser<InvoicePeriod> {

    @Override
    public InvoicePeriod parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return InvoicePeriod.builder()
                .startDate(reader.readDate(
                        basePath
                                + "/ram:StartDateTime"
                                + "/udt:DateTimeString"))
                .endDate(reader.readDate(
                        basePath
                                + "/ram:EndDateTime"
                                + "/udt:DateTimeString"))
                .build();
    }
}