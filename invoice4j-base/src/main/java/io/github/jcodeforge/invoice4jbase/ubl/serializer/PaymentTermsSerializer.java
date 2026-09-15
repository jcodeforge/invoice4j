package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PaymentTerms;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class PaymentTermsSerializer implements XmlSerializer<PaymentTerms> {

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

        writer.endElement();
    }
}