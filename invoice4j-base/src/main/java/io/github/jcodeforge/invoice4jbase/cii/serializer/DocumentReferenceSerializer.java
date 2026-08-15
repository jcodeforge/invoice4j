package io.github.jcodeforge.invoice4jbase.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.DocumentReference;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;
import java.time.format.DateTimeFormatter;

public final class DocumentReferenceSerializer implements XmlSerializer<DocumentReference> {

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


        if ("InvoiceReferencedDocument".equals(elementName)) {

            if (reference.getIssueDate() != null) {
                writer.startElement(
                        XmlNamespaces.RAM,
                        "FormattedIssueDateTime"
                );

                writer.startElement(
                        XmlNamespaces.UDT,
                        "DateTimeString"
                );

                writer.writeAttribute(
                        "format",
                        "102"
                );

                writer.writeCharacters(
                        reference.getIssueDate()
                                .format(DateTimeFormatter.BASIC_ISO_DATE)
                );

                writer.endElement(); // DateTimeString
                writer.endElement(); // FormattedIssueDateTime
            }

        } else {
            // AdditionalReferencedDocument
            if (reference.getTypeCode() != null) {
                writer.writeElement(
                        XmlNamespaces.RAM,
                        "TypeCode",
                        reference.getTypeCode().getCode()
                );
            }

            writer.writeOptionalElement(
                    XmlNamespaces.RAM,
                    "Name",
                    reference.getName()
            );

            if (reference.getIssueDate() != null) {
                writer.startElement(
                        XmlNamespaces.RAM,
                        "FormattedIssueDateTime"
                );

                writer.startElement(
                        XmlNamespaces.UDT,
                        "DateTimeString"
                );

                writer.writeAttribute(
                        "format",
                        "102"
                );

                writer.writeCharacters(
                        reference.getIssueDate()
                                .format(DateTimeFormatter.BASIC_ISO_DATE)
                );

                writer.endElement();
                writer.endElement();
            }
        }

        writer.endElement();
    }
}