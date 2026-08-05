package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;

public interface XmlSerializer<T> {

    void serialize(XmlWriter writer, T object);
}