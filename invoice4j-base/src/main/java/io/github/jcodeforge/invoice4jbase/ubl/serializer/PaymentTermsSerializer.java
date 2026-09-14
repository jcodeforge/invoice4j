package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PaymentTerms;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;
import java.time.format.DateTimeFormatter;

public final class PaymentTermsSerializer implements XmlSerializer<PaymentTerms> {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public void serialize(XmlWriter writer, PaymentTerms paymentTerms) {
        if (paymentTerms == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "PaymentTerms"
        );

        // BT-20
        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "Note",
                paymentTerms.getDescription()
        );

        // BT-9
        if (paymentTerms.getDueDate() != null) {
            writer.writeElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "PaymentDueDate",
                    paymentTerms.getDueDate().format(DATE_FORMAT)
            );
        }

        writer.endElement();
    }
}