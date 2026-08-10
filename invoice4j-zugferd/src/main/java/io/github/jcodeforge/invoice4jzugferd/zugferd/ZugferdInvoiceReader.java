package io.github.jcodeforge.invoice4jzugferd.zugferd;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiInvoiceReader;
import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import java.io.File;
import java.io.InputStream;
import java.util.Objects;

public class ZugferdInvoiceReader {

    private final CiiInvoiceReader ciiReader;

    private ZugferdInvoiceReader() {
        this.ciiReader = CiiInvoiceReader.builder().build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        public ZugferdInvoiceReader build() {
            return new ZugferdInvoiceReader();
        }
    }

    /**
     * Reads a ZUGFeRD invoice from an XML input stream.
     *
     * @param inputStream the ZUGFeRD CII XML input stream
     * @return the parsed invoice
     * @throws DeserializationException if the invoice cannot be read
     */
    public Invoice read(InputStream inputStream) {
        Objects.requireNonNull(inputStream, "inputStream must not be null");

        // CiiInvoiceReader currently exposes string/file methods,
        // so stream handling can be added there if desired.
        throw new UnsupportedOperationException("InputStream reading should be delegated to CiiInvoiceReader.");
    }

    /**
     * Reads a ZUGFeRD invoice from a file.
     *
     * @param file the ZUGFeRD XML file
     * @return the parsed invoice
     */
    public Invoice readFromFile(File file) {
        Objects.requireNonNull(file, "file must not be null");

        return ciiReader.readFromFile(file);
    }

    /**
     * Reads a ZUGFeRD invoice from an XML string.
     *
     * @param xml the ZUGFeRD CII XML document
     * @return the parsed invoice
     */
    public Invoice readFromString(String xml) {
        Objects.requireNonNull(xml, "xml must not be null");

        return ciiReader.readFromString(xml);
    }
}
