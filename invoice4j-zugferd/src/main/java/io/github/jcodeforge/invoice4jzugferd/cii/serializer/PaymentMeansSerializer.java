package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PaymentMeans;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;

public class PaymentMeansSerializer implements XmlSerializer<PaymentMeans> {

    private final BankAccountSerializer bankAccountSerializer = new BankAccountSerializer();

    @Override
    public void serialize(XmlWriter writer, PaymentMeans paymentMeans) {
        if (paymentMeans == null) {
            return;
        }

        writer.startElement(XmlNamespaces.RAM, "SpecifiedTradeSettlementPaymentMeans");
        // BT-81
        writer.writeElement(XmlNamespaces.RAM, "TypeCode", paymentMeans.getMeansCode().getCode());
        // Optional description
        writer.writeOptionalElement(XmlNamespaces.RAM, "Information", paymentMeans.getMeansDescription());
        // BT-83
        writer.writeOptionalElement(XmlNamespaces.RAM, "PaymentReference", paymentMeans.getRemittanceInformation());
        // BG-17
        bankAccountSerializer.serialize(writer, paymentMeans.getBankAccount());

        writer.endElement();
    }
}