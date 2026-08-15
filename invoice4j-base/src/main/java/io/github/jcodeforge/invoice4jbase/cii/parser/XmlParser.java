package io.github.jcodeforge.invoice4jbase.cii.parser;

import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public interface XmlParser<T>  {

    T parse(XmlReader reader, String basePath);
}
