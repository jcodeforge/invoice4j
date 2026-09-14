package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoicePeriod;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class InvoicePeriodSerializer implements XmlSerializer<InvoicePeriod> {

    @Override
    public void serialize(XmlWriter writer, InvoicePeriod invoicePeriod) {
        if (invoicePeriod == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "InvoicePeriod"
        );

        if (invoicePeriod.getStartDate() != null) {
            writer.writeElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "StartDate",
                    invoicePeriod.getStartDate().toString()
            );
        }

        if (invoicePeriod.getEndDate() != null) {
            writer.writeElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "EndDate",
                    invoicePeriod.getEndDate().toString()
            );
        }

        writer.endElement();
    }
}