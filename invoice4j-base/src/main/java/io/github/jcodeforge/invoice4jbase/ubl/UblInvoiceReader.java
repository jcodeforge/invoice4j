package io.github.jcodeforge.invoice4jbase.ubl;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import io.github.jcodeforge.invoice4jbase.ubl.parser.InvoiceParser;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;
import io.github.jcodeforge.invoice4jbase.xml.XmlReaderFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Reads UBL 2.1 Invoice XML documents and converts them
 * into {@link Invoice} domain objects.
 *
 * <p>Create instances using the {@link Builder}.</p>
 */
public final class UblInvoiceReader {

    private final UblConfigurationOptions options;

    private final InvoiceParser invoiceParser;

    private UblInvoiceReader(UblConfigurationOptions options) {
        this.invoiceParser = new InvoiceParser();
        this.options = options;
    }

    /**
     * Creates a new builder for configuring a {@link UblInvoiceReader}.
     *
     * @return a new reader builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for creating {@link UblInvoiceReader} instances.
     */
    public static final class Builder {

        private final UblConfigurationOptions.Builder options = UblConfigurationOptions.builder();

        /**
         * Builds a new {@link UblInvoiceReader}.
         *
         * @return a new reader instance
         */
        public UblInvoiceReader build() {
            return new UblInvoiceReader(options.build());
        }
    }

    /**
     * Reads an invoice from the given input stream.
     *
     * @param inputStream the XML input stream
     * @return the parsed invoice
     * @throws DeserializationException if the invoice cannot be read
     */
    public Invoice read(InputStream inputStream) {
        Objects.requireNonNull(inputStream, "inputStream must not be null");

        try {
            byte[] bytes = inputStream.readAllBytes();

            XmlReader reader = XmlReaderFactory.create(new ByteArrayInputStream(bytes));

            return invoiceParser.parse(reader, "/ubl:Invoice");

        } catch (Exception e) {
            throw new DeserializationException("Unable to read UBL invoice from input stream.", e);
        }
    }

    /**
     * Reads an invoice from a UBL XML file.
     *
     * @param file the XML file to read
     * @return the parsed invoice
     * @throws NullPointerException if {@code file} is {@code null}
     * @throws DeserializationException if the invoice cannot be read
     */
    public Invoice readFromFile(File file) {
        Objects.requireNonNull(file, "file must not be null");

        try (InputStream in = new FileInputStream(file)) {
            return read(in);

        } catch (IOException e) {
            throw new DeserializationException("Unable to read UBL invoice from file.", e);
        }
    }

    /**
     * Reads an invoice from a UBL XML string.
     *
     * @param xml the XML document
     * @return the parsed invoice
     * @throws NullPointerException if {@code xml} is {@code null}
     * @throws DeserializationException if the invoice cannot be read
     */
    public Invoice readFromString(String xml) {
        Objects.requireNonNull(xml, "xml must not be null");

        if (xml.isBlank()) {
            throw new DeserializationException("XML document must not be empty.");
        }

        try (InputStream in = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))) {
            return read(in);

        } catch (IOException e) {
            throw new DeserializationException("Unable to read UBL invoice from string.", e);
        }
    }
}