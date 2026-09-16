package io.github.jcodeforge.invoice4jbase.ubl.parser;

import io.github.jcodeforge.invoice4jbase.xml.XmlParser;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.ShipTo;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public final class ShipToParser implements XmlParser<ShipTo> {

    private final AddressParser addressParser = new AddressParser();

    @Override
    public ShipTo parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return ShipTo.builder()
                .name(reader.readString(
                        basePath + "/cac:PartyName/cbc:Name"))
                .address(addressParser.parse(
                        reader,
                        basePath + "/cac:PostalAddress"))
                .build();
    }
}