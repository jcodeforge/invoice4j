package io.github.jcodeforge.invoice4jbase.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Delivery;
import io.github.jcodeforge.invoice4jbase.cii.CiiConfigurationOptions;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class DeliverySerializer implements XmlSerializer<Delivery> {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.BASIC_ISO_DATE;

    private final ShipToSerializer shipToSerializer;

    private final CiiConfigurationOptions options;

    public DeliverySerializer(CiiConfigurationOptions options) {
        this.options = Objects.requireNonNull(options, "options must not be null");
        this.shipToSerializer = new ShipToSerializer(options);
    }

    @Override
    public void serialize(XmlWriter writer, Delivery delivery) {
        if (delivery == null) {
            return;
        }

        // BG-13
        shipToSerializer.serialize(writer, delivery.getShipTo());

        // BT-72
        if (delivery.getActualDeliveryDate() != null) {
            writer.startElement(XmlNamespaces.RAM, "ActualDeliverySupplyChainEvent");
            writer.startElement(XmlNamespaces.RAM, "OccurrenceDateTime");
            writer.startElement(XmlNamespaces.UDT, "DateTimeString");
            writer.writeAttribute("format", "102");
            writer.writeCharacters(delivery.getActualDeliveryDate().format(DATE_FORMAT));
            writer.endElement();
            writer.endElement();
            writer.endElement();
        }
    }
}