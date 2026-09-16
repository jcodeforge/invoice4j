package io.github.jcodeforge.invoice4jbase.ubl.parser;

import io.github.jcodeforge.invoice4jbase.ubl.UblDateFormats;
import io.github.jcodeforge.invoice4jbase.xml.XmlParser;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Delivery;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public final class DeliveryParser implements XmlParser<Delivery> {

    private final ShipToParser shipToParser = new ShipToParser();

    @Override
    public Delivery parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return Delivery.builder()
                .shipTo(shipToParser.parse(
                        reader,
                        basePath + "/cac:DeliveryParty/cac:Party"))
                .actualDeliveryDate(reader.readDate(
                        basePath + "/cbc:ActualDeliveryDate", UblDateFormats.DATE))
                .build();
    }
}