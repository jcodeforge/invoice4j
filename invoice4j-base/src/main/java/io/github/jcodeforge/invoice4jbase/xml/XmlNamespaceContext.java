package io.github.jcodeforge.invoice4jbase.xml;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import java.util.Iterator;
import java.util.Map;

/**
 * Namespace context used for XPath evaluation of invoice XML documents.
 *
 * <p>Maps the namespace prefixes used throughout the library to their
 * corresponding namespace URIs.</p>
 */
public class XmlNamespaceContext implements NamespaceContext {

    private static final Map<String, String> NAMESPACES = Map.of(
            // CII
            "rsm", XmlNamespaces.RSM,
            "ram", XmlNamespaces.RAM,
            "udt", XmlNamespaces.UDT,
            "qdt", XmlNamespaces.QDT,

            // UBL
            "ubl", XmlNamespaces.UBL_INVOICE,
            "cac", XmlNamespaces.UBL_CAC,
            "cbc", XmlNamespaces.UBL_CBC
    );

    @Override
    public String getNamespaceURI(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("Prefix must not be null.");
        }

        return NAMESPACES.getOrDefault(prefix, XMLConstants.NULL_NS_URI);
    }

    @Override
    public String getPrefix(String namespaceURI) {
        return NAMESPACES.entrySet().stream()
                .filter(e -> e.getValue().equals(namespaceURI))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Iterator<String> getPrefixes(String namespaceURI) {
        String prefix = getPrefix(namespaceURI);

        if (prefix == null) {
            return java.util.Collections.emptyIterator();
        }

        return java.util.Collections.singleton(prefix).iterator();
    }
}