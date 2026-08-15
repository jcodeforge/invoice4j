package cii;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.DocumentTypeCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import io.github.jcodeforge.invoice4jbase.cii.CiiInvoiceWriter;
import io.github.jcodeforge.invoice4jbase.cii.CiiProfile;
import io.github.jcodeforge.invoice4jbase.testfactory.*;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.io.File;
import java.io.StringReader;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class CiiInvoiceWriterTest {

    private CiiInvoiceWriter SUT;

    @Before
    public void setUp() {
        SUT = CiiInvoiceWriter.builder()
                .profile(CiiProfile.EN16931)
                .build();
    }

    @Test
    public void shouldGenerateMinimalInvoice() {
        Invoice invoice = TestInvoiceFactory.createMinimalInvoice();
        String xml = SUT.writeToString(invoice);

        System.out.println(xml);

        assertNotNull(xml);
        assertFalse(xml.isBlank());
    }

    @Test
    public void shouldGenerateCompleteInvoice() throws Exception {
        Invoice invoice = TestInvoiceFactory.createCompleteInvoice();
        String xml = SUT.writeToString(invoice);
        Document document = parse(xml);

        assertNotNull(document);
    }

    @Test
    public void shouldWriteInvoiceToFile() throws Exception {
        Invoice invoice = TestInvoiceFactory.createMinimalInvoice();

        File file = File.createTempFile("invoice-", ".xml");
        file.deleteOnExit();

        SUT.writeToFile(invoice, file);

        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    public void shouldWriteValidXmlToFile() throws Exception {
        Invoice invoice = TestInvoiceFactory.createMinimalInvoice();

        File file = File.createTempFile("invoice-", ".xml");
        file.deleteOnExit();

        SUT.writeToFile(invoice, file);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        Document document = factory.newDocumentBuilder().parse(file);

        assertNotNull(document);
    }

    @Test
    public void shouldWriteInvoiceNumberToFile() throws Exception {
        Invoice invoice = TestInvoiceFactory.createMinimalInvoice();

        File file = File.createTempFile("invoice-", ".xml");
        file.deleteOnExit();

        SUT.writeToFile(invoice, file);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        Document document = factory.newDocumentBuilder().parse(file);

        XPath xpath = TestXPathFactory.createXPath();

        String invoiceNumber = xpath.evaluate("/rsm:CrossIndustryInvoice/rsm:ExchangedDocument/ram:ID",
                document);

        assertEquals("INV-2026-0001", invoiceNumber);
    }

    @Test
    public void shouldWriteSameContentToFileAndString() throws Exception {
        Invoice invoice = TestInvoiceFactory.createMinimalInvoice();
        String expected = SUT.writeToString(invoice);

        File file = File.createTempFile("invoice-", ".xml");
        file.deleteOnExit();

        SUT.writeToFile(invoice, file);

        String actual = Files.readString(file.toPath());

        assertEquals(expected, actual);
    }

    @Test
    public void shouldGenerateWellFormedXml() throws Exception {
        Invoice invoice = TestInvoiceFactory.createMinimalInvoice();

        String xml = SUT.writeToString(invoice);

        DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml)));
    }

    @Test
    public void shouldContainRequiredNamespaces() throws Exception {
        String xml = SUT.writeToString(TestInvoiceFactory.createMinimalInvoice());
        Document document = parse(xml);

        assertEquals("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100",
                document.getDocumentElement().getNamespaceURI());
        assertEquals("urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100",
                document.getDocumentElement().getAttribute("xmlns:ram"));
        assertEquals("urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100",
                document.getDocumentElement().getAttribute("xmlns:udt"));
    }

    @Test
    public void shouldContainCrossIndustryInvoiceRoot() throws Exception {
        Document document = parse(SUT.writeToString(TestInvoiceFactory.createMinimalInvoice()));

        assertEquals("CrossIndustryInvoice", document.getDocumentElement().getLocalName());
    }

    @Test
    public void shouldContainMandatoryDocumentStructure() throws Exception {
        Document document = parse(SUT.writeToString(TestInvoiceFactory.createMinimalInvoice()));
        XPath xpath = TestXPathFactory.createXPath();

        Node node = (Node) xpath.evaluate("/rsm:CrossIndustryInvoice/rsm:ExchangedDocumentContext", document,
                XPathConstants.NODE);

        assertNotNull(node);
        assertFalse(xpath.evaluate("/rsm:CrossIndustryInvoice/rsm:ExchangedDocument", document).isBlank());
        assertFalse(xpath.evaluate("/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction",
                document).isBlank());
    }

    @Test
    public void shouldContainSellerAndBuyer() throws Exception {
        Document document = parse(SUT.writeToString(TestInvoiceFactory.createMinimalInvoice()));
        XPath xpath = TestXPathFactory.createXPath();

        assertFalse(xpath.evaluate("/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
                        + "/ram:ApplicableHeaderTradeAgreement"
                        + "/ram:SellerTradeParty", document).isBlank());
        assertFalse(xpath.evaluate("/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
                        + "/ram:ApplicableHeaderTradeAgreement"
                        + "/ram:BuyerTradeParty", document).isBlank());
    }

    @Test
    public void shouldContainInvoiceLine() throws Exception {
        Document document = parse(SUT.writeToString(TestInvoiceFactory.createMinimalInvoice()));
        XPath xpath = TestXPathFactory.createXPath();

        assertFalse(xpath.evaluate("/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
                        + "/ram:IncludedSupplyChainTradeLineItem", document).isBlank());
    }

    @Test
    public void shouldContainTradeTax() throws Exception {
        Document document = parse(SUT.writeToString(TestInvoiceFactory.createMinimalInvoice()));
        XPath xpath = TestXPathFactory.createXPath();

        assertFalse(xpath.evaluate("/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
                        + "/ram:ApplicableHeaderTradeSettlement"
                        + "/ram:ApplicableTradeTax", document).isBlank());
    }

    @Test
    public void shouldContainMonetarySummation() throws Exception {
        Document document = parse(SUT.writeToString(TestInvoiceFactory.createMinimalInvoice()));
        XPath xpath = TestXPathFactory.createXPath();

        assertFalse(xpath.evaluate("/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
                        + "/ram:ApplicableHeaderTradeSettlement"
                        + "/ram:SpecifiedTradeSettlementHeaderMonetarySummation", document).isBlank());
    }

    @Test
    public void shouldWriteInvoiceNumber() throws Exception {
        String xml = SUT.writeToString(TestInvoiceFactory.createMinimalInvoice());
        Document document = parse(xml);

        XPath xpath = TestXPathFactory.createXPath();
        String value = xpath.evaluate("/rsm:CrossIndustryInvoice/rsm:ExchangedDocument/ram:ID", document);

        assertEquals("INV-2026-0001", value);
    }

    @Test
    public void shouldWriteIssueDate() throws Exception {
        String xml = SUT.writeToString(TestInvoiceFactory.createMinimalInvoice());

        Document document = parse(xml);
        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate(
                "/rsm:CrossIndustryInvoice/rsm:ExchangedDocument/ram:IssueDateTime/udt:DateTimeString", document);

        assertEquals("20260101", value);
    }

    private Document parse(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        return factory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
    }

    @Test
    public void shouldWriteCurrency() throws Exception {
        String xml = SUT.writeToString(TestInvoiceFactory.createMinimalInvoice());

        Document document = parse(xml);
        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate("/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
                        + "/ram:ApplicableHeaderTradeSettlement"
                        + "/ram:InvoiceCurrencyCode", document);

        assertEquals("EUR", value);
    }

    @Test
    public void shouldWriteSellerName() throws Exception {
        String xml = SUT.writeToString(TestInvoiceFactory.createMinimalInvoice());

        Document document = parse(xml);
        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate("/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
                        + "/ram:ApplicableHeaderTradeAgreement"
                        + "/ram:SellerTradeParty"
                        + "/ram:Name", document);

        assertEquals("Müller & Söhne GmbH", value);
    }

    @Test
    public void shouldWriteBuyerName() throws Exception {
        String xml = SUT.writeToString(TestInvoiceFactory.createMinimalInvoice());

        Document document = parse(xml);
        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate("/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
                        + "/ram:ApplicableHeaderTradeAgreement"
                        + "/ram:BuyerTradeParty"
                        + "/ram:Name", document);

        assertEquals("Müller & Söhne GmbH", value);
    }

    @Test
    public void shouldWriteInvoiceLineId() throws Exception {
        String xml = SUT.writeToString(TestInvoiceFactory.createMinimalInvoice());

        Document document = parse(xml);
        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate("/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
                        + "/ram:IncludedSupplyChainTradeLineItem"
                        + "/ram:AssociatedDocumentLineDocument"
                        + "/ram:LineID", document);

        assertEquals("1", value);
    }

    @Test
    public void shouldWriteInvoiceLineName() throws Exception {
        String xml = SUT.writeToString(TestInvoiceFactory.createMinimalInvoice());

        Document document = parse(xml);
        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate("/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
                        + "/ram:IncludedSupplyChainTradeLineItem"
                        + "/ram:SpecifiedTradeProduct"
                        + "/ram:Name", document);

        assertEquals("Item", value);
    }

    @Test
    public void shouldWriteTaxRate() throws Exception {
        String xml = SUT.writeToString(TestInvoiceFactory.createMinimalInvoice());

        Document document = parse(xml);
        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate("/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
                        + "/ram:ApplicableHeaderTradeSettlement"
                        + "/ram:ApplicableTradeTax"
                        + "/ram:RateApplicablePercent", document);

        assertEquals("19", value);
    }

    @Test
    public void shouldWriteGrandTotal() throws Exception {
        String xml = SUT.writeToString(TestInvoiceFactory.createMinimalInvoice());

        Document document = parse(xml);
        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate("/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
                        + "/ram:ApplicableHeaderTradeSettlement"
                        + "/ram:SpecifiedTradeSettlementHeaderMonetarySummation"
                        + "/ram:GrandTotalAmount", document);

        assertEquals("119.00", value);
    }

    @Test
    public void shouldProduceSameXmlTwice() {
        Invoice invoice = TestInvoiceFactory.createMinimalInvoice();

        String xml1 = SUT.writeToString(invoice);
        String xml2 = SUT.writeToString(invoice);

        assertEquals(xml1, xml2);
    }

    @Test
    public void shouldProduceUtf8() throws Exception {
        Invoice invoice = TestInvoiceFactory.createMinimalInvoice();
        String xml = SUT.writeToString(invoice);
        System.out.println(xml);

        Document document = parse(xml);
        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate("/rsm:CrossIndustryInvoice"
                + "/rsm:SupplyChainTradeTransaction"
                + "/ram:ApplicableHeaderTradeAgreement"
                + "/ram:SellerTradeParty"
                + "/ram:Name", document);

        assertEquals("Müller & Söhne GmbH", value);
    }

    @Test
    public void shouldNotWriteBuyerReferenceWhenNull() throws Exception {
        Invoice invoice = TestInvoiceFactory.createMinimalInvoice();
        String xml = SUT.writeToString(invoice);
        Document document = parse(xml);

        XPath xpath = TestXPathFactory.createXPath();

        String exists = xpath.evaluate("count(/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
                        + "/ram:ApplicableHeaderTradeAgreement"
                        + "/ram:BuyerReference)", document);

        assertEquals("0", exists);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullInvoiceForWriteToString() {
        SUT.writeToString(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullInvoiceForWriteToFile() {
        File file = new File("dummy.xml");
        SUT.writeToFile(null, file);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullFile() {
        SUT.writeToFile(TestInvoiceFactory.createMinimalInvoice(), null);
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectInvoiceWithoutInvoiceNumber() {
        Invoice invoice = Invoice.builder()
                .documentTypeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .issueDate(LocalDate.of(2026, 1, 1))
                .currency(CurrencyCode.EUR)
                .seller(TestPartyFactory.createSeller())
                .buyer(TestPartyFactory.createBuyer())
                .delivery(TestDeliveryFactory.createDelivery())
                .paymentMeans(TestPaymentFactory.createPaymentMeans())
                .paymentTerms(TestPaymentFactory.createPaymentTerms())
                .invoicePeriod(TestPartyFactory.createInvoicePeriod())
                .allowanceCharges(List.of())
                .lines(List.of(TestInvoiceLineFactory.createFirstInvoiceLine()))
                .taxes(List.of(TestTaxFactory.createTax()))
                .monetarySummation(TestMonetarySummationFactory.createMonetarySummation())
                .build();

        SUT.writeToString(invoice);
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectInvoiceWithoutIssueDate() {
        Invoice invoice = Invoice.builder()
                .invoiceNumber("INV-2026-0001")
                .documentTypeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .currency(CurrencyCode.EUR)
                .seller(TestPartyFactory.createSeller())
                .buyer(TestPartyFactory.createBuyer())
                .delivery(TestDeliveryFactory.createDelivery())
                .paymentMeans(TestPaymentFactory.createPaymentMeans())
                .paymentTerms(TestPaymentFactory.createPaymentTerms())
                .invoicePeriod(TestPartyFactory.createInvoicePeriod())
                .allowanceCharges(List.of())
                .lines(List.of(TestInvoiceLineFactory.createFirstInvoiceLine()))
                .taxes(List.of(TestTaxFactory.createTax()))
                .monetarySummation(TestMonetarySummationFactory.createMonetarySummation())
                .build();

        SUT.writeToString(invoice);
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectInvoiceWithoutCurrency() {
        Invoice invoice = Invoice.builder()
                .invoiceNumber("INV-2026-0001")
                .documentTypeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .issueDate(LocalDate.of(2026, 1, 1))
                .seller(TestPartyFactory.createSeller())
                .buyer(TestPartyFactory.createBuyer())
                .delivery(TestDeliveryFactory.createDelivery())
                .paymentMeans(TestPaymentFactory.createPaymentMeans())
                .paymentTerms(TestPaymentFactory.createPaymentTerms())
                .invoicePeriod(TestPartyFactory.createInvoicePeriod())
                .allowanceCharges(List.of())
                .lines(List.of(TestInvoiceLineFactory.createFirstInvoiceLine()))
                .taxes(List.of(TestTaxFactory.createTax()))
                .monetarySummation(TestMonetarySummationFactory.createMonetarySummation())
                .build();

        SUT.writeToString(invoice);
    }

    @Test
    public void shouldWriteMultipleInvoiceLines() throws Exception {
        Invoice invoice = TestInvoiceFactory.createCompleteInvoice();
        Document document = parse(SUT.writeToString(invoice));
        XPath xpath = TestXPathFactory.createXPath();

        String count = xpath.evaluate("count(/rsm:CrossIndustryInvoice"
                        + "/rsm:SupplyChainTradeTransaction"
                        + "/ram:IncludedSupplyChainTradeLineItem)", document);

        assertEquals("2", count);
    }

    @Test
    public void shouldWriteSecondInvoiceLine() throws Exception {
        Invoice invoice = TestInvoiceFactory.createCompleteInvoice();
        Document document = parse(SUT.writeToString(invoice));
        XPath xpath = TestXPathFactory.createXPath();

        String id = xpath.evaluate("/rsm:CrossIndustryInvoice"
                        + "/rsm:SupplyChainTradeTransaction"
                        + "/ram:IncludedSupplyChainTradeLineItem[2]"
                        + "/ram:AssociatedDocumentLineDocument"
                        + "/ram:LineID", document);

        assertEquals("2", id);
    }

    @Test
    public void shouldWriteAllowance() throws Exception {
        Invoice invoice = TestInvoiceFactory.createCompleteInvoice();
        Document document = parse(SUT.writeToString(invoice));
        XPath xpath = TestXPathFactory.createXPath();

        String count = xpath.evaluate("count(/rsm:CrossIndustryInvoice"
                        + "/rsm:SupplyChainTradeTransaction"
                        + "/ram:ApplicableHeaderTradeSettlement"
                        + "/ram:SpecifiedTradeAllowanceCharge)", document);

        assertEquals("1", count);
    }

    @Test
    public void shouldWriteAllowanceAndCharge() throws Exception {
        Invoice invoice = TestInvoiceFactory.createInvoiceWithAllowanceAndCharge();
        Document document = parse(SUT.writeToString(invoice));
        XPath xpath = TestXPathFactory.createXPath();

        String count = xpath.evaluate("count(/rsm:CrossIndustryInvoice"
                        + "/rsm:SupplyChainTradeTransaction"
                        + "/ram:ApplicableHeaderTradeSettlement"
                        + "/ram:SpecifiedTradeAllowanceCharge)", document);

        assertEquals("2", count);
    }

    @Test
    public void shouldNotWriteNotesWhenCollectionIsEmpty() throws Exception {
        Invoice invoice = TestInvoiceFactory.createMinimalInvoice();
        Document document = parse(SUT.writeToString(invoice));
        XPath xpath = TestXPathFactory.createXPath();

        String count = xpath.evaluate("count(/rsm:CrossIndustryInvoice"
                        + "/rsm:ExchangedDocument"
                        + "/ram:IncludedNote)", document);

        assertEquals("0", count);
    }

    @Test
    public void shouldNotWriteAllowanceChargesWhenCollectionIsEmpty() throws Exception {
        Invoice invoice = TestInvoiceFactory.createMinimalInvoice();
        Document document = parse(SUT.writeToString(invoice));
        XPath xpath = TestXPathFactory.createXPath();

        String count = xpath.evaluate("count(/rsm:CrossIndustryInvoice"
                        + "/rsm:SupplyChainTradeTransaction"
                        + "/ram:ApplicableHeaderTradeSettlement"
                        + "/ram:SpecifiedTradeAllowanceCharge)", document);

        assertEquals("0", count);
    }

    @Test
    public void shouldNotWriteBillingReferencesWhenCollectionIsEmpty() throws Exception {
        Invoice invoice = TestInvoiceFactory.createMinimalInvoice();
        Document document = parse(SUT.writeToString(invoice));
        XPath xpath = TestXPathFactory.createXPath();

        String count = xpath.evaluate("count(/rsm:CrossIndustryInvoice"
                        + "/rsm:SupplyChainTradeTransaction"
                        + "/ram:ApplicableHeaderTradeSettlement"
                        + "/ram:InvoiceReferencedDocument)", document);

        assertEquals("0", count);
    }

    @Test
    public void shouldNotWriteAdditionalDocumentsWhenCollectionIsEmpty() throws Exception {
        Invoice invoice = TestInvoiceFactory.createMinimalInvoice();
        Document document = parse(SUT.writeToString(invoice));
        XPath xpath = TestXPathFactory.createXPath();

        String count = xpath.evaluate("count(/rsm:CrossIndustryInvoice"
                        + "/rsm:SupplyChainTradeTransaction"
                        + "/ram:ApplicableHeaderTradeSettlement"
                        + "/ram:AdditionalReferencedDocument)", document);

        assertEquals("0", count);
    }

    @Test
    public void shouldNotWriteTradeTaxWhenCollectionIsEmpty() throws Exception {
        Invoice invoice = Invoice.builder()
                .invoiceNumber("INV-2026-0001")
                .documentTypeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .issueDate(LocalDate.of(2026, 1, 1))
                .currency(CurrencyCode.EUR)
                .seller(TestPartyFactory.createSeller())
                .buyer(TestPartyFactory.createBuyer())
                .delivery(TestDeliveryFactory.createDelivery())
                .paymentMeans(TestPaymentFactory.createPaymentMeans())
                .paymentTerms(TestPaymentFactory.createPaymentTerms())
                .invoicePeriod(TestPartyFactory.createInvoicePeriod())
                .allowanceCharges(List.of())
                .lines(List.of(TestInvoiceLineFactory.createFirstInvoiceLine()))
                .taxes(List.of())
                .monetarySummation(TestMonetarySummationFactory.createMonetarySummation())
                .build();

        Document document = parse(SUT.writeToString(invoice));
        XPath xpath = TestXPathFactory.createXPath();

        String count = xpath.evaluate("count(/rsm:CrossIndustryInvoice"
                        + "/rsm:SupplyChainTradeTransaction"
                        + "/ram:ApplicableHeaderTradeSettlement"
                        + "/ram:ApplicableTradeTax)", document);

        assertEquals("0", count);
    }
}
