import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import io.github.jcodeforge.invoice4jxr.XrProfile;
import io.github.jcodeforge.invoice4jxr.XrProfileDetector;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XrProfileDetectorTest {

    private final XrProfileDetector SUT = new XrProfileDetector();

    @Test
    public void shouldDetectXRechnungProfile() {
        String xml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <rsm:CrossIndustryInvoice
                        xmlns:rsm="urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100"
                        xmlns:ram="urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100">

                    <rsm:ExchangedDocumentContext>
                        <ram:GuidelineSpecifiedDocumentContextParameter>
                            <ram:ID>
                                urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0
                            </ram:ID>
                        </ram:GuidelineSpecifiedDocumentContextParameter>
                    </rsm:ExchangedDocumentContext>

                </rsm:CrossIndustryInvoice>
                """;

        assertEquals(XrProfile.XRECHNUNG, SUT.detect(xml));
    }

    @Test(expected = DeserializationException.class)
    public void shouldRejectMissingGuidelineId() {
        String xml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <rsm:CrossIndustryInvoice
                        xmlns:rsm="urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100"
                        xmlns:ram="urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100">

                    <rsm:ExchangedDocumentContext>
                    </rsm:ExchangedDocumentContext>

                </rsm:CrossIndustryInvoice>
                """;

        SUT.detect(xml);
    }

    @Test(expected = DeserializationException.class)
    public void shouldRejectUnsupportedGuidelineId() {
        String xml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <rsm:CrossIndustryInvoice
                        xmlns:rsm="urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100"
                        xmlns:ram="urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100">

                    <rsm:ExchangedDocumentContext>
                        <ram:GuidelineSpecifiedDocumentContextParameter>
                            <ram:ID>unsupported-profile</ram:ID>
                        </ram:GuidelineSpecifiedDocumentContextParameter>
                    </rsm:ExchangedDocumentContext>

                </rsm:CrossIndustryInvoice>
                """;

        SUT.detect(xml);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullXml() {
        SUT.detect(null);
    }
}