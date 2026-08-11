package zugferd;

import factory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdInvoiceWriter;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdProfile;
import org.junit.Test;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ZugfertMinimumWriterTest {

    private final ZugferdInvoiceWriter SUT = ZugferdInvoiceWriter.builder()
            .profile(ZugferdProfile.MINIMUM)
            .prettyPrint(true)
            .build();

    @Test
    public void shouldWriteMinimumGuideline() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createMinimalInvoice());

        String xml = SUT.writeToString(invoice);

        assertTrue(xml.contains("<ram:ID>urn:factur-x.eu:1p0:minimum</ram:ID>"));
    }

    @Test
    public void shouldWriteWellFormedXml() throws Exception {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createMinimalInvoice());

        String xml = SUT.writeToString(invoice);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
    }

    @Test
    public void shouldWriteMinimumInvoiceStructure() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createMinimalInvoice());

        String xml = SUT.writeToString(invoice);

        assertTrue(xml.contains("<rsm:CrossIndustryInvoice"));
        assertTrue(xml.contains("<rsm:ExchangedDocumentContext>"));
        assertTrue(xml.contains("<rsm:ExchangedDocument>"));
        assertTrue(xml.contains("<rsm:SupplyChainTradeTransaction>"));
        assertTrue(xml.contains("<ram:ApplicableHeaderTradeAgreement>"));
        assertTrue(xml.contains("<ram:ApplicableHeaderTradeSettlement>"));
    }

    @Test
    public void shouldWriteMinimumMonetarySummation() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createMinimalInvoice());

        String xml = SUT.writeToString(invoice);

        assertTrue(xml.contains("<ram:SpecifiedTradeSettlementHeaderMonetarySummation>"));
        assertTrue(xml.contains("<ram:TaxBasisTotalAmount>"));
        assertTrue(xml.contains("<ram:GrandTotalAmount>"));
        assertTrue(xml.contains("<ram:DuePayableAmount>"));
    }

    @Test
    public void shouldNotWriteUnsupportedMinimumMonetarySummationElements() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createMinimalInvoice());

        String xml = SUT.writeToString(invoice);

        assertFalse(xml.contains("<ram:LineTotalAmount>"));
        assertFalse(xml.contains("<ram:ChargeTotalAmount>"));
        assertFalse(xml.contains("<ram:AllowanceTotalAmount>"));
        assertFalse(xml.contains("<ram:TotalPrepaidAmount>"));
    }

    @Test
    public void shouldWriteMinimumTaxTotalAmount() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);

        assertTrue(xml.contains("<ram:TaxTotalAmount"));
    }
}
