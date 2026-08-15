package io.github.jcodeforge.invoice4jxr;

import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;
import io.github.jcodeforge.invoice4jbase.xml.XmlReaderFactory;
import java.util.Objects;

public class XrProfileDetector {

    public XrProfile detect(String xml) {
        Objects.requireNonNull(xml, "xml must not be null");

        XmlReader reader = XmlReaderFactory.create(xml);

        String guidelineId = reader.readString(
                "/rsm:CrossIndustryInvoice"
                        + "/rsm:ExchangedDocumentContext"
                        + "/ram:GuidelineSpecifiedDocumentContextParameter"
                        + "/ram:ID"
        );

        if (guidelineId == null || guidelineId.isBlank()) {
            throw new DeserializationException("Unable to detect XRechnung profile: guideline ID is missing.");
        }

        guidelineId = guidelineId.trim();

        for (XrProfile profile : XrProfile.values()) {
            if (profile.getGuidelineId().equals(guidelineId)) {
                return profile;
            }
        }

        throw new DeserializationException("Unsupported XRechnung profile: " + guidelineId);
    }
}
