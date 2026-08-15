package io.github.jcodeforge.invoice4jbase.cii;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import io.github.jcodeforge.invoice4jbase.cii.parser.InvoiceParser;
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
 * Reads EN16931 Cross Industry Invoice (CII) XML documents and converts them
 * into {@link Invoice} domain objects.
 *
 * <p>Create instances using the {@link Builder}.</p>
 */
public final class CiiInvoiceReader {

    private final CiiConfigurationOptions options;

    private final InvoiceParser invoiceParser;

    private CiiInvoiceReader(CiiConfigurationOptions options) {
        this.invoiceParser = new InvoiceParser();
        this.options = options;
    }

    /**
     * Creates a new builder for configuring a {@link CiiInvoiceReader}.
     *
     * @return a new reader builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for creating {@link CiiInvoiceReader} instances.
     */
    public static final class Builder {

        private final CiiConfigurationOptions.Builder options = CiiConfigurationOptions.builder();

        /**
         * Builds a new {@link CiiInvoiceReader}.
         *
         * @return a new reader instance
         */
        public CiiInvoiceReader build() {
            return new CiiInvoiceReader(options.build());
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

            return invoiceParser.parse(reader, "/rsm:CrossIndustryInvoice");

        } catch (Exception e) {
            throw new DeserializationException("Unable to read invoice from input stream.", e);
        }
    }

    /**
     * Reads an invoice from a CII XML file.
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
            throw new DeserializationException("Unable to read invoice from file.", e);
        }
    }

    /**
     * Reads an invoice from a CII XML string.
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
            throw new DeserializationException("Unable to read invoice from string.", e);
        }
    }
}