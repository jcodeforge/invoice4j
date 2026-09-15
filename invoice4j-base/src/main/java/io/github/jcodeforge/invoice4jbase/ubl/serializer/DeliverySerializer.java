package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Delivery;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;
import java.time.format.DateTimeFormatter;

public final class DeliverySerializer implements XmlSerializer<Delivery> {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    private final ShipToSerializer shipToSerializer = new ShipToSerializer();

    @Override
    public void serialize(XmlWriter writer, Delivery delivery) {
        if (delivery == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "Delivery"
        );

        if (delivery.getActualDeliveryDate() != null) {
            writer.writeElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "ActualDeliveryDate",
                    delivery.getActualDeliveryDate().format(DATE_FORMAT)
            );
        }

        shipToSerializer.serialize(writer, delivery.getShipTo());

        writer.endElement();
    }
}