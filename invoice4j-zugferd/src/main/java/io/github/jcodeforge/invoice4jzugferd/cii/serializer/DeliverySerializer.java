package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Delivery;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;
import java.time.format.DateTimeFormatter;

public class DeliverySerializer implements XmlSerializer<Delivery> {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.BASIC_ISO_DATE;

    private final ShipToSerializer shipToSerializer = new ShipToSerializer();

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

        // BG-14
        if (delivery.getDeliveryPeriodStartDate() != null || delivery.getDeliveryPeriodEndDate() != null) {
            writer.startElement(XmlNamespaces.RAM, "BillingSpecifiedPeriod");

            if (delivery.getDeliveryPeriodStartDate() != null) {
                writer.startElement(XmlNamespaces.RAM, "StartDateTime");
                writer.startElement(XmlNamespaces.UDT, "DateTimeString");
                writer.writeAttribute("format", "102");
                writer.writeCharacters(delivery.getDeliveryPeriodStartDate().format(DATE_FORMAT));
                writer.endElement();
                writer.endElement();
            }

            if (delivery.getDeliveryPeriodEndDate() != null) {
                writer.startElement(XmlNamespaces.RAM, "EndDateTime");
                writer.startElement(XmlNamespaces.UDT, "DateTimeString");
                writer.writeAttribute("format", "102");
                writer.writeCharacters(delivery.getDeliveryPeriodEndDate().format(DATE_FORMAT));
                writer.endElement();
                writer.endElement();
            }

            writer.endElement();
        }
    }
}