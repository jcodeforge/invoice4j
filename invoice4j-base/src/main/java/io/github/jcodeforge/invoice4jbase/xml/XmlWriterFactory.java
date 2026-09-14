package io.github.jcodeforge.invoice4jbase.xml;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.OutputStream;
import io.github.jcodeforge.invoice4jbase.Invoice4jBaseConstants;

public final class XmlWriterFactory {

    public static XmlWriter createForCii(OutputStream outputStream) {
        try {
            XMLOutputFactory factory = XMLOutputFactory.newFactory();
            XMLStreamWriter writer = factory.createXMLStreamWriter(outputStream,
                    Invoice4jBaseConstants.STANDARD_ENCODING_UTF_8);

            writer.setPrefix("rsm", XmlNamespaces.RSM);
            writer.setPrefix("ram", XmlNamespaces.RAM);
            writer.setPrefix("udt", XmlNamespaces.UDT);
            writer.setPrefix("qdt", XmlNamespaces.QDT);

            return new XmlWriter(writer);

        } catch (Exception e) {
            throw new XmlException("Unable to create Cii XML writer.", e);
        }
    }

    public static XmlWriter createForUbl(OutputStream outputStream) {
        try {
            XMLOutputFactory factory = XMLOutputFactory.newFactory();

            XMLStreamWriter writer = factory.createXMLStreamWriter(
                    outputStream,
                    Invoice4jBaseConstants.STANDARD_ENCODING_UTF_8
            );

            writer.setPrefix("cac", XmlNamespaces.UBL_CAC);
            writer.setPrefix("cbc", XmlNamespaces.UBL_CBC);

            return new XmlWriter(writer);

        } catch (Exception e) {
            throw new XmlException("Unable to create UBL XML writer.", e);
        }
    }
}