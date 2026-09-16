package io.github.jcodeforge.invoice4jbase.ubl.parser;

import io.github.jcodeforge.invoice4jbase.xml.XmlParser;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Contact;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public final class ContactParser implements XmlParser<Contact> {

    @Override
    public Contact parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return Contact.builder()
                .name(reader.readString(
                        basePath + "/cbc:Name"))
                .telephone(reader.readString(
                        basePath + "/cbc:Telephone"))
                .email(reader.readString(
                        basePath + "/cbc:ElectronicMail"))
                .build();
    }
}