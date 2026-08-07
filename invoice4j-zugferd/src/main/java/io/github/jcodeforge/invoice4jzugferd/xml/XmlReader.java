package io.github.jcodeforge.invoice4jzugferd.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class XmlReader {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.BASIC_ISO_DATE;

    private final Document document;

    private final XPath xpath;

    public XmlReader(Document document, NamespaceContext namespaceContext) {
        this.document = document;
        XPathFactory factory = XPathFactory.newInstance();
        this.xpath = factory.newXPath();
        this.xpath.setNamespaceContext(namespaceContext);
    }

    public Document getDocument() {
        return document;
    }

    public String readString(String expression) {
        try {
            String value = xpath.evaluate(expression, document);
            return value == null || value.isBlank() ? null : value;
        } catch (XPathExpressionException e) {
            throw new XmlException("Unable to evaluate XPath: " + expression, e);
        }
    }

    public BigDecimal readDecimal(String expression) {
        String value = readString(expression);
        return value == null ? null : new BigDecimal(value);
    }

    public Integer readInteger(String expression) {
        String value = readString(expression);
        return value == null ? null : Integer.valueOf(value);
    }

    public Boolean readBoolean(String expression) {
        String value = readString(expression);
        return value == null ? null : Boolean.valueOf(value);
    }

    public LocalDate readDate(String expression) {
        String value = readString(expression);
        return value == null ? null : LocalDate.parse(value, DATE_FORMAT);
    }

    public Node readNode(String expression) {
        try {
            return (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new XmlException("Unable to evaluate XPath: " + expression, e);
        }
    }

    public NodeList readNodes(String expression) {
        try {
            return (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new XmlException("Unable to evaluate XPath: " + expression, e);
        }
    }

    public String readAttribute(String expression, String attributeName) {
        Node node = readNode(expression);

        if (node == null || node.getAttributes() == null) {
            return null;
        }

        Node attribute = node.getAttributes().getNamedItem(attributeName);

        return attribute == null ? null : attribute.getNodeValue();
    }

    /**
     * Returns whether an XML node exists.
     *
     * @param expression the XPath expression
     * @return {@code true} if the node exists; {@code false} otherwise
     */
    public boolean exists(String expression) {
        return readNode(expression) != null;
    }

    /**
     * Returns whether an XML element is missing or contains no text.
     *
     * @param expression the XPath expression
     * @return {@code true} if the element is missing or blank
     */
    public boolean isEmpty(String expression) {
        String value = readString(expression);
        return value == null || value.isBlank();
    }

    /**
     * Returns the number of nodes matching an XPath expression.
     *
     * @param expression the XPath expression
     * @return the number of matching nodes
     */
    public int count(String expression) {
        NodeList nodes = readNodes(expression);
        return nodes == null ? 0 : nodes.getLength();
    }

    /**
     * Returns whether one or more nodes match an XPath expression.
     *
     * @param expression the XPath expression
     * @return {@code true} if at least one node exists
     */
    public boolean hasNodes(String expression) {
        return count(expression) > 0;
    }

    public List<String> readPaths(String expression) {
        NodeList nodeList = readNodes(expression);

        List<String> paths = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            paths.add(expression + "[" + (i + 1) + "]");
        }

        return paths;
    }

    public <T> T readEnum(String expression, Function<String, T> mapper) {
        String value = readString(expression);

        return value == null ? null : mapper.apply(value);
    }
}