package io.github.jcodeforge.invoice4jbase.cii.parser;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Payee;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public final class PayeeParser implements XmlParser<Payee> {

    private final AddressParser addressParser = new AddressParser();

    private final ContactParser contactParser = new ContactParser();

    private final ElectronicAddressParser electronicAddressParser = new ElectronicAddressParser();

    @Override
    public Payee parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return Payee.builder()
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