package factory;

import cii.CiiNamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

public final class TestXPathFactory {

    public static XPath createXPath() {
        XPath xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext(new CiiNamespaceContext());
        return xpath;
    }
}
