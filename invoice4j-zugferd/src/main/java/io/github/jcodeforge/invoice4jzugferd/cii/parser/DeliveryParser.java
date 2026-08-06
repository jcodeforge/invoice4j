package io.github.jcodeforge.invoice4jzugferd.cii.parser;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Delivery;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlReader;

public final class DeliveryParser implements XmlParser<Delivery> {

    private final ShipToParser shipToParser = new ShipToParser();

    @Override
    public Delivery parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return Delivery.builder()
                .shipTo(shipToParser.parse(reader, basePath + "/ram:ShipToTradeParty"))
                .actualDeliveryDate(reader.readDate(
                        basePath
                                + "/ram:ActualDeliverySupplyChainEvent"
                                + "/ram:OccurrenceDateTime"
                                + "/udt:DateTimeString"))
                .deliveryPeriodStartDate(reader.readDate(
                        basePath
                                + "/ram:BillingSpecifiedPeriod"
                                + "/ram:StartDateTime"
                                + "/udt:DateTimeString"))
                .deliveryPeriodEndDate(reader.readDate(
                        basePath
                                + "/ram:BillingSpecifiedPeriod"
                                + "/ram:EndDateTime"
                                + "/udt:DateTimeString"))
                .build();
    }
}