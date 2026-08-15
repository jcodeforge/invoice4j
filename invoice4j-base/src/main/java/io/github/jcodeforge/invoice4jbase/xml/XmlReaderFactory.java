package io.github.jcodeforge.invoice4jbase.xml;

import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public final class XmlReaderFactory {

    public static XmlReader create(InputStream inputStream) {
        Objects.requireNonNull(inputStream, "inputStream must not be null");

        try {
            DocumentBuilderFactory factory = createFactory();

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            return new XmlReader(document, new XmlNamespaceContext());

        } catch (Exception e) {
            throw new DeserializationException("Unable to create XML reader.", e);
        }
    }

    public static XmlReader create(String xml) {
        Objects.requireNonNull(xml, "xml must not be null");
        return create(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
    }

    private static DocumentBuilderFactory createFactory() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true);

        // Secure XML processing
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);

        return factory;
    }
}