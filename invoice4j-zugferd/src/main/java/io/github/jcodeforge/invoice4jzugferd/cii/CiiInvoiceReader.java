package io.github.jcodeforge.invoice4jzugferd.cii;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import io.github.jcodeforge.invoice4jzugferd.cii.parser.InvoiceParser;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlReader;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlReaderFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class CiiInvoiceReader {

    private final InvoiceParser invoiceParser;

    private CiiInvoiceReader() {
        this.invoiceParser = new InvoiceParser();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        public CiiInvoiceReader build() {
            return new CiiInvoiceReader();
        }
    }

    private Invoice read(InputStream inputStream) {
        try {
            XmlReader reader = XmlReaderFactory.create(inputStream);
            return invoiceParser.parse(reader, "/rsm:CrossIndustryInvoice");

        } catch (Exception e) {
            throw new DeserializationException("Unable to read invoice from input stream.", e);
        }
    }

    public Invoice readFromFile(File file) {
        Objects.requireNonNull(file, "file must not be null");

        try (InputStream in = new FileInputStream(file)) {
            return read(in);

        } catch (IOException e) {
            throw new DeserializationException("Unable to read invoice from file.", e);
        }
    }

    public Invoice readFromString(String xml) {
        Objects.requireNonNull(xml, "xml must not be null");

        try (InputStream in = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))) {
            return read(in);

        } catch (IOException e) {
            throw new DeserializationException("Unable to read invoice from string.", e);
        }
    }
}