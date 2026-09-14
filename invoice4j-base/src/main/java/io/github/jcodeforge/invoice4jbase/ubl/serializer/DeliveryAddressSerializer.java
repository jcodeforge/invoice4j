package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Address;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class DeliveryAddressSerializer implements XmlSerializer<Address> {

    @Override
    public void serialize(XmlWriter writer, Address address) {
        if (address == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "Address"
        );

        // BT-38 / BT-53 / BT-64 / BT-78
        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "PostalZone",
                address.getPostcode()
        );

        // BT-35 / BT-50 / BT-61 / BT-75
        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "StreetName",
                address.getStreet()
        );

        // BT-36 / BT-51 / BT-62 / BT-76
        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "CityName",
                address.getCity()
        );

        // BT-39 / BT-54 / BT-65 / BT-79
        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "CountrySubentity",
                address.getCountrySubdivision()
        );

        if (address.getCountryCode() != null) {
            writer.startElement(
                    "cac",
                    XmlNamespaces.UBL_CAC,
                    "Country"
            );

            writer.writeElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "IdentificationCode",
                    address.getCountryCode().getCode()
            );

            writer.endElement(); // Country
        }

        writer.endElement(); // Address
    }
}