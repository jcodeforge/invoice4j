package io.github.jcodeforge.invoice4jbase.cii;

import io.github.jcodeforge.invoice4jbase.cii.serializer.InvoiceSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.exceptions.SerializationException;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriterFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class CiiInvoiceWriter {

    private final CiiConfigurationOptions options;

    private final InvoiceSerializer invoiceSerializer;
    /**
     * Writes {@link Invoice} instances as UN/CEFACT Cross Industry Invoice (CII)
     * XML documents.
     *
     * <p>A writer instance is immutable and thread-safe after construction.
     * Configure a writer using the {@link Builder}.</p>
     *
     * <pre>{@code
     * CiiInvoiceWriter writer = CiiInvoiceWriter.builder()
     *         .profile(CiiProfile.EN16931)
     *         .prettyPrint(true)
     *         .build();
     *
     * writer.writeToFile(invoice, new File("invoice.xml"));
     * }</pre>
     */
    private CiiInvoiceWriter(CiiConfigurationOptions options) {
        this.options = Objects.requireNonNull(options, "options must not be null");
        this.invoiceSerializer = new InvoiceSerializer(options);
    }

    /**
     * Builder for creating immutable {@link CiiInvoiceWriter} instances.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final CiiConfigurationOptions.Builder options = CiiConfigurationOptions.builder();

        /**
         * Sets the CII profile used during serialization.
         *
         * @param profile the target CII profile
         * @return this builder
         */
        public Builder profile(CiiProfile profile) {
            options.profile(profile);
            return this;
        }

        /**
         * Enables or disables pretty printed XML output.
         *
         * @param prettyPrint {@code true} to enable formatted XML
         * @return this builder
         */
        public Builder prettyPrint(boolean prettyPrint) {
            options.prettyPrint(prettyPrint);
            return this;
        }

        /**
         * Builds a new {@link CiiInvoiceWriter}.
         *
         * @return a configured writer instance
         */
        public CiiInvoiceWriter build() {
            return new CiiInvoiceWriter(options.build());
        }
    }

    private void write(Invoice invoice, OutputStream outputStream) {
        Objects.requireNonNull(invoice, "invoice must not be null");

        try (XmlWriter writer = XmlWriterFactory.create(outputStream)) {
            invoiceSerializer.serialize(writer, invoice);
            writer.flush();

        } catch (Exception e) {
            throw new SerializationException("Unable to write invoice to output stream.", e);
        }
    }

    /**
     * Writes an invoice to the specified file.
     *
     * <p>The output format depends on the configured writer options. If
     * pretty printing is enabled, the generated XML will be formatted with
     * indentation.</p>
     *
     * @param invoice the invoice to serialize
     * @param file the destination file
     *
     * @throws NullPointerException if {@code invoice} or {@code file} is
     *                              {@code null}
     * @throws SerializationException if the invoice cannot be written
     */
    public void writeToFile(Invoice invoice, File file) {
        Objects.requireNonNull(invoice, "invoice must not be null");
        Objects.requireNonNull(file, "file must not be null");

        try {
            if (!options.isPrettyPrint()) {
                try (OutputStream out = new FileOutputStream(file)) {
                    write(invoice, out);
                }

                return;
            }

            prettyPrint(writeToString(invoice), file);

        } catch (Exception e) {
            throw new SerializationException("Unable to write invoice to file.", e);
        }
    }

    /**
     * Serializes an invoice to a UTF-8 encoded XML string.
     *
     * @param invoice the invoice to serialize
     *
     * @return the generated XML document
     *
     * @throws NullPointerException if {@code invoice} is {@code null}
     * @throws SerializationException if serialization fails
     */
    public String writeToString(Invoice invoice) {
        Objects.requireNonNull(invoice, "invoice must not be null");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        write(invoice, out);

        String xml = out.toString(StandardCharsets.UTF_8);

        if (options.isPrettyPrint()) {
            return prettyPrintToString(xml);
        }

        return xml;
    }

    /**
     * Formats an XML document using indentation and writes it to a file.
     *
     * @param xml the XML document
     * @param file the destination file
     *
     * @throws SerializationException if formatting fails
     */
    private void prettyPrint(String xml, File file) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(file));

        } catch (Exception e) {
            throw new SerializationException("Unable to pretty print XML.", e);
        }
    }

    private String prettyPrintToString(String xml) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            StringWriter result = new StringWriter();

            transformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(result));

            return result.toString();

        } catch (Exception e) {
            throw new SerializationException("Unable to pretty print XML.", e);
        }
    }
}