package zugferd;

import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jzugferd.ZugferdProfile;
import io.github.jcodeforge.invoice4jzugferd.ZugferdXmpMetadata;
import org.junit.Test;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class ZugferdXmpMetadataTest {

    @Test
    public void shouldCreateFacturXMetadata() throws Exception {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xmp = new ZugferdXmpMetadata().create(invoice, ZugferdProfile.EXTENDED);

        assertNotNull(xmp);
        assertFalse(xmp.isBlank());

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        Document document = factory.newDocumentBuilder()
                .parse(
                        new ByteArrayInputStream(
                                xmp.getBytes(StandardCharsets.UTF_8)
                        )
                );

        String facturXNamespace = "urn:factur-x:pdfa:CrossIndustryDocument:invoice:1p0#";

        // Factur-X metadata
        assertEquals(
                "INVOICE",
                document.getElementsByTagNameNS(
                        facturXNamespace,
                        "DocumentType"
                ).item(0).getTextContent().trim()
        );

        assertEquals(
                "factur-x.xml",
                document.getElementsByTagNameNS(
                        facturXNamespace,
                        "DocumentFileName"
                ).item(0).getTextContent().trim()
        );

        assertEquals(
                "1.0",
                document.getElementsByTagNameNS(
                        facturXNamespace,
                        "Version"
                ).item(0).getTextContent().trim()
        );

        assertEquals(
                "EXTENDED",
                document.getElementsByTagNameNS(
                        facturXNamespace,
                        "ConformanceLevel"
                ).item(0).getTextContent().trim()
        );

        // PDF/A extension schema
        assertTrue(
                document.getElementsByTagNameNS(
                        "http://www.aiim.org/pdfa/ns/extension/",
                        "schemas"
                ).getLength() > 0
        );

        assertTrue(
                document.getElementsByTagNameNS(
                        "http://www.aiim.org/pdfa/ns/schema#",
                        "schema"
                ).getLength() > 0
        );

        assertTrue(
                document.getElementsByTagNameNS(
                        "http://www.aiim.org/pdfa/ns/schema#",
                        "namespaceURI"
                ).getLength() > 0
        );

        assertTrue(
                document.getElementsByTagNameNS(
                        "http://www.aiim.org/pdfa/ns/schema#",
                        "prefix"
                ).getLength() > 0
        );
    }
}
