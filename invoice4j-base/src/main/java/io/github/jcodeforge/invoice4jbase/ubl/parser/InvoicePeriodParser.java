package io.github.jcodeforge.invoice4jbase.ubl.parser;

import io.github.jcodeforge.invoice4jbase.ubl.UblDateFormats;
import io.github.jcodeforge.invoice4jbase.xml.XmlParser;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoicePeriod;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public final class InvoicePeriodParser implements XmlParser<InvoicePeriod> {

    @Override
    public InvoicePeriod parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return InvoicePeriod.builder()
                .startDate(reader.readDate(
                        basePath + "/cbc:StartDate", UblDateFormats.DATE))
                .endDate(reader.readDate(
                        basePath + "/cbc:EndDate", UblDateFormats.DATE))
                .build();
    }
}