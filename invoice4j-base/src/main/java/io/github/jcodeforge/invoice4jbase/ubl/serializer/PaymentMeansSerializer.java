package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PaymentMeans;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class PaymentMeansSerializer implements XmlSerializer<PaymentMeans> {

    private final BankAccountSerializer bankAccountSerializer = new BankAccountSerializer();

    @Override
    public void serialize(XmlWriter writer, PaymentMeans paymentMeans) {
        if (paymentMeans == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "PaymentMeans"
        );

        // BT-81
        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "PaymentMeansCode",
                paymentMeans.getMeansCode().getCode()
        );

        // BG-17
        bankAccountSerializer.serialize(writer, paymentMeans.getBankAccount());

        writer.endElement();
    }
}