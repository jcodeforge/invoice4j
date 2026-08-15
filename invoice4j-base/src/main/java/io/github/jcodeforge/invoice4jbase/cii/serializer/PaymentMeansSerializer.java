package io.github.jcodeforge.invoice4jbase.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PaymentMeans;
import io.github.jcodeforge.invoice4jbase.cii.CiiConfigurationOptions;
import io.github.jcodeforge.invoice4jbase.cii.CiiProfile;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;
import java.util.Objects;

public final class PaymentMeansSerializer implements XmlSerializer<PaymentMeans> {

    private final BankAccountSerializer bankAccountSerializer;

    private final CiiConfigurationOptions options;

    public PaymentMeansSerializer(CiiConfigurationOptions options) {
        this.options = Objects.requireNonNull(options, "options must not be null");
        this.bankAccountSerializer = new BankAccountSerializer(options);
    }

    @Override
    public void serialize(XmlWriter writer, PaymentMeans paymentMeans) {
        if (paymentMeans == null) {
            return;
        }

        writer.startElement(
                XmlNamespaces.RAM,
                "SpecifiedTradeSettlementPaymentMeans"
        );

        // BT-81
        writer.writeElement(
                XmlNamespaces.RAM,
                "TypeCode",
                paymentMeans.getMeansCode().getCode()
        );

        // Payment means description
        // Not supported by ZUGFeRD BASIC.
        if (options.getProfile() != CiiProfile.ZUGFERD_BASIC && options.getProfile() != CiiProfile.ZUGFERD_BASIC_WL) {
            writer.writeOptionalElement(
                    XmlNamespaces.RAM,
                    "Information",
                    paymentMeans.getMeansDescription()
            );
        }

        // BG-17
        bankAccountSerializer.serialize(writer, paymentMeans.getBankAccount());

        writer.endElement();
    }
}