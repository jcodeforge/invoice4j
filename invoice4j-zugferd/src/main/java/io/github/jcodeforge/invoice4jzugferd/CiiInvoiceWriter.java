package io.github.jcodeforge.invoice4jzugferd;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiInvoiceWriterOptions;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiProfile;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiSerializationContext;
import io.github.jcodeforge.invoice4jzugferd.cii.serializer.InvoiceSerializer;
import io.github.jcodeforge.invoice4jbase.exceptions.SerializationException;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriterFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class CiiInvoiceWriter {

    private final CiiSerializationContext context;

    private final InvoiceSerializer invoiceSerializer;

    private CiiInvoiceWriter(CiiSerializationContext context) {
        this.context = context;
        this.invoiceSerializer = new InvoiceSerializer(context);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final CiiInvoiceWriterOptions.Builder options = CiiInvoiceWriterOptions.builder();

        public Builder profile(CiiProfile profile) {
            options.profile(profile);
            return this;
        }

        public Builder prettyPrint(boolean prettyPrint) {
            options.prettyPrint(prettyPrint);
            return this;
        }

        public CiiInvoiceWriter build() {
            return new CiiInvoiceWriter(new CiiSerializationContext(options.build()));
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

    public void writeToFile(Invoice invoice, File file) {
        Objects.requireNonNull(invoice, "invoice must not be null");
        Objects.requireNonNull(file, "file must not be null");

        try {
            if (!context.isPrettyPrint()) {
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

    public String writeToString(Invoice invoice) {
        Objects.requireNonNull(invoice, "invoice must not be null");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        write(invoice, out);

        return out.toString(StandardCharsets.UTF_8);
    }

    private void prettyPrint(String xml, File file) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(file));

        } catch (Exception e) {
            throw new SerializationException("Unable to pretty print XML.", e);
        }
    }
}