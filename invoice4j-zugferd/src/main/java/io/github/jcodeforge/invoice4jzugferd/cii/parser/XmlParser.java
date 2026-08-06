package io.github.jcodeforge.invoice4jzugferd.cii.parser;

import io.github.jcodeforge.invoice4jzugferd.xml.XmlReader;

public interface XmlParser<T>  {

    T parse(XmlReader reader, String basePath);
}
