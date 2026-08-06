package io.github.jcodeforge.invoice4jzugferd.cii.parser;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.DocumentTypeCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.*;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlReader;
import java.util.ArrayList;
import java.util.List;

public final class InvoiceParser implements XmlParser<Invoice> {

    private final SellerParser sellerParser = new SellerParser();

    private final BuyerParser buyerParser = new BuyerParser();

    private final PayeeParser payeeParser = new PayeeParser();

    private final DeliveryParser deliveryParser = new DeliveryParser();

    private final PaymentMeansParser paymentMeansParser = new PaymentMeansParser();

    private final PaymentTermsParser paymentTermsParser = new PaymentTermsParser();

    private final InvoicePeriodParser invoicePeriodParser = new InvoicePeriodParser();

    private final TaxParser taxParser = new TaxParser();

    private final MonetarySummationParser monetarySummationParser = new MonetarySummationParser();

    private final InvoiceLineParser invoiceLineParser = new InvoiceLineParser();

    private final AllowanceChargeParser allowanceChargeParser = new AllowanceChargeParser();

    private final NoteParser noteParser = new NoteParser();

    private final DocumentReferenceParser documentReferenceParser = new DocumentReferenceParser();

    @Override
    public Invoice parse(XmlReader reader, String basePath) {

        return Invoice.builder()
                // Context
                .businessProcessIdentifier(reader.readString(
                        basePath +
                                "/rsm:ExchangedDocumentContext"
                                + "/ram:BusinessProcessSpecifiedDocumentContextParameter"
                                + "/ram:ID"))
                .customizationIdentifier(reader.readString(
                        basePath
                                + "/rsm:ExchangedDocumentContext"
                                + "/ram:GuidelineSpecifiedDocumentContextParameter"
                                + "/ram:ID"))
                // Header
                .invoiceNumber(reader.readString(
                        basePath
                                + "/rsm:ExchangedDocument"
                                + "/ram:ID"))
                .documentTypeCode(readDocumentTypeCode(reader, basePath))
                .issueDate(reader.readDate(
                        basePath
                                + "/rsm:ExchangedDocument"
                                + "/ram:IssueDateTime"
                                + "/udt:DateTimeString"))
                .notes(readNotes(reader, basePath))
                // References
                .billingReferences(readBillingReferences(reader, basePath))
                // Parties
                .seller(sellerParser.parse(reader,
                        basePath
                                + "/rsm:SupplyChainTradeTransaction"
                                + "/ram:ApplicableHeaderTradeAgreement"
                                + "/ram:SellerTradeParty"))
                .buyer(buyerParser.parse(reader,
                        basePath
                                + "/rsm:SupplyChainTradeTransaction"
                                + "/ram:ApplicableHeaderTradeAgreement"
                                + "/ram:BuyerTradeParty"))
                .payee(payeeParser.parse(reader,
                        basePath
                                + "/rsm:SupplyChainTradeTransaction"
                                + "/ram:ApplicableHeaderTradeAgreement"
                                + "/ram:PayeeTradeParty"))
                // Delivery
                .delivery(deliveryParser.parse(reader,
                        basePath
                                + "/rsm:SupplyChainTradeTransaction"
                                + "/ram:ApplicableHeaderTradeDelivery"))
                // Settlement
                .currency(readCurrency(reader, basePath))
                .paymentMeans(paymentMeansParser.parse(reader,
                        basePath
                                + "/rsm:SupplyChainTradeTransaction"
                                + "/ram:ApplicableHeaderTradeSettlement"
                                + "/ram:SpecifiedTradeSettlementPaymentMeans"))
                .paymentTerms(paymentTermsParser.parse(reader,
                        basePath
                                + "/rsm:SupplyChainTradeTransaction"
                                + "/ram:ApplicableHeaderTradeSettlement"
                                + "/ram:SpecifiedTradePaymentTerms"))
                .invoicePeriod(invoicePeriodParser.parse(reader,
                        basePath
                                + "/rsm:SupplyChainTradeTransaction"
                                + "/ram:ApplicableHeaderTradeSettlement"
                                + "/ram:BillingSpecifiedPeriod"))
                .taxes(readTaxes(reader, basePath))
                .allowanceCharges(readAllowanceCharges(reader, basePath))
                .monetarySummation(monetarySummationParser.parse(reader,
                        basePath
                                + "/rsm:SupplyChainTradeTransaction"
                                + "/ram:ApplicableHeaderTradeSettlement"
                                + "/ram:SpecifiedTradeSettlementHeaderMonetarySummation"))
                // Documents
                .additionalDocuments(readAdditionalDocuments(reader, basePath))
                // Lines
                .lines(readInvoiceLines(reader, basePath))
                .build();
    }

    private DocumentTypeCode readDocumentTypeCode(XmlReader reader, String basePath) {
        String code = reader.readString(
                basePath
                        + "/rsm:ExchangedDocument"
                        + "/ram:TypeCode");

        return code == null ? null : DocumentTypeCode.fromCode(code);
    }

    private CurrencyCode readCurrency(XmlReader reader, String basePath) {
        String code = reader.readString(
                basePath
                        + "/rsm:SupplyChainTradeTransaction"
                        + "/ram:ApplicableHeaderTradeSettlement"
                        + "/ram:InvoiceCurrencyCode");

        return code == null ? null : CurrencyCode.fromCode(code);
    }

    private List<Tax> readTaxes(XmlReader reader, String basePath) {
        List<Tax> taxes = new ArrayList<>();
        for (String path : reader.readPaths(
                basePath
                        + "/rsm:SupplyChainTradeTransaction"
                        + "/ram:ApplicableHeaderTradeSettlement"
                        + "/ram:ApplicableTradeTax")) {

            taxes.add(taxParser.parse(reader, path));
        }

        return taxes;
    }

    private List<InvoiceLine> readInvoiceLines(XmlReader reader, String basePath) {
        List<InvoiceLine> lines = new ArrayList<>();
        for (String path : reader.readPaths(
                basePath
                        + "/rsm:SupplyChainTradeTransaction"
                        + "/ram:IncludedSupplyChainTradeLineItem")) {

            lines.add(invoiceLineParser.parse(reader, path));
        }

        return lines;
    }

    private List<AllowanceCharge> readAllowanceCharges(XmlReader reader, String basePath) {
        List<AllowanceCharge> allowanceCharges = new ArrayList<>();

        for (String path : reader.readPaths(basePath
                + "/rsm:SupplyChainTradeTransaction"
                + "/ram:ApplicableHeaderTradeSettlement"
                + "/ram:SpecifiedTradeAllowanceCharge")) {
            allowanceCharges.add(allowanceChargeParser.parse(reader, path));
        }

        return allowanceCharges;
    }

    private List<DocumentReference> readBillingReferences(XmlReader reader, String basePath) {
        List<DocumentReference> references = new ArrayList<>();

        for (String path : reader.readPaths(
                basePath
                        + "/rsm:SupplyChainTradeTransaction"
                        + "/ram:ApplicableHeaderTradeAgreement"
                        + "/ram:BuyerOrderReferencedDocument")) {

            references.add(documentReferenceParser.parse(reader, path));
        }

        return references;
    }

    private List<Note> readNotes(XmlReader reader, String basePath) {
        List<Note> notes = new ArrayList<>();

        for (String path : reader.readPaths(
                basePath
                        + "/rsm:ExchangedDocument"
                        + "/ram:IncludedNote")) {

            notes.add(noteParser.parse(reader, path));
        }

        return notes;
    }

    private List<DocumentReference> readAdditionalDocuments(XmlReader reader, String basePath) {
        List<DocumentReference> documents = new ArrayList<>();

        for (String path : reader.readPaths(
                basePath
                        + "/rsm:SupplyChainTradeTransaction"
                        + "/ram:ApplicableHeaderTradeSettlement"
                        + "/ram:AdditionalReferencedDocument")) {

            documents.add(documentReferenceParser.parse(reader, path));
        }

        return documents;
    }
}