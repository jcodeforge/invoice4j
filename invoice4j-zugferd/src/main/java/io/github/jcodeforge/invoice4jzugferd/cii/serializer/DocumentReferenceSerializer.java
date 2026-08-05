package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.DocumentReference;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;
import java.time.format.DateTimeFormatter;

public class DocumentReferenceSerializer implements XmlSerializer<DocumentReference> {

    @Override
    public void serialize(XmlWriter writer, DocumentReference reference) {
        serialize(writer, "AdditionalReferencedDocument", reference);
    }

    public void serialize(XmlWriter writer, String elementName, DocumentReference reference) {
        if (reference == null) {
            return;
        }

        writer.startElement(XmlNamespaces.RAM, elementName);
        writer.writeElement(XmlNamespaces.RAM, "IssuerAssignedID", reference.getId());
        writer.writeElement(XmlNamespaces.RAM, "TypeCode", reference.getTypeCode().getCode());
        writer.writeElement(XmlNamespaces.RAM, "Name", reference.getName());

        if (reference.getIssueDate() != null) {
            writer.startElement(XmlNamespaces.RAM, "FormattedIssueDateTime");
            writer.startElement(XmlNamespaces.UDT, "DateTimeString");
            writer.writeAttribute("format", "102");
            writer.writeCharacters(reference.getIssueDate().format(DateTimeFormatter.BASIC_ISO_DATE));
            writer.endElement();
            writer.endElement();
        }

        writer.endElement();
    }
}