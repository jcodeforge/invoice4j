package zugferd;

import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdInvoiceWriter;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdProfile;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ZugferdInvoiceBasicWriterTest {

    private final ZugferdInvoiceWriter SUT = ZugferdInvoiceWriter.builder()
            .profile(ZugferdProfile.BASIC)
            .prettyPrint(true)
            .build();

    @Test
    public void shouldWriteBasicProfile() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());
        String xml = SUT.writeToString(invoice);

        Assert.assertNotNull(xml);
        assertTrue(xml.contains(
                "<ram:ID>urn:cen.eu:en16931:2017#compliant#urn:factur-x.eu:1p0:basic</ram:ID>"
        ));
    }

    @Test
    public void shouldNotSerializeUnsupportedBasicElements() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);

        assertFalse(xml.contains("PayeeTradeParty"));
        assertFalse(xml.contains("SellerOrderReferencedDocument"));
        assertFalse(xml.contains("AdditionalReferencedDocument"));
        assertFalse(xml.contains("SpecifiedProcuringProject"));
    }

    @Test
    public void shouldUseBasicGuideline() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        String xml = SUT.writeToString(invoice);
        assertTrue(xml.contains("urn:factur-x.eu:1p0:basic"));
    }

    @Test
    public void shouldSerializeRequiredBasicParties() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());
        String xml = SUT.writeToString(invoice);

        assertTrue(xml.contains("<ram:SellerTradeParty>"));
        assertTrue(xml.contains("<ram:BuyerTradeParty>"));
    }

    @Test
    public void shouldSerializeBasicPaymentMeans() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());
        String xml = SUT.writeToString(invoice);

        assertTrue(xml.contains("<ram:SpecifiedTradeSettlementPaymentMeans>"));
        assertTrue(xml.contains("<ram:TypeCode>30</ram:TypeCode>"));
        assertTrue(xml.contains("<ram:PayeePartyCreditorFinancialAccount>"));
        assertTrue(xml.contains("<ram:IBANID>DE89370400440532013000</ram:IBANID>"));

        // BASIC must not contain these payment fields
        assertFalse(xml.contains("<ram:AccountName>"));
        assertFalse(xml.contains("<ram:PayeeSpecifiedCreditorFinancialInstitution>"));
    }

    @Test
    public void shouldSerializeBasicMonetarySummation() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());
        String xml = SUT.writeToString(invoice);

        assertTrue(xml.contains("<ram:LineTotalAmount>200.00</ram:LineTotalAmount>"));
        assertTrue(xml.contains("<ram:TaxBasisTotalAmount>190.00</ram:TaxBasisTotalAmount>"));
        assertTrue(xml.contains("<ram:TaxTotalAmount currencyID=\"EUR\">24.10</ram:TaxTotalAmount>"));
        assertTrue(xml.contains("<ram:GrandTotalAmount>214.10</ram:GrandTotalAmount>"));
    }
}
