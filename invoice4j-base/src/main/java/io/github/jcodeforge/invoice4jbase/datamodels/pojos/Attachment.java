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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MimeType getMimeType() {
        return mimeType;
    }

    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }
}
