package io.github.jcodeforge.invoice4jbase.cii.serializer;

import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public interface XmlSerializer<T> {

    void serialize(XmlWriter writer, T object);
}