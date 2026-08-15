package zugferd;

import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jzugferd.ZugferdInvoiceWriter;
import io.github.jcodeforge.invoice4jzugferd.ZugferdProfile;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ZugferdInvoiceBasicwlWriterTest {

    private final ZugferdInvoiceWriter SUT = ZugferdInvoiceWriter.builder()
            .profile(ZugferdProfile.BASIC_WL)
            .prettyPrint(true)
            .build();

    @Test
    public void shouldWriteBasicwlProfile() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);

        Assert.assertNotNull(xml);
        assertTrue(xml.contains("<ram:ID>urn:factur-x.eu:1p0:basicwl</ram:ID>"));
    }

    @Test
    public void shouldWriteBasicwlInvoiceStructure() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);

        assertTrue(xml.contains("<rsm:CrossIndustryInvoice"));
        assertTrue(xml.contains("<rsm:ExchangedDocumentContext>"));
        assertTrue(xml.contains("<rsm:ExchangedDocument>"));
        assertTrue(xml.contains("<rsm:SupplyChainTradeTransaction>"));
        assertTrue(xml.contains("<ram:ApplicableHeaderTradeAgreement>"));
        assertTrue(xml.contains("<ram:ApplicableHeaderTradeDelivery>"));
        assertTrue(xml.contains("<ram:ApplicableHeaderTradeSettlement>"));
    }

    @Test
    public void shouldNotWriteInvoiceLines() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);

        assertFalse(xml.contains("<ram:IncludedSupplyChainTradeLineItem>"));
    }

    @Test
    public void shouldNotWritePayeeTradeParty() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);

        assertFalse(xml.contains("<ram:PayeeTradeParty>"));
    }

    @Test
    public void shouldNotWriteSellerOrderReference() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);

        assertFalse(xml.contains("<ram:SellerOrderReferencedDocument>"));
    }

    @Test
    public void shouldNotWritePaymentMeansInformation() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);

        assertFalse(xml.contains("<ram:Information>"));
    }

    @Test
    public void shouldNotWriteCreditorFinancialInstitution() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);

        assertFalse(xml.contains("<ram:PayeeSpecifiedCreditorFinancialInstitution>"));
    }

    @Test
    public void shouldNotWriteBankAccountName() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);

        assertFalse(xml.contains("<ram:AccountName>"));
    }

    @Test
    public void shouldWritePayeeFinancialAccount() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);

        assertTrue(xml.contains("<ram:PayeePartyCreditorFinancialAccount>"));
        assertTrue(xml.contains("<ram:IBANID>"));
    }

    @Test
    public void shouldWriteBasicwlMonetarySummation() {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);

        assertTrue(xml.contains("<ram:SpecifiedTradeSettlementHeaderMonetarySummation>"));
        assertTrue(xml.contains("<ram:TaxBasisTotalAmount>"));
        assertTrue(xml.contains("<ram:TaxTotalAmount"));
        assertTrue(xml.contains("<ram:GrandTotalAmount>"));
        assertTrue(xml.contains("<ram:DuePayableAmount>"));
    }

    @Test
    public void shouldWriteWellFormedXml() throws Exception {
        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true);
        factory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
    }
}
