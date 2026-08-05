package cii;

import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import java.util.Iterator;
import java.util.Map;

public class CiiNamespaceContext implements NamespaceContext {

    private static final Map<String, String> NAMESPACES = Map.of(
            "rsm", XmlNamespaces.RSM,
            "ram", XmlNamespaces.RAM,
            "udt", XmlNamespaces.UDT,
            "qdt", XmlNamespaces.QDT
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