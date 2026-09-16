package io.github.jcodeforge.invoice4jbase.ubl.parser;

import io.github.jcodeforge.invoice4jbase.xml.XmlParser;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Seller;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public final class SellerParser implements XmlParser<Seller> {

    private final AddressParser addressParser = new AddressParser();

    private final ContactParser contactParser = new ContactParser();

    private final ElectronicAddressParser electronicAddressParser =
            new ElectronicAddressParser();

    @Override
    public Seller parse(XmlReader reader, String basePath) {
        return Seller.builder()
                .name(reader.readString(
                        basePath + "/cac:PartyLegalEntity/cbc:RegistrationName"))
                .tradingName(reader.readString(
                        basePath + "/cac:PartyName/cbc:Name"))
                .address(addressParser.parse(
                        reader,
                        basePath + "/cac:PostalAddress"))
                .contact(contactParser.parse(
                        reader,
                        basePath + "/cac:Contact"))
                .electronicAddress(electronicAddressParser.parse(
                        reader,
                        basePath + "/cbc:EndpointID"))
                .build();
    }
}