package io.github.jcodeforge.invoice4jbase.xml;

public interface XmlParser<T>  {

    T parse(XmlReader reader, String basePath);
}
