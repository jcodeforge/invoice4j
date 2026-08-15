package io.github.jcodeforge.invoice4jbase.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import io.github.jcodeforge.invoice4jbase.invoice4jBaseConstants;

public class XmlWriter implements AutoCloseable {

    private final XMLStreamWriter writer;

    public XmlWriter(XMLStreamWriter writer) {
        this.writer = writer;
    }

    public void startDocument() {
        try {
            writer.writeStartDocument(invoice4jBaseConstants.STANDARD_ENCODING_UTF_8, "1.0");
        } catch (XMLStreamException e) {
            throw new XmlException("Unable to start XML document.", e);
        }
    }

    public void endDocument() {
        try {
            writer.flush();
        } catch (XMLStreamException e) {
            throw new XmlException("Unable to finish XML document.", e);
        }
    }

    public void startElement(String namespace, String localName) {
        try {
            writer.writeStartElement(namespace, localName);
        } catch (XMLStreamException e) {
            throw new XmlException("Unable to write start element.", e);
        }
    }

    public void endElement() {
        try {
            writer.writeEndElement();
        } catch (XMLStreamException e) {
            throw new XmlException("Unable to write end element.", e);
        }
    }

    public void writeElement(String namespace, String localName, String value) {
        if (value == null || value.isBlank()) {
            return;
        }

        startElement(namespace, localName);

        try {
            writer.writeCharacters(value);
        } catch (XMLStreamException e) {
            throw new XmlException("Unable to write element value.", e);
        }

        endElement();
    }

    public void writeAttribute(String name, String value) {
        if (value == null || value.isBlank()) {
            return;
        }

        try {
            writer.writeAttribute(name, value);
        } catch (XMLStreamException e) {
            throw new XmlException("Unable to write attribute.", e);
        }
    }

    public void writeNamespace(String prefix, String uri) {
        try {
            writer.writeNamespace(prefix, uri);
        } catch (XMLStreamException e) {
            throw new XmlException("Unable to write namespace.", e);
        }
    }

    public void writeCharacters(String value) {
        if (value == null || value.isBlank()) {
            return;
        }

        try {
            writer.writeCharacters(value);
        } catch (XMLStreamException e) {
            throw new XmlException("Unable to write text.", e);
        }
    }

    public void writeOptionalElement(String namespace, String localName, String value) {
        if (value == null || value.isBlank()) {
            return;
        }

        writeElement(namespace, localName, value);
    }

    public void writeEmptyElement(String namespace, String localName) {
        try {
            writer.writeEmptyElement(namespace, localName);
        } catch (XMLStreamException e) {
            throw new XmlException("Unable to write empty element.", e);
        }
    }

    public void writeComment(String comment) {
        if (comment == null || comment.isBlank()) {
            return;
        }

        try {
            writer.writeComment(comment);
        } catch (XMLStreamException e) {
            throw new XmlException("Unable to write XML comment.", e);
        }
    }

    public void flush() {
        try {
            writer.flush();
        } catch (XMLStreamException e) {
            throw new XmlException("Unable to flush XML writer.", e);
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (XMLStreamException e) {
            throw new XmlException("Unable to close XML writer.", e);
        }
    }

    public void writeDefaultNamespace(String namespace) {
        try {
            writer.writeDefaultNamespace(namespace);
        } catch (XMLStreamException e) {
            throw new XmlException("Unable to write default namespace.", e);
        }
    }

    public void writeProcessingInstruction(String target, String data) {
        try {
            writer.writeProcessingInstruction(target, data);
        } catch (XMLStreamException e) {
            throw new XmlException("Unable to write processing instruction.", e);
        }
    }

    public void writeCData(String value) {
        if (value == null) {
            return;
        }

        try {
            writer.writeCData(value);
        } catch (XMLStreamException e) {
            throw new XmlException("Unable to write CDATA.", e);
        }
    }
}