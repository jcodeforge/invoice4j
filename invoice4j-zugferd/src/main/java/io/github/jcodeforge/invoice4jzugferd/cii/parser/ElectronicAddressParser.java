package io.github.jcodeforge.invoice4jzugferd.cii.parser;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.IdentifierScheme;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.ElectronicAddress;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlReader;

public final class ElectronicAddressParser implements XmlParser<ElectronicAddress> {

    @Override
    public ElectronicAddress parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return ElectronicAddress.builder()
                .scheme(readScheme(reader, basePath))
                .value(reader.readString(basePath + "/ram:URIID"))
                .build();
    }

    private IdentifierScheme readScheme(XmlReader reader, String basePath) {
        String scheme = reader.readAttribute(
                basePath + "/ram:URIID",
                "schemeID");

        return scheme == null ? null : IdentifierScheme.fromCode(scheme);
    }
}