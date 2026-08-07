package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.DocumentTypeCode;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;

import java.net.URI;
import java.time.LocalDate;

/**
 * Reference to another business document.
 *
 * Used for:
 * - Billing references (BG-3)
 * - Additional supporting documents (BG-24)
 * - External document references
 */
public class DocumentReference {
    /**
     * Document identifier.
     *
     * Example:
     * INV-2025-001
     * PO-12345
     */
    private String id;

    /**
     * Type of referenced document.
     */
    private DocumentTypeCode typeCode;

    /**
     * Human-readable document name.
     */
    private String name;

    /**
     * Description of the referenced document.
     */
    private String description;

    /**
     * Issue date of the referenced document.
     */
    private LocalDate issueDate;

    /**
     * URI to the document.
     */
    private URI uri;

    /**
     * Optional attachment.
     */
    private Attachment attachment;

    private DocumentReference() {
    }

    public String getId() {
        return id;
    }

    public DocumentTypeCode getTypeCode() {
        return typeCode;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public URI getUri() {
        return uri;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final DocumentReference documentReference;

        private Builder() {
            this.documentReference = new DocumentReference();
        }

        public Builder id(String id) {
            documentReference.id = id;
            return this;
        }

        public Builder typeCode(DocumentTypeCode typeCode) {
            documentReference.typeCode = typeCode;
            return this;
        }

        public Builder name(String name) {
            documentReference.name = name;
            return this;
        }

        public Builder description(String description) {
            documentReference.description = description;
            return this;
        }

        public Builder issueDate(LocalDate issueDate) {
            documentReference.issueDate = issueDate;
            return this;
        }

        public Builder uri(URI uri) {
            documentReference.uri = uri;
            return this;
        }

        public Builder attachment(Attachment attachment) {
            documentReference.attachment = attachment;
            return this;
        }

        public DocumentReference build() {
            if (documentReference.id == null || documentReference.id.isBlank()) {
                throw new InvoiceValidationException("Document reference identifier is required.");
            }
            // Optional strings
            if (documentReference.name != null && documentReference.name.isBlank()) {
                throw new InvoiceValidationException("Document reference name must not be blank.");
            }
            if (documentReference.description != null && documentReference.description.isBlank()) {
                throw new InvoiceValidationException("Document reference description must not be blank.");
            }
            if (documentReference.issueDate != null && documentReference.issueDate.isAfter(LocalDate.now())) {
                throw new InvoiceValidationException("Document reference issue date must not be in the future.");
            }
            if (documentReference.uri != null) {
                if (!documentReference.uri.isAbsolute()) {
                    throw new InvoiceValidationException("Document reference URI must be absolute.");
                }
                if (documentReference.uri.getScheme() == null) {
                    throw new InvoiceValidationException("Document reference URI scheme is required.");
                }
            }

            return documentReference;
        }
    }
}
