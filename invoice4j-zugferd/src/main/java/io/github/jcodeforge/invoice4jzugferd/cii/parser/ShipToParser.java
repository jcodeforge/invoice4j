package io.github.jcodeforge.invoice4jzugferd.cii.parser;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.ShipTo;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlReader;

public final class ShipToParser implements XmlParser<ShipTo> {

    private final AddressParser addressParser = new AddressParser();

    @Override
    public ShipTo parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return ShipTo.builder()
                .name(reader.readString(basePath + "/ram:Name"))
                .address(addressParser.parse(
                        reader,
                        basePath + "/ram:PostalTradeAddress"))
                .build();
    }
}