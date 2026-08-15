package io.github.jcodeforge.invoice4jxr;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.cii.CiiInvoiceReader;
import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import java.io.File;
import java.io.InputStream;
import java.util.Objects;

public class XrCiiInvoiceReader {

    private final CiiInvoiceReader ciiReader;

    private final XrProfileDetector profileDetector = new XrProfileDetector();

    private XrCiiInvoiceReader() {
        this.ciiReader = CiiInvoiceReader.builder().build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        public XrCiiInvoiceReader build() {
            return new XrCiiInvoiceReader();
        }
    }

    /**
     * Reads a Xrechnung invoice from an XML input stream.
     *
     * @param inputStream the Xrechnung CII XML input stream
     * @return the parsed invoice
     * @throws DeserializationException if the invoice cannot be read
     */
    public Invoice read(InputStream inputStream) {
        Objects.requireNonNull(inputStream, "inputStream must not be null");
        return ciiReader.read(inputStream);
    }

    /**
     * Reads a Xrechnung invoice from a file.
     *
     * @param file the Xrechnung XML file
     * @return the parsed invoice
     */
    public Invoice readFromFile(File file) {
        Objects.requireNonNull(file, "file must not be null");

        return ciiReader.readFromFile(file);
    }

    /**
     * Reads a Xrechnung invoice from an XML string.
     *
     * @param xml the Xrechnung CII XML document
     * @return the parsed invoice
     */
    public Invoice readFromString(String xml) {
        Objects.requireNonNull(xml, "xml must not be null");

        return ciiReader.readFromString(xml);
    }

    public XrProfile detectProfile(String xml) {
        Objects.requireNonNull(xml, "xml must not be null");

        return profileDetector.detect(xml);
    }
}

