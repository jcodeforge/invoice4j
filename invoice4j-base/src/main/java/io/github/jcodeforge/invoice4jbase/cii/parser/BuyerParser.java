package io.github.jcodeforge.invoice4jbase.cii.parser;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Buyer;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public final class BuyerParser implements XmlParser<Buyer> {

    private final AddressParser addressParser = new AddressParser();

    private final ContactParser contactParser = new ContactParser();

    private final ElectronicAddressParser electronicAddressParser = new ElectronicAddressParser();

    @Override
    public Buyer parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return Buyer.builder()
                .name(reader.readString(basePath + "/ram:Name"))
                .tradingName(reader.readString(basePath + "/ram:Description"))
                .address(addressParser.parse(
                        reader,
                        basePath + "/ram:PostalTradeAddress"))
                .contact(contactParser.parse(
                        reader,
                        basePath + "/ram:DefinedTradeContact"))
                .electronicAddress(electronicAddressParser.parse(
                        reader,
                        basePath + "/ram:URIUniversalCommunication"))
                .build();
    }
}
