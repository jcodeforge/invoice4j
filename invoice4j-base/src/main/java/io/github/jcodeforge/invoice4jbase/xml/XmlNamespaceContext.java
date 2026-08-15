package io.github.jcodeforge.invoice4jbase.xml;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import java.util.Iterator;
import java.util.Map;

/**
 * Namespace context used for XPath evaluation of Cross Industry Invoice (CII)
 * XML documents.
 *
 * <p>Maps the namespace prefixes used throughout the library to their
 * corresponding namespace URIs.</p>
 */
public class XmlNamespaceContext implements NamespaceContext {

    private static final Map<String, String> NAMESPACES = Map.of(
            "rsm", XmlNamespaces.RSM,
            "ram", XmlNamespaces.RAM,
            "udt", XmlNamespaces.UDT,
            "qdt", XmlNamespaces.QDT
    );

    /**
     * Returns the namespace URI associated with the given prefix.
     *
     * @param prefix the namespace prefix
     * @return the namespace URI, or {@link XMLConstants#NULL_NS_URI} if the
     *         prefix is not registered
     * @throws IllegalArgumentException if {@code prefix} is {@code null}
     */
    @Override
    public String getNamespaceURI(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("Prefix must not be null.");
        }

        return NAMESPACES.getOrDefault(prefix, XMLConstants.NULL_NS_URI);
    }

    /**
     * Returns the preferred prefix for the given namespace URI.
     *
     * @param namespaceURI the namespace URI
     * @return the corresponding prefix, or {@code null} if none exists
     */
    @Override
    public String getPrefix(String namespaceURI) {
        return NAMESPACES.entrySet().stream()
                .filter(e -> e.getValue().equals(namespaceURI))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns an iterator over the prefixes associated with the given
     * namespace URI.
     *
     * @param namespaceURI the namespace URI
     * @return an iterator containing the matching prefix, or an empty iterator
     *         if none exists
     */
    @Override
    public Iterator<String> getPrefixes(String namespaceURI) {
        String prefix = getPrefix(namespaceURI);

        if (prefix == null) {
            return java.util.Collections.emptyIterator();
        }

        return java.util.Collections.singleton(prefix).iterator();
    }
}