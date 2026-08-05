package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoicePeriod;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;
import java.time.format.DateTimeFormatter;

public class InvoicePeriodSerializer implements XmlSerializer<InvoicePeriod> {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.BASIC_ISO_DATE;

    @Override
    public void serialize(XmlWriter writer, InvoicePeriod invoicePeriod) {
        if (invoicePeriod == null) {
            return;
        }

        writer.startElement(XmlNamespaces.RAM, "BillingSpecifiedPeriod");

        if (invoicePeriod.getStartDate() != null) {
            writer.startElement(XmlNamespaces.RAM, "StartDateTime");
            writer.startElement(XmlNamespaces.UDT, "DateTimeString");
            writer.writeAttribute("format", "102");
            writer.writeCharacters(invoicePeriod.getStartDate().format(DATE_FORMAT));
            writer.endElement();
            writer.endElement();
        }

        if (invoicePeriod.getEndDate() != null) {
            writer.startElement(XmlNamespaces.RAM, "EndDateTime");
            writer.startElement(XmlNamespaces.UDT, "DateTimeString");
            writer.writeAttribute("format", "102");
            writer.writeCharacters(invoicePeriod.getEndDate().format(DATE_FORMAT));
            writer.endElement();
            writer.endElement();
        }

        writer.endElement();
    }
}