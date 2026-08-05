package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PaymentTerms;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;

public class PaymentTermsSerializer implements XmlSerializer<PaymentTerms> {

    @Override
    public void serialize(XmlWriter writer, PaymentTerms paymentTerms) {
        if (paymentTerms == null) {
            return;
        }

        writer.startElement(XmlNamespaces.RAM, "SpecifiedTradePaymentTerms");
        // BT-20
        writer.writeOptionalElement(XmlNamespaces.RAM, "Description", paymentTerms.getDescription());
        // BT-9
        if (paymentTerms.getDueDate() != null) {
            writer.startElement(XmlNamespaces.RAM, "DueDateDateTime");
            writer.startElement(XmlNamespaces.UDT, "DateTimeString");
            writer.writeAttribute("format", "102");
            writer.writeCharacters(paymentTerms.getDueDate().format(java.time.format.DateTimeFormatter.BASIC_ISO_DATE));
            writer.endElement();
            writer.endElement();
        }

        writer.endElement();
    }
}