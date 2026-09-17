package io.github.jcodeforge.invoice4jxr;

import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XrUblProfileDetectorTest {

    private final XrUblProfileDetector SUT = new XrUblProfileDetector();

    @Test
    public void shouldDetectXRechnungProfile() {
        String xml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <Invoice
                        xmlns="urn:oasis:names:specification:ubl:schema:xsd:Invoice-2"
                        xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2"
                        xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2">

                    <cbc:CustomizationID>
                        urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0
                    </cbc:CustomizationID>

                </Invoice>
                """;

        assertEquals(XrProfile.XRECHNUNG, SUT.detect(xml));
    }

    @Test(expected = DeserializationException.class)
    public void shouldRejectMissingCustomizationId() {
        String xml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <Invoice
                        xmlns="urn:oasis:names:specification:ubl:schema:xsd:Invoice-2"
                        xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2"
                        xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2">
                </Invoice>
                """;

        SUT.detect(xml);
    }

    @Test(expected = DeserializationException.class)
    public void shouldRejectUnsupportedCustomizationId() {
        String xml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <Invoice
                        xmlns="urn:oasis:names:specification:ubl:schema:xsd:Invoice-2"
                        xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2"
                        xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2">

                    <cbc:CustomizationID>unsupported-profile</cbc:CustomizationID>

                </Invoice>
                """;

        SUT.detect(xml);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullXml() {
        SUT.detect(null);
    }
}