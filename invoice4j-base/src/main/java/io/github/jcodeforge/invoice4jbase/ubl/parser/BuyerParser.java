package io.github.jcodeforge.invoice4jbase.ubl.parser;

import io.github.jcodeforge.invoice4jbase.xml.XmlParser;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Buyer;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public final class BuyerParser implements XmlParser<Buyer> {

    private final AddressParser addressParser = new AddressParser();

    private final ContactParser contactParser = new ContactParser();

    private final ElectronicAddressParser electronicAddressParser =
            new ElectronicAddressParser();

    @Override
    public Buyer parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return Buyer.builder()
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