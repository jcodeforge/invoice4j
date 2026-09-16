package io.github.jcodeforge.invoice4jbase.ubl.parser;

import io.github.jcodeforge.invoice4jbase.xml.XmlParser;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.IdentifierScheme;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.ElectronicAddress;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public final class ElectronicAddressParser implements XmlParser<ElectronicAddress> {

    @Override
    public ElectronicAddress parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return ElectronicAddress.builder()
                .scheme(readScheme(reader, basePath))
                .value(reader.readString(basePath))
                .build();
    }

    private IdentifierScheme readScheme(XmlReader reader, String basePath) {
        String scheme = reader.readAttribute(
                basePath,
                "schemeID");

        return scheme == null ? null : IdentifierScheme.fromCode(scheme);
    }
}