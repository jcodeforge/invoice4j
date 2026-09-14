package ubl;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.DocumentTypeCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.UnitCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoiceLine;
import io.github.jcodeforge.invoice4jbase.testfactory.*;
import io.github.jcodeforge.invoice4jbase.ubl.UblInvoiceWriter;
import io.github.jcodeforge.invoice4jbase.ubl.UblProfile;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import java.io.File;
import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class UblInvoiceWriterTest {

    private UblInvoiceWriter SUT;

    @Before
    public void setUp() {
        SUT = UblInvoiceWriter.builder()
                .profile(UblProfile.EN16931)
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
    public void shouldWriteCompleteInvoiceStructure() throws Exception {
        Invoice invoice = TestInvoiceFactory.createCompleteInvoice();

        Document document = parse(SUT.writeToString(invoice));

        XPath xpath = TestXPathFactory.createXPath();

        assertFalse(
                xpath.evaluate(
                        "/ubl:Invoice/cac:AccountingSupplierParty",
                        document
                ).isBlank()
        );

        assertFalse(
                xpath.evaluate(
                        "/ubl:Invoice/cac:AccountingCustomerParty",
                        document
                ).isBlank()
        );

        assertFalse(
                xpath.evaluate(
                        "/ubl:Invoice/cac:Delivery",
                        document
                ).isBlank()
        );

        assertFalse(
                xpath.evaluate(
                        "/ubl:Invoice/cac:PaymentMeans",
                        document
                ).isBlank()
        );

        assertFalse(
                xpath.evaluate(
                        "/ubl:Invoice/cac:PaymentTerms",
                        document
                ).isBlank()
        );

        assertFalse(
                xpath.evaluate(
                        "/ubl:Invoice/cac:TaxTotal",
                        document
                ).isBlank()
        );

        assertFalse(
                xpath.evaluate(
                        "/ubl:Invoice/cac:LegalMonetaryTotal",
                        document
                ).isBlank()
        );

        assertEquals(
                "2",
                xpath.evaluate(
                        "count(/ubl:Invoice/cac:InvoiceLine)",
                        document
                )
        );
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
    public void shouldWriteInvoiceNumber() throws Exception {
        String xml = SUT.writeToString(TestInvoiceFactory.createMinimalInvoice());

        Document document = parse(xml);
        XPath xpath = TestXPathFactory.createXPath();

        String invoiceNumber = xpath.evaluate("/ubl:Invoice/cbc:ID", document);

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

        assertEquals("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2",
                document.getDocumentElement().getNamespaceURI());

        assertEquals("urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2",
                document.getDocumentElement().getAttribute("xmlns:cac"));

        assertEquals("urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2",
                document.getDocumentElement().getAttribute("xmlns:cbc"));
    }

    @Test
    public void shouldContainInvoiceRoot() throws Exception {
        Document document = parse(SUT.writeToString(TestInvoiceFactory.createMinimalInvoice()));

        assertEquals("Invoice", document.getDocumentElement().getLocalName());
    }

    @Test
    public void shouldContainSellerAndBuyer() throws Exception {
        Document document = parse(SUT.writeToString(TestInvoiceFactory.createMinimalInvoice()));

        XPath xpath = TestXPathFactory.createXPath();

        assertFalse(xpath.evaluate(
                "/ubl:Invoice/cac:AccountingSupplierParty",
                document
        ).isBlank());

        assertFalse(xpath.evaluate(
                "/ubl:Invoice/cac:AccountingCustomerParty",
                document
        ).isBlank());
    }

    @Test
    public void shouldContainInvoiceLine() throws Exception {
        Document document = parse(SUT.writeToString(TestInvoiceFactory.createMinimalInvoice()));

        XPath xpath = TestXPathFactory.createXPath();

        assertFalse(xpath.evaluate(
                "/ubl:Invoice/cac:InvoiceLine",
                document
        ).isBlank());
    }

    @Test
    public void shouldContainTaxTotal() throws Exception {
        Document document = parse(SUT.writeToString(TestInvoiceFactory.createMinimalInvoice()));

        XPath xpath = TestXPathFactory.createXPath();

        assertFalse(xpath.evaluate(
                "/ubl:Invoice/cac:TaxTotal",
                document
        ).isBlank());
    }

    @Test
    public void shouldContainMonetaryTotal() throws Exception {
        Document document = parse(SUT.writeToString(TestInvoiceFactory.createMinimalInvoice()));

        XPath xpath = TestXPathFactory.createXPath();

        assertFalse(xpath.evaluate(
                "/ubl:Invoice/cac:LegalMonetaryTotal",
                document
        ).isBlank());
    }

    @Test
    public void shouldWriteIssueDate() throws Exception {
        String xml = SUT.writeToString(TestInvoiceFactory.createMinimalInvoice());

        Document document = parse(xml);
        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate("/ubl:Invoice/cbc:IssueDate", document);

        assertEquals("2026-01-01", value);
    }

    @Test
    public void shouldWriteCurrency() throws Exception {
        String xml = SUT.writeToString(TestInvoiceFactory.createMinimalInvoice());

        Document document = parse(xml);
        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate("/ubl:Invoice/cbc:DocumentCurrencyCode", document);

        assertEquals("EUR", value);
    }

    @Test
    public void shouldWriteSellerName() throws Exception {
        String xml = SUT.writeToString(TestInvoiceFactory.createMinimalInvoice());

        Document document = parse(xml);
        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate(
                "/ubl:Invoice"
                        + "/cac:AccountingSupplierParty"
                        + "/cac:Party"
                        + "/cac:PartyName"
                        + "/cbc:Name",
                document
        );

        assertEquals("Müller & Söhne GmbH", value);
    }

    @Test
    public void shouldWriteBuyerName() throws Exception {
        String xml = SUT.writeToString(TestInvoiceFactory.createMinimalInvoice());

        Document document = parse(xml);
        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate(
                "/ubl:Invoice"
                        + "/cac:AccountingCustomerParty"
                        + "/cac:Party"
                        + "/cac:PartyName"
                        + "/cbc:Name",
                document
        );

        assertEquals("Müller & Söhne GmbH", value);
    }

    @Test
    public void shouldWriteInvoiceLineId() throws Exception {
        String xml = SUT.writeToString(TestInvoiceFactory.createMinimalInvoice());

        Document document = parse(xml);
        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate(
                "/ubl:Invoice"
                        + "/cac:InvoiceLine"
                        + "/cbc:ID",
                document
        );

        assertEquals("1", value);
    }

    @Test
    public void shouldWriteInvoiceLineName() throws Exception {
        String xml = SUT.writeToString(TestInvoiceFactory.createMinimalInvoice());

        Document document = parse(xml);
        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate(
                "/ubl:Invoice"
                        + "/cac:InvoiceLine"
                        + "/cac:Item"
                        + "/cbc:Name",
                document
        );

        assertEquals("Item", value);
    }

    @Test
    public void shouldWriteTaxRate() throws Exception {
        String xml = SUT.writeToString(TestInvoiceFactory.createMinimalInvoice());

        Document document = parse(xml);
        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate(
                "/ubl:Invoice"
                        + "/cac:TaxTotal"
                        + "/cac:TaxSubtotal"
                        + "/cac:TaxCategory"
                        + "/cbc:Percent",
                document
        );

        assertEquals("19", value);
    }

    @Test
    public void shouldWriteGrandTotal() throws Exception {
        String xml = SUT.writeToString(TestInvoiceFactory.createMinimalInvoice());

        Document document = parse(xml);
        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate(
                "/ubl:Invoice"
                        + "/cac:LegalMonetaryTotal"
                        + "/cbc:PayableAmount",
                document
        );

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
    public void shouldNotWriteBuyerReferenceWhenNull() throws Exception {
        Invoice invoice = TestInvoiceFactory.createMinimalInvoice();

        String xml = SUT.writeToString(invoice);

        Document document = parse(xml);
        XPath xpath = TestXPathFactory.createXPath();

        String count = xpath.evaluate(
                "count(/ubl:Invoice/cbc:BuyerReference)",
                document
        );

        assertEquals("0", count);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullInvoiceForWriteToString() {
        SUT.writeToString(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullFile() throws Exception {
        SUT.writeToFile(TestInvoiceFactory.createMinimalInvoice(), null);
    }

    @Test
    public void shouldWriteMultipleInvoiceLines() throws Exception {
        Invoice invoice = TestInvoiceFactory.createCompleteInvoice();

        Document document = parse(SUT.writeToString(invoice));

        XPath xpath = TestXPathFactory.createXPath();

        String count = xpath.evaluate("count(/ubl:Invoice/cac:InvoiceLine)", document);

        assertEquals("2", count);
    }

    @Test
    public void shouldWriteAllowance() throws Exception {
        Invoice invoice = TestInvoiceFactory.createCompleteInvoice();

        Document document = parse(SUT.writeToString(invoice));

        XPath xpath = TestXPathFactory.createXPath();

        String count = xpath.evaluate(
                "count(/ubl:Invoice/cac:AllowanceCharge)",
                document
        );

        assertEquals("1", count);
    }

    @Test
    public void shouldWriteAllowanceAndCharge() throws Exception {
        Invoice invoice = TestInvoiceFactory.createInvoiceWithAllowanceAndCharge();

        Document document = parse(SUT.writeToString(invoice));

        XPath xpath = TestXPathFactory.createXPath();

        String count = xpath.evaluate("count(/ubl:Invoice/cac:AllowanceCharge)", document);

        assertEquals("2", count);
    }

    @Test
    public void shouldNotWriteNotesWhenCollectionIsEmpty() throws Exception {
        Invoice invoice = TestInvoiceFactory.createMinimalInvoice();

        Document document = parse(SUT.writeToString(invoice));

        XPath xpath = TestXPathFactory.createXPath();

        String count = xpath.evaluate("count(/ubl:Invoice/cbc:Note)", document);

        assertEquals("0", count);
    }

    @Test
    public void shouldNotWriteAllowanceChargesWhenCollectionIsEmpty() throws Exception {
        Invoice invoice = TestInvoiceFactory.createMinimalInvoice();

        Document document = parse(SUT.writeToString(invoice));

        XPath xpath = TestXPathFactory.createXPath();

        String count = xpath.evaluate(
                "count(/ubl:Invoice/cac:AllowanceCharge)",
                document
        );

        assertEquals("0", count);
    }

    @Test
    public void shouldNotWriteBillingReferencesWhenCollectionIsEmpty() throws Exception {
        Invoice invoice = TestInvoiceFactory.createMinimalInvoice();

        Document document = parse(SUT.writeToString(invoice));

        XPath xpath = TestXPathFactory.createXPath();

        String count = xpath.evaluate(
                "count(/ubl:Invoice/cac:BillingReference)",
                document
        );

        assertEquals("0", count);
    }

    @Test
    public void shouldNotWriteAdditionalDocumentsWhenCollectionIsEmpty() throws Exception {
        Invoice invoice = TestInvoiceFactory.createMinimalInvoice();

        Document document = parse(SUT.writeToString(invoice));

        XPath xpath = TestXPathFactory.createXPath();

        String count = xpath.evaluate(
                "count(/ubl:Invoice/cac:AdditionalDocumentReference)",
                document
        );

        assertEquals("0", count);
    }

    @Test
    public void shouldNotWriteTaxTotalWhenCollectionIsEmpty() throws Exception {
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
                .lines(List.of(
                        TestInvoiceLineFactory.createFirstInvoiceLine()))
                .taxes(List.of())
                .monetarySummation(
                        TestMonetarySummationFactory
                                .createMonetarySummation())
                .build();

        Document document =
                parse(SUT.writeToString(invoice));

        XPath xpath = TestXPathFactory.createXPath();

        String count = xpath.evaluate(
                "count(/ubl:Invoice/cac:TaxTotal)",
                document
        );

        assertEquals("0", count);
    }

    @Test
    public void shouldWriteInvoiceLinePriceDiscountAndGrossPrice() throws Exception {
        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .priceDiscount(new BigDecimal("15.00"))
                .grossPrice(CalculationUtils.createEUMoney("115.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

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
                .lines(List.of(line))
                .taxes(List.of(TestTaxFactory.createTax()))
                .monetarySummation(
                        TestMonetarySummationFactory.createMonetarySummation()
                )
                .build();

        Document document = parse(SUT.writeToString(invoice));

        XPath xpath = TestXPathFactory.createXPath();

        String priceDiscount = xpath.evaluate(
                "/ubl:Invoice"
                        + "/cac:InvoiceLine"
                        + "/cac:Price"
                        + "/cac:AllowanceCharge"
                        + "/cbc:Amount",
                document
        );

        String grossPrice = xpath.evaluate(
                "/ubl:Invoice"
                        + "/cac:InvoiceLine"
                        + "/cac:Price"
                        + "/cac:AllowanceCharge"
                        + "/cbc:BaseAmount",
                document
        );

        String chargeIndicator = xpath.evaluate(
                "/ubl:Invoice"
                        + "/cac:InvoiceLine"
                        + "/cac:Price"
                        + "/cac:AllowanceCharge"
                        + "/cbc:ChargeIndicator",
                document
        );

        assertEquals("15.00", priceDiscount);
        assertEquals("115.00", grossPrice);
        assertEquals("false", chargeIndicator);
    }

    @Test
    public void shouldWriteInvoiceLineBaseQuantity() throws Exception {
        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .baseQuantity(new BigDecimal("10"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

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
                .lines(List.of(line))
                .taxes(List.of(TestTaxFactory.createTax()))
                .monetarySummation(
                        TestMonetarySummationFactory.createMonetarySummation()
                )
                .build();

        Document document = parse(SUT.writeToString(invoice));

        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate(
                "/ubl:Invoice"
                        + "/cac:InvoiceLine"
                        + "/cac:Price"
                        + "/cbc:BaseQuantity",
                document
        );

        String unitCode = xpath.evaluate(
                "/ubl:Invoice"
                        + "/cac:InvoiceLine"
                        + "/cac:Price"
                        + "/cbc:BaseQuantity/@unitCode",
                document
        );

        assertEquals("10", value);
        assertEquals(UnitCode.ONE.getCode(), unitCode);
    }

    @Test
    public void shouldWriteInvoiceLineNote() throws Exception {
        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .note("Special handling required")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

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
                .lines(List.of(line))
                .taxes(List.of(TestTaxFactory.createTax()))
                .monetarySummation(
                        TestMonetarySummationFactory.createMonetarySummation()
                )
                .build();

        Document document = parse(SUT.writeToString(invoice));

        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate(
                "/ubl:Invoice"
                        + "/cac:InvoiceLine"
                        + "/cbc:Note",
                document
        );

        assertEquals("Special handling required", value);
    }

    @Test
    public void shouldWriteInvoiceLineAccountingCost() throws Exception {
        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .buyerAccountingReference("COST-CENTER-100")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

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
                .lines(List.of(line))
                .taxes(List.of(TestTaxFactory.createTax()))
                .monetarySummation(
                        TestMonetarySummationFactory.createMonetarySummation()
                )
                .build();

        Document document = parse(SUT.writeToString(invoice));

        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate(
                "/ubl:Invoice"
                        + "/cac:InvoiceLine"
                        + "/cbc:AccountingCost",
                document
        );

        assertEquals("COST-CENTER-100", value);
    }

    @Test
    public void shouldWriteInvoiceLineQuantityAndUnitCode() throws Exception {
        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(new BigDecimal("2.50"))
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

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
                .lines(List.of(line))
                .taxes(List.of(TestTaxFactory.createTax()))
                .monetarySummation(
                        TestMonetarySummationFactory.createMonetarySummation()
                )
                .build();

        Document document = parse(SUT.writeToString(invoice));

        XPath xpath = TestXPathFactory.createXPath();

        String quantity = xpath.evaluate(
                "/ubl:Invoice"
                        + "/cac:InvoiceLine"
                        + "/cbc:InvoicedQuantity",
                document
        );

        String unitCode = xpath.evaluate(
                "/ubl:Invoice"
                        + "/cac:InvoiceLine"
                        + "/cbc:InvoicedQuantity/@unitCode",
                document
        );

        assertEquals("2.50", quantity);
        assertEquals(UnitCode.ONE.getCode(), unitCode);
    }

    @Test
    public void shouldWriteInvoiceLineItemDescription() throws Exception {
        InvoiceLine line = InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .description("15 inch business notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

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
                .lines(List.of(line))
                .taxes(List.of(TestTaxFactory.createTax()))
                .monetarySummation(
                        TestMonetarySummationFactory.createMonetarySummation()
                )
                .build();

        Document document = parse(SUT.writeToString(invoice));

        XPath xpath = TestXPathFactory.createXPath();

        String value = xpath.evaluate(
                "/ubl:Invoice"
                        + "/cac:InvoiceLine"
                        + "/cac:Item"
                        + "/cbc:Description",
                document
        );

        assertEquals("15 inch business notebook", value);
    }

    @Test
    public void shouldWriteCompleteInvoiceLine() throws Exception {
        InvoiceLine line = InvoiceLine.builder()
                .id("42")
                .note("Special handling required")
                .buyerAccountingReference("COST-CENTER-100")
                .itemName("Notebook")
                .description("15 inch business notebook")
                .sellerAssignedIdentifier("SELLER-123")
                .buyerAssignedIdentifier("BUYER-456")
                .itemClassificationIdentifier("12345678")
                .quantity(new BigDecimal("2.50"))
                .unitCode(UnitCode.ONE)
                .netPrice(CalculationUtils.createEUMoney("100.00"))
                .priceDiscount(new BigDecimal("15.00"))
                .grossPrice(CalculationUtils.createEUMoney("115.00"))
                .baseQuantity(new BigDecimal("10"))
                .lineExtensionAmount(
                        CalculationUtils.createEUMoney("250.00")
                )
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();

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
                .lines(List.of(line))
                .taxes(List.of(TestTaxFactory.createTax()))
                .monetarySummation(
                        TestMonetarySummationFactory.createMonetarySummation()
                )
                .build();

        Document document = parse(SUT.writeToString(invoice));

        XPath xpath = TestXPathFactory.createXPath();

        // BT-126 — Invoice line ID
        assertEquals("42", xpath.evaluate("/ubl:Invoice/cac:InvoiceLine/cbc:ID", document));
        // BT-127 — Invoice line note
        assertEquals("Special handling required", xpath.evaluate("/ubl:Invoice/cac:InvoiceLine/cbc:Note",
                document));
        // BT-133 — Accounting reference
        assertEquals("COST-CENTER-100", xpath.evaluate(
                "/ubl:Invoice/cac:InvoiceLine/cbc:AccountingCost", document));
        // BT-129 / BT-130 — Quantity / unit
        assertEquals("2.50", xpath.evaluate("/ubl:Invoice/cac:InvoiceLine/cbc:InvoicedQuantity", document));

        assertEquals(
                UnitCode.ONE.getCode(),
                xpath.evaluate(
                        "/ubl:Invoice/cac:InvoiceLine/cbc:InvoicedQuantity/@unitCode",
                        document
                )
        );

        // BT-131 — Line extension amount
        assertEquals(
                "250.00",
                xpath.evaluate(
                        "/ubl:Invoice/cac:InvoiceLine/cbc:LineExtensionAmount",
                        document
                )
        );

        // BT-154 — Item description
        assertEquals(
                "15 inch business notebook",
                xpath.evaluate(
                        "/ubl:Invoice/cac:InvoiceLine/cac:Item/cbc:Description",
                        document
                )
        );

        // BT-153 — Item name
        assertEquals(
                "Notebook",
                xpath.evaluate(
                        "/ubl:Invoice/cac:InvoiceLine/cac:Item/cbc:Name",
                        document
                )
        );

        // BT-155 — Seller item identifier
        assertEquals(
                "SELLER-123",
                xpath.evaluate(
                        "/ubl:Invoice/cac:InvoiceLine"
                                + "/cac:Item"
                                + "/cac:SellersItemIdentification"
                                + "/cbc:ID",
                        document
                )
        );

        // BT-156 — Buyer item identifier
        assertEquals(
                "BUYER-456",
                xpath.evaluate(
                        "/ubl:Invoice/cac:InvoiceLine"
                                + "/cac:Item"
                                + "/cac:BuyersItemIdentification"
                                + "/cbc:ID",
                        document
                )
        );

        // BT-158 — Item classification
        assertEquals(
                "12345678",
                xpath.evaluate(
                        "/ubl:Invoice/cac:InvoiceLine"
                                + "/cac:Item"
                                + "/cac:CommodityClassification"
                                + "/cbc:ItemClassificationCode",
                        document
                )
        );

        // BT-151 / BT-152 — VAT category / rate
        assertEquals(
                TaxCategoryCode.STANDARD.getCode(),
                xpath.evaluate(
                        "/ubl:Invoice/cac:InvoiceLine"
                                + "/cac:Item"
                                + "/cac:ClassifiedTaxCategory"
                                + "/cbc:ID",
                        document
                )
        );

        assertEquals(
                "19",
                xpath.evaluate(
                        "/ubl:Invoice/cac:InvoiceLine"
                                + "/cac:Item"
                                + "/cac:ClassifiedTaxCategory"
                                + "/cbc:Percent",
                        document
                )
        );

        // BT-146 — Net price
        assertEquals(
                "100.00",
                xpath.evaluate(
                        "/ubl:Invoice/cac:InvoiceLine"
                                + "/cac:Price"
                                + "/cbc:PriceAmount",
                        document
                )
        );

        // BT-147 — Price discount
        assertEquals(
                "15.00",
                xpath.evaluate(
                        "/ubl:Invoice/cac:InvoiceLine"
                                + "/cac:Price"
                                + "/cac:AllowanceCharge"
                                + "/cbc:Amount",
                        document
                )
        );

        // BT-148 — Gross price
        assertEquals(
                "115.00",
                xpath.evaluate(
                        "/ubl:Invoice/cac:InvoiceLine"
                                + "/cac:Price"
                                + "/cac:AllowanceCharge"
                                + "/cbc:BaseAmount",
                        document
                )
        );

        assertEquals(
                "false",
                xpath.evaluate(
                        "/ubl:Invoice/cac:InvoiceLine"
                                + "/cac:Price"
                                + "/cac:AllowanceCharge"
                                + "/cbc:ChargeIndicator",
                        document
                )
        );

        // BT-149 / BT-150 — Base quantity / unit
        assertEquals(
                "10",
                xpath.evaluate(
                        "/ubl:Invoice/cac:InvoiceLine"
                                + "/cac:Price"
                                + "/cbc:BaseQuantity",
                        document
                )
        );

        assertEquals(
                UnitCode.ONE.getCode(),
                xpath.evaluate(
                        "/ubl:Invoice/cac:InvoiceLine"
                                + "/cac:Price"
                                + "/cbc:BaseQuantity/@unitCode",
                        document
                )
        );
    }

    private Document parse(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true);

        return factory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
    }
}