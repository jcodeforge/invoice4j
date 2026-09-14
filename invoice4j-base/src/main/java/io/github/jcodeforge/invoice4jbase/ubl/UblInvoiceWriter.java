package io.github.jcodeforge.invoice4jbase.ubl;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.exceptions.SerializationException;
import io.github.jcodeforge.invoice4jbase.ubl.serializer.InvoiceSerializer;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriterFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Writes {@link Invoice} instances as UBL 2.1 XML documents.
 *
 * <p>A writer instance is immutable and thread-safe after construction.
 * Configure a writer using the {@link Builder}.</p>
 *
 * <pre>{@code
 * UblInvoiceWriter writer = UblInvoiceWriter.builder()
 *         .prettyPrint(true)
 *         .build();
 *
 * writer.writeToFile(invoice, new File("invoice.xml"));
 * }</pre>
 */
public final class UblInvoiceWriter {

    private final UblConfigurationOptions options;

    private final InvoiceSerializer invoiceSerializer;

    private UblInvoiceWriter(UblConfigurationOptions options) {
        this.options = Objects.requireNonNull(options, "options must not be null");
        this.invoiceSerializer = new InvoiceSerializer(options);
    }

    /**
     * Creates a new builder.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for creating immutable {@link UblInvoiceWriter} instances.
     */
    public static final class Builder {

        private final UblConfigurationOptions.Builder options =
                UblConfigurationOptions.builder();

        /**
         * Sets the UBL profile used during serialization.
         *
         * @param profile the target UBL profile
         * @return this builder
         */
        public Builder profile(UblProfile profile) {
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
         * Builds a new {@link UblInvoiceWriter}.
         *
         * @return a configured writer instance
         */
        public UblInvoiceWriter build() {
            return new UblInvoiceWriter(options.build());
        }
    }

    private void write(Invoice invoice, OutputStream outputStream) {
        Objects.requireNonNull(invoice, "invoice must not be null");
        Objects.requireNonNull(outputStream, "outputStream must not be null");

        try (XmlWriter writer = XmlWriterFactory.createForUbl(outputStream)) {
            invoiceSerializer.serialize(writer, invoice);
            writer.flush();

        } catch (Exception e) {
            throw new SerializationException("Unable to write invoice to output stream.", e);
        }
    }

    /**
     * Writes an invoice to the specified file.
     *
     * <p>The output format depends on the configured writer options.
     * If pretty printing is enabled, the generated XML will be formatted
     * with indentation.</p>
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
     * Writes an XML document to the specified file.
     *
     * @param xml the XML document
     * @param file the destination file
     *
     * @throws NullPointerException if {@code xml} or {@code file} is
     *                              {@code null}
     * @throws SerializationException if the XML cannot be written
     */
    public void writeToFile(String xml, File file) {
        Objects.requireNonNull(xml, "xml must not be null");
        Objects.requireNonNull(file, "file must not be null");

        try {
            if (!options.isPrettyPrint()) {

                try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
                    writer.write(xml);
                }

                return;
            }

            prettyPrint(xml, file);

        } catch (Exception e) {
            throw new SerializationException("Unable to write XML to file.", e);
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