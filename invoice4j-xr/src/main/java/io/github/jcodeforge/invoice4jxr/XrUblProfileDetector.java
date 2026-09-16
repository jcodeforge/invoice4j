package io.github.jcodeforge.invoice4jxr;

import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;
import io.github.jcodeforge.invoice4jbase.xml.XmlReaderFactory;
import java.util.Objects;

public class XrUblProfileDetector {

    public XrProfile detect(String xml) {
        Objects.requireNonNull(xml, "xml must not be null");

        XmlReader reader = XmlReaderFactory.create(xml);

        String customizationId = reader.readString("/Invoice/cbc:CustomizationID");

        if (customizationId == null || customizationId.isBlank()) {
            throw new DeserializationException("Unable to detect XRechnung UBL profile: customization ID is missing.");
        }

        customizationId = customizationId.trim();

        for (XrProfile profile : XrProfile.values()) {
            if (profile.getGuidelineId().equals(customizationId)) {
                return profile;
            }
        }

        throw new DeserializationException("Unsupported XRechnung UBL profile: " + customizationId);
    }
}