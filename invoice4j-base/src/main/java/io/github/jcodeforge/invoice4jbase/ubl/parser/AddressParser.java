package io.github.jcodeforge.invoice4jbase.ubl.parser;

import io.github.jcodeforge.invoice4jbase.xml.XmlParser;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.CountryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Address;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public final class AddressParser implements XmlParser<Address> {

    @Override
    public Address parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return Address.builder()
                .street(reader.readString(
                        basePath + "/cbc:StreetName"))

                .additionalStreet(reader.readString(
                        basePath + "/cbc:AdditionalStreetName"))

                .city(reader.readString(
                        basePath + "/cbc:CityName"))

                .postcode(reader.readString(
                        basePath + "/cbc:PostalZone"))

                .countrySubdivision(reader.readString(
                        basePath + "/cbc:CountrySubentity"))

                .countryCode(readCountryCode(reader, basePath))

                .build();
    }

    private CountryCode readCountryCode(XmlReader reader, String basePath) {
        String code = reader.readString(
                basePath + "/cac:Country/cbc:IdentificationCode");

        return code == null ? null : CountryCode.fromCode(code);
    }
}