package factory;

import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

public final class TestXPathFactory {

    public static XPath createXPath() {
        XPath xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext(new XmlNamespaceContext());
        return xpath;
    }
}
