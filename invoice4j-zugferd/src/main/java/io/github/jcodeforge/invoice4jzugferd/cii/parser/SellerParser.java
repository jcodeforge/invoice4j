package io.github.jcodeforge.invoice4jzugferd.cii.parser;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Seller;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlReader;

public final class SellerParser implements XmlParser<Seller> {

    private final AddressParser addressParser = new AddressParser();

    private final ContactParser contactParser = new ContactParser();

    private final ElectronicAddressParser electronicAddressParser = new ElectronicAddressParser();

    @Override
    public Seller parse(XmlReader reader, String basePath) {

        return Seller.builder()
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