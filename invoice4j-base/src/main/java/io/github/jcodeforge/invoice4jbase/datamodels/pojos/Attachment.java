package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import java.net.URI;

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
        return content;
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
            return attachment;
        }
    }
}
