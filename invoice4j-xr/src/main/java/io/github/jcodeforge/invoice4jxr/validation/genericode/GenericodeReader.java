package io.github.jcodeforge.invoice4jxr.validation.genericode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class GenericodeReader {

    private static final String GENERICODE_NAMESPACE =
            "http://docs.oasis-open.org/codelist/ns/genericode/1.0/";

    public GenericodeCodeList read(InputStream input) {
        if (input == null) {
            throw new IllegalArgumentException("Input stream must not be null.");
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setNamespaceAware(true);

            factory.setFeature(
                    "http://apache.org/xml/features/disallow-doctype-decl",
                    true
            );

            factory.setFeature(
                    "http://xml.org/sax/features/external-general-entities",
                    false
            );

            factory.setFeature(
                    "http://xml.org/sax/features/external-parameter-entities",
                    false
            );

            factory.setFeature(
                    "http://apache.org/xml/features/nonvalidating/load-external-dtd",
                    false
            );

            factory.setXIncludeAware(false);
            factory.setExpandEntityReferences(false);

            factory.setAttribute(
                    XMLConstants.ACCESS_EXTERNAL_DTD,
                    ""
            );

            factory.setAttribute(
                    XMLConstants.ACCESS_EXTERNAL_SCHEMA,
                    ""
            );

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(input);

            Element root = document.getDocumentElement();

            validateRoot(root);

            String shortName = readChildText(root, "ShortName");
            String longName = readChildText(root, "LongName");
            String version = readChildText(root);
            String canonicalUri = readChildText(root, "CanonicalUri");
            String canonicalVersionUri =
                    readChildText(root, "CanonicalVersionUri");

            String codeColumn = findCodeColumn(root);

            List<String> values = readCodeValues(root, codeColumn);

            if (values.isEmpty()) {
                throw new IllegalArgumentException("Genericode code list does not contain any code values.");
            }

            return new GenericodeCodeList(shortName, longName, version, canonicalUri, canonicalVersionUri, codeColumn,
                    values);

        } catch (Exception exception) {
            if (exception instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) exception;
            }

            throw new IllegalArgumentException("Unable to read Genericode code list.", exception);
        }
    }

    public GenericodeCodeList readResource(String resource) {
        String resourcePath = resource.startsWith("/") ? resource.substring(1) : resource;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        InputStream input = classLoader.getResourceAsStream(resourcePath);

        if (input == null) {
            throw new IllegalArgumentException("Genericode resource not found: " + resource);
        }

        try (input) {
            return read(input);
        } catch (Exception exception) {
            if (exception instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) exception;
            }

            throw new IllegalArgumentException("Unable to read Genericode resource: " + resource, exception);
        }
    }

    private void validateRoot(Element root) {
        if (root == null || !"CodeList".equals(root.getLocalName())
                || !GENERICODE_NAMESPACE.equals(root.getNamespaceURI())) {

            throw new IllegalArgumentException("Input is not a valid Genericode CodeList.");
        }
    }

    private String findCodeColumn(Element root) {
        NodeList keys = root.getElementsByTagName("Key");

        for (int i = 0; i < keys.getLength(); i++) {
            Element key = (Element) keys.item(i);

            NodeList annotations = key.getElementsByTagName("AppInfo");

            for (int j = 0; j < annotations.getLength(); j++) {
                Element appInfo = (Element) annotations.item(j);

                NodeList recommendedColumns =
                        appInfo.getElementsByTagName("empfohleneCodeSpalte");

                if (recommendedColumns.getLength() > 0) {
                    NodeList columnRefs =
                            key.getElementsByTagName("ColumnRef");

                    if (columnRefs.getLength() > 0) {
                        Element columnRef =
                                (Element) columnRefs.item(0);

                        String ref = columnRef.getAttribute("Ref");

                        if (!ref.isBlank()) {
                            return ref;
                        }
                    }
                }
            }
        }

        /*
         * Fallback:
         *
         * If the code-list does not explicitly mark a recommended
         * code column, use the first ColumnRef of the first Key.
         */
        NodeList keysFallback = root.getElementsByTagName("Key");

        if (keysFallback.getLength() > 0) {
            Element key = (Element) keysFallback.item(0);

            NodeList columnRefs =
                    key.getElementsByTagName("ColumnRef");

            if (columnRefs.getLength() > 0) {
                Element columnRef =
                        (Element) columnRefs.item(0);

                String ref = columnRef.getAttribute("Ref");

                if (!ref.isBlank()) {
                    return ref;
                }
            }
        }

        throw new IllegalArgumentException("Genericode code list does not define a code column.");
    }

    private List<String> readCodeValues(Element root, String codeColumn) {
        NodeList rows = root.getElementsByTagName("Row");

        List<String> values = new ArrayList<>();

        for (int i = 0; i < rows.getLength(); i++) {
            Element row = (Element) rows.item(i);

            NodeList rowValues = row.getElementsByTagName("Value");

            for (int j = 0; j < rowValues.getLength(); j++) {
                Element value = (Element) rowValues.item(j);

                String columnRef = value.getAttribute("ColumnRef");

                if (!codeColumn.equals(columnRef)) {
                    continue;
                }

                NodeList simpleValues =
                        value.getElementsByTagName("SimpleValue");

                if (simpleValues.getLength() == 0) {
                    continue;
                }

                String code = simpleValues.item(0)
                        .getTextContent()
                        .trim();

                if (!code.isEmpty()) {
                    values.add(code);
                }

                break;
            }
        }

        return Collections.unmodifiableList(values);
    }

    private String readChildText(Element parent, String elementName) {
        NodeList nodes = parent.getElementsByTagName(elementName);

        if (nodes.getLength() == 0) {
            return null;
        }

        String value = nodes.item(0)
                .getTextContent()
                .trim();

        return value.isEmpty() ? null : value;
    }

    private String readChildText(Element root) {
        return readChildText(root, "Version");
    }
}