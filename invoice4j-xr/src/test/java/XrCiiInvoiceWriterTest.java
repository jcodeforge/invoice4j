import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jxr.XrCiiInvoiceWriter;
import io.github.jcodeforge.invoice4jxr.XrProfile;
import org.junit.Test;

import static org.junit.Assert.*;

public class XrCiiInvoiceWriterTest {

    private final XrCiiInvoiceWriter SUT = XrCiiInvoiceWriter.builder()
            .profile(XrProfile.XRECHNUNG)
            .prettyPrint(true)
            .build();

    @Test
    public void shouldWriteXRechnungCii() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);

        assertNotNull(xml);
        assertTrue(xml.contains("<rsm:CrossIndustryInvoice"));
        assertTrue(xml.contains("urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0"));
        assertTrue(xml.contains("urn:fdc:peppol.eu:2017:poacc:billing:01:1.0"));
        assertTrue(xml.contains("<ram:ID>" + invoice.getInvoiceNumber() + "</ram:ID>"));
        assertTrue(xml.contains("<ram:TypeCode>380</ram:TypeCode>"));
        assertTrue(xml.contains("<ram:InvoiceCurrencyCode>" + invoice.getCurrency().getCode() +
                "</ram:InvoiceCurrencyCode>"));
    }

    @Test
    public void shouldWriteXRechnungCiiContext() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);

        assertTrue(xml.contains("<ram:BusinessProcessSpecifiedDocumentContextParameter>"));
        assertTrue(xml.contains("<ram:GuidelineSpecifiedDocumentContextParameter>"));
        assertTrue(xml.contains("urn:fdc:peppol.eu:2017:poacc:billing:01:1.0"));
        assertTrue(xml.contains("urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0"));
    }
}
