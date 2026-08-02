package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.net.URI;

/**
 * Embedded or externally referenced document attachment.
 *
 * Supports either:
 * - embedded binary content
 * - an external URI
 */
public class Attachment {
    /**
     * File name.
     */
    private String fileName;

    /**
     * MIME type.
     */
    private MimeType mimeType;

    /**
     * Embedded binary data.
     */
    private byte[] content;

    /**
     * External URI instead of embedded data.
     */
    private URI uri;

    private Attachment() {
    }

    public String getFileName() {
        return fileName;
    }

    public MimeType getMimeType() {
        return mimeType;
    }

    public byte[] getContent() {
        return content == null ? null : content.clone();
    }

    public URI getUri() {
        return uri;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Attachment attachment;

        private Builder() {
            this.attachment = new Attachment();
        }

        public Builder fileName(String fileName) {
            attachment.fileName = fileName;
            return this;
        }

        public Builder mimeType(MimeType mimeType) {
            attachment.mimeType = mimeType;
            return this;
        }

        public Builder content(byte[] content) {
            attachment.content = content;
            return this;
        }

        public Builder uri(URI uri) {
            attachment.uri = uri;
            return this;
        }

        public Attachment build() {
            if (attachment.fileName == null || attachment.fileName.isBlank()) {
                throw new InvoiceValidationException("Attachment file name is required.");
            }
            if (attachment.mimeType == null) {
                throw new InvoiceValidationException("Attachment MIME type is required.");
            }
            // Either embedded content or external URI
            if (attachment.content == null && attachment.uri == null) {
                throw new InvoiceValidationException("Either attachment content or a URI must be provided.");
            }
            // Not both
            if (attachment.content != null && attachment.uri != null) {
                throw new InvoiceValidationException("Attachment cannot contain both embedded content and a URI.");
            }
            // Embedded content
            if (attachment.content != null && attachment.content.length == 0) {
                throw new InvoiceValidationException("Attachment content must not be empty.");
            }
            if (attachment.uri != null) {
                if (!attachment.uri.isAbsolute()) {
                    throw new InvoiceValidationException("Attachment URI must be absolute.");
                }
            }

            return attachment;
        }
    }
}
