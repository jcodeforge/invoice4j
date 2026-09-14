package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Attachment;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.DocumentReference;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;
import java.util.Base64;

public final class DocumentReferenceSerializer implements XmlSerializer<DocumentReference> {

    @Override
    public void serialize(XmlWriter writer, DocumentReference reference) {
        if (reference == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "AdditionalDocumentReference"
        );

        writeId(writer, reference);
        writeIssueDate(writer, reference);
        writeTypeCode(writer, reference);
        writeDescriptions(writer, reference);
        writeAttachment(writer, reference);

        writer.endElement();
    }

    public void serializeBillingReference(XmlWriter writer, DocumentReference reference) {
        if (reference == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "BillingReference"
        );

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "InvoiceDocumentReference"
        );

        writeId(writer, reference);
        writeIssueDate(writer, reference);

        writer.endElement(); // InvoiceDocumentReference
        writer.endElement(); // BillingReference
    }

    private void writeId(XmlWriter writer, DocumentReference reference) {
        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "ID",
                reference.getId()
        );
    }

    private void writeIssueDate(XmlWriter writer, DocumentReference reference) {
        if (reference.getIssueDate() == null) {
            return;
        }

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "IssueDate",
                reference.getIssueDate().toString()
        );
    }

    private void writeTypeCode(XmlWriter writer, DocumentReference reference) {
        if (reference.getTypeCode() == null) {
            return;
        }

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "DocumentTypeCode",
                reference.getTypeCode().getCode()
        );
    }

    private void writeDescriptions(XmlWriter writer, DocumentReference reference) {
        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "DocumentDescription",
                reference.getName()
        );

        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "DocumentDescription",
                reference.getDescription()
        );
    }

    private void writeAttachment(XmlWriter writer, DocumentReference reference) {
        Attachment attachment = reference.getAttachment();

        if (attachment == null && reference.getUri() == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "Attachment"
        );

        if (attachment != null) {
            writeAttachmentContent(writer, attachment);
        } else {
            writeExternalReference(writer, reference.getUri().toString());
        }

        writer.endElement();
    }

    private void writeAttachmentContent(XmlWriter writer, Attachment attachment) {
        if (attachment.getContent() != null) {

            writer.startElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "EmbeddedDocumentBinaryObject"
            );

            writer.writeAttribute(
                    "mimeCode",
                    attachment.getMimeType().value()
            );

            writer.writeAttribute(
                    "filename",
                    attachment.getFileName()
            );

            writer.writeCharacters(Base64.getEncoder().encodeToString(attachment.getContent()));

            writer.endElement();

            return;
        }

        if (attachment.getUri() != null) {
            writeExternalReference(writer, attachment.getUri().toString());
        }
    }

    private void writeExternalReference(XmlWriter writer, String uri) {
        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "ExternalReference"
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "URI",
                uri
        );

        writer.endElement();
    }
}