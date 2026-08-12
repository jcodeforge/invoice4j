package zugferd;

import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdProfile;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdProfileDetector;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ZugferdProfileDetectorTest {

    private final ZugferdProfileDetector SUT = new ZugferdProfileDetector();

    @Test
    public void shouldDetectMinimumProfile() {
        String xml = createXml("urn:factur-x.eu:1p0:minimum");
        assertEquals(ZugferdProfile.MINIMUM, SUT.detect(xml));
    }

    @Test
    public void shouldDetectBasicwlProfile() {
        String xml = createXml("urn:factur-x.eu:1p0:basicwl");
        assertEquals(ZugferdProfile.BASIC_WL, SUT.detect(xml));
    }

    @Test
    public void shouldDetectBasicProfile() {
        String xml = createXml("urn:cen.eu:en16931:2017#compliant#urn:factur-x.eu:1p0:basic");
        assertEquals(ZugferdProfile.BASIC, SUT.detect(xml));
    }

    @Test
    public void shouldDetectEn16931Profile() {
        String xml = createXml("urn:cen.eu:en16931:2017");
        assertEquals(ZugferdProfile.EN16931, SUT.detect(xml));
    }

    @Test
    public void shouldDetectExtendedProfile() {
        String xml = createXml("urn:cen.eu:en16931:2017#conformant#urn:factur-x.eu:1p0:extended");
        assertEquals(ZugferdProfile.EXTENDED, SUT.detect(xml));
    }

    @Test(expected = DeserializationException.class)
    public void shouldRejectUnknownProfile() {
        String xml = createXml(
                "urn:factur-x.eu:1p0:unknown"
        );

        SUT.detect(xml);
    }

    @Test(expected = DeserializationException.class)
    public void shouldRejectMissingProfile() {
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                        + "<rsm:CrossIndustryInvoice "
                        + "xmlns:rsm=\"urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100\" "
                        + "xmlns:ram=\"urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100\">"
                        + "<rsm:ExchangedDocumentContext/>"
                        + "</rsm:CrossIndustryInvoice>";

        SUT.detect(xml);
    }

    private String createXml(String guidelineId) {
        return """
                <?xml version="1.0" encoding="UTF-8"?>
                <rsm:CrossIndustryInvoice
                    xmlns:rsm="urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100"
                    xmlns:ram="urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100">
                    <rsm:ExchangedDocumentContext>
                        <ram:GuidelineSpecifiedDocumentContextParameter>
                            <ram:ID>%s</ram:ID>
                        </ram:GuidelineSpecifiedDocumentContextParameter>
                    </rsm:ExchangedDocumentContext>
                </rsm:CrossIndustryInvoice>
                """.formatted(guidelineId);
    }

    @Test(expected = DeserializationException.class)
    public void shouldRejectMalformedXml() {
        SUT.detect("<rsm:CrossIndustryInvoice>");
    }
}