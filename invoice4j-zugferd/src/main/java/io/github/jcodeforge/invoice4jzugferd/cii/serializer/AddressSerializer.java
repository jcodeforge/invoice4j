package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Address;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;

public final class AddressSerializer implements XmlSerializer<Address> {

    @Override
    public void serialize(XmlWriter writer, Address address) {
        if (address == null) {
            return;
        }

        writer.startElement(XmlNamespaces.RAM, "PostalTradeAddress");
        // BT-38 / BT-53 / BT-64 / BT-78
        writer.writeOptionalElement(XmlNamespaces.RAM, "PostcodeCode", address.getPostcode());
        // BT-35 / BT-50 / BT-61 / BT-75
        writer.writeOptionalElement(XmlNamespaces.RAM, "LineOne", address.getStreet());
        writer.writeOptionalElement(XmlNamespaces.RAM, "LineTwo", address.getAdditionalStreet());
        // BT-36 / BT-51 / BT-62 / BT-76
        writer.writeOptionalElement(XmlNamespaces.RAM, "CityName", address.getCity());
        // BT-40 / BT-55 / BT-66 / BT-80
        if (address.getCountryCode() != null) {
            writer.writeElement(XmlNamespaces.RAM, "CountryID", address.getCountryCode().getCode());
        }
        // BT-39 / BT-54 / BT-65 / BT-79
        writer.writeOptionalElement(XmlNamespaces.RAM, "CountrySubDivisionName", address.getCountrySubdivision());

        writer.endElement();
    }
}