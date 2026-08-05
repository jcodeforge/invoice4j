package io.github.jcodeforge.invoice4jzugferd.xml;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.OutputStream;
import io.github.jcodeforge.invoice4jbase.invoice4jBaseConstants;

public final class XmlWriterFactory {

    public static XmlWriter create(OutputStream outputStream) {
        try {
            XMLOutputFactory factory = XMLOutputFactory.newFactory();
            XMLStreamWriter writer = factory.createXMLStreamWriter(outputStream,
                    invoice4jBaseConstants.STANDARD_ENCODING_UTF_8);

            writer.setPrefix("rsm", XmlNamespaces.RSM);
            writer.setPrefix("ram", XmlNamespaces.RAM);
            writer.setPrefix("udt", XmlNamespaces.UDT);
            writer.setPrefix("qdt", XmlNamespaces.QDT);

            return new XmlWriter(writer);

        } catch (Exception e) {
            throw new XmlException("Unable to create XML writer.", e);
        }
    }
}