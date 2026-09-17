package io.github.jcodeforge.invoice4jxr;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

public final class XrInvoiceReader {

    private final XrUblInvoiceReader ublReader;
    private final XrCiiInvoiceReader ciiReader;

    private XrInvoiceReader(Builder builder) {
        this.ublReader = XrUblInvoiceReader.builder()
                .validate(builder.validate)
                .build();

        this.ciiReader = XrCiiInvoiceReader.builder()
                .validate(builder.validate)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public Invoice readFromFile(File file) {
        Objects.requireNonNull(file, "file must not be null");

        try {
            String xml = Files.readString(file.toPath(), StandardCharsets.UTF_8);

            return readFromString(xml);

        } catch (IOException e) {
            throw new DeserializationException("Unable to read XRechnung invoice from file.", e);
        }
    }

    public Invoice readFromString(String xml) {
        Objects.requireNonNull(xml, "xml must not be null");

        if (xml.isBlank()) {
            throw new DeserializationException("XML document must not be empty.");
        }

        XrFormat format = detectFormat(xml);

        return switch (format) {
            case UBL -> ublReader.readFromString(xml);
            case CII -> ciiReader.readFromString(xml);
        };
    }

    private XrFormat detectFormat(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(
                    new InputSource(new StringReader(xml))
            );

            Element root = document.getDocumentElement();

            String localName = root.getLocalName();
            String namespace = root.getNamespaceURI();

            if ("Invoice".equals(localName)
                    && "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2".equals(namespace)) {
                return XrFormat.UBL;
            }

            if ("CrossIndustryInvoice".equals(localName) && namespace != null
                    && namespace.contains("CrossIndustryInvoice")) {
                return XrFormat.CII;
            }

            throw new DeserializationException("Unsupported XRechnung XML format.");

        } catch (DeserializationException e) {
            throw e;
        } catch (Exception e) {
            throw new DeserializationException("Unable to detect XRechnung XML format.", e);
        }
    }

    public static final class Builder {

        private boolean validate = true;

        public Builder validate(boolean validate) {
            this.validate = validate;
            return this;
        }

        public XrInvoiceReader build() {
            return new XrInvoiceReader(this);
        }
    }
}
