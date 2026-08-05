package io.github.jcodeforge.invoice4jzugferd;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jzugferd.cii.serializer.InvoiceSerializer;
import io.github.jcodeforge.invoice4jbase.exceptions.SerializationException;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriterFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class CiiInvoiceWriter {

    private final InvoiceSerializer invoiceSerializer = new InvoiceSerializer();

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

        try (OutputStream out = new FileOutputStream(file)) {
            write(invoice, out);
        } catch (IOException e) {
            throw new SerializationException("Unable to write invoice to file.", e);
        }
    }

    public String writeToString(Invoice invoice) {
        Objects.requireNonNull(invoice, "invoice must not be null");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        write(invoice, out);

        return out.toString(StandardCharsets.UTF_8);
    }
}