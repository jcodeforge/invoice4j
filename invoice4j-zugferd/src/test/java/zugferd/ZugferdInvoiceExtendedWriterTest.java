package zugferd;

import factory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdInvoiceWriter;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdProfile;
import org.junit.Assert;
import org.junit.Test;

public class ZugferdInvoiceExtendedWriterTest {

    private final ZugferdInvoiceWriter SUT = ZugferdInvoiceWriter.builder()
            .profile(ZugferdProfile.EXTENDED)
            .prettyPrint(true)
            .build();

    @Test
    public void shouldWriteExtendedInvoiceToString() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());

        String xml = SUT.writeToString(invoice);

        Assert.assertNotNull(xml);
        Assert.assertFalse(xml.isBlank());
        Assert.assertTrue(xml.contains("<ram:ID>urn:cen.eu:en16931:2017#conformant#urn:factur-x.eu:1p0:extended</ram:ID>"));
    }

    @Test
    public void shouldWriteExtendedInvoiceStructure() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createMinimalInvoice());

        String xml = SUT.writeToString(invoice);

        Assert.assertTrue(xml.contains("<rsm:CrossIndustryInvoice"));
        Assert.assertTrue(xml.contains("<rsm:ExchangedDocumentContext>"));
        Assert.assertTrue(xml.contains("<rsm:ExchangedDocument>"));
        Assert.assertTrue(xml.contains("<rsm:SupplyChainTradeTransaction>"));
        Assert.assertTrue(xml.contains("<ram:ApplicableHeaderTradeAgreement>"));
        Assert.assertTrue(xml.contains("<ram:ApplicableHeaderTradeDelivery>"));
        Assert.assertTrue(xml.contains("<ram:ApplicableHeaderTradeSettlement>"));
    }

    @Test
    public void shouldWriteInvoiceLines() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);

        Assert.assertTrue(xml.contains("<ram:IncludedSupplyChainTradeLineItem>"));
    }

    @Test
    public void shouldWriteExtendedReferences() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);

        Assert.assertTrue(xml.contains("<ram:AdditionalReferencedDocument>"));
        Assert.assertTrue(xml.contains("<ram:SpecifiedProcuringProject>"));
    }

    @Test
    public void shouldWriteCompleteExtendedInvoice() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);

        Assert.assertNotNull(xml);
        Assert.assertFalse(xml.isBlank());
        Assert.assertTrue(xml.contains("<ram:IncludedSupplyChainTradeLineItem>"));
        Assert.assertTrue(xml.contains("<ram:ApplicableHeaderTradeAgreement>"));
        Assert.assertTrue(xml.contains("<ram:ApplicableHeaderTradeDelivery>"));
        Assert.assertTrue(xml.contains("<ram:ApplicableHeaderTradeSettlement>"));
    }
}