package io.github.jcodeforge.invoice4jzugferd.cii.parser;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CountryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Address;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlReader;

public final class AddressParser implements XmlParser<Address> {

    @Override
    public Address parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return Address.builder()
                .street(reader.readString(basePath + "/ram:LineOne"))
                .additionalStreet(reader.readString(basePath + "/ram:LineTwo"))
                .city(reader.readString(basePath + "/ram:CityName"))
                .postcode(reader.readString(basePath + "/ram:PostcodeCode"))
                .countrySubdivision(reader.readString(basePath + "/ram:CountrySubDivisionName"))
                .countryCode(readCountryCode(reader, basePath))
                .build();
    }

    private CountryCode readCountryCode(XmlReader reader, String basePath) {
        String code = reader.readString(basePath + "/ram:CountryID");

        return code == null ? null : CountryCode.fromCode(code);
    }
}