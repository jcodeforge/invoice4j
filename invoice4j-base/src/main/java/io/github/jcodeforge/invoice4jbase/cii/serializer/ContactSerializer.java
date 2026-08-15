package io.github.jcodeforge.invoice4jbase.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Contact;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class ContactSerializer implements XmlSerializer<Contact> {

    @Override
    public void serialize(XmlWriter writer, Contact contact) {
        if (contact == null) {
            return;
        }

        writer.startElement(XmlNamespaces.RAM, "DefinedTradeContact");
        // BT-41 / BT-56
        writer.writeOptionalElement(XmlNamespaces.RAM, "PersonName", contact.getName());
        writer.writeOptionalElement(XmlNamespaces.RAM, "DepartmentName", contact.getDepartment());
        // BT-42 / BT-57
        if (contact.getTelephone() != null) {
            writer.startElement(XmlNamespaces.RAM, "TelephoneUniversalCommunication");
            writer.writeElement(XmlNamespaces.RAM, "CompleteNumber", contact.getTelephone());
            writer.endElement();
        }

        // BT-43 / BT-58
        if (contact.getEmail() != null) {
            writer.startElement(XmlNamespaces.RAM, "EmailURIUniversalCommunication");
            writer.writeElement(XmlNamespaces.RAM, "URIID", contact.getEmail());
            writer.endElement();
        }

        // Optional note
        writer.writeOptionalElement(XmlNamespaces.RAM, "Description", contact.getNote());

        writer.endElement();
    }
}