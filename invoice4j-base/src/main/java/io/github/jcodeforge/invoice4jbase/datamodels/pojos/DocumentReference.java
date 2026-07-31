package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.DocumentTypeCode;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DocumentTypeCode getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(DocumentTypeCode typeCode) {
        this.typeCode = typeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }
}
