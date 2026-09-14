package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Contact;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class ContactSerializer implements XmlSerializer<Contact> {

    @Override
    public void serialize(
            XmlWriter writer,
            Contact contact) {

        if (contact == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "Contact"
        );

        // BT-41 / BT-56
        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "Name",
                contact.getName()
        );

        // BT-42 / BT-57
        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "Telephone",
                contact.getTelephone()
        );

        // BT-43 / BT-58
        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "ElectronicMail",
                contact.getEmail()
        );

        writer.endElement();
    }
}