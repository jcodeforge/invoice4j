package io.github.jcodeforge.invoice4jbase.cii.parser;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Contact;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public final class ContactParser implements XmlParser<Contact> {

    @Override
    public Contact parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return Contact.builder()
                .name(reader.readString(basePath + "/ram:PersonName"))
                .telephone(reader.readString(
                        basePath
                                + "/ram:TelephoneUniversalCommunication"
                                + "/ram:CompleteNumber"))
                .email(reader.readString(
                        basePath
                                + "/ram:EmailURIUniversalCommunication"
                                + "/ram:URIID"))
                .build();
    }
}