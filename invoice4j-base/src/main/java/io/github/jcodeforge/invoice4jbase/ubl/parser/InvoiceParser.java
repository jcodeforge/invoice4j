package io.github.jcodeforge.invoice4jbase.ubl.parser;

import io.github.jcodeforge.invoice4jbase.ubl.UblDateFormats;
import io.github.jcodeforge.invoice4jbase.xml.XmlParser;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.DocumentTypeCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.*;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;
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
                // Header
                .invoiceNumber(reader.readString(
                        basePath + "/cbc:ID"
                ))
                .documentTypeCode(readDocumentTypeCode(reader, basePath))
                .issueDate(reader.readDate(
                        basePath + "/cbc:IssueDate", UblDateFormats.DATE))
                .notes(readNotes(reader, basePath))
                .buyerReference(reader.readString(
                        basePath + "/cbc:BuyerReference"
                ))
                // References
                .purchaseOrderReference(reader.readString(
                        basePath
                                + "/cac:OrderReference"
                                + "/cbc:ID"
                ))
                .contractReference(reader.readString(
                        basePath
                                + "/cac:ContractDocumentReference"
                                + "/cbc:ID"
                ))
                .billingReferences(readBillingReferences(reader, basePath))
                .additionalDocuments(readAdditionalDocuments(reader, basePath))
                // Parties
                .seller(sellerParser.parse(
                        reader,
                        basePath
                                + "/cac:AccountingSupplierParty"
                                + "/cac:Party"
                ))
                .buyer(buyerParser.parse(
                        reader,
                        basePath
                                + "/cac:AccountingCustomerParty"
                                + "/cac:Party"
                ))
                .payee(payeeParser.parse(
                        reader,
                        basePath + "/cac:PayeeParty"
                ))
                // Delivery
                .delivery(deliveryParser.parse(
                        reader,
                        basePath + "/cac:Delivery"
                ))
                // Payment
                .paymentMeans(paymentMeansParser.parse(
                        reader,
                        basePath + "/cac:PaymentMeans"
                ))
                .paymentTerms(paymentTermsParser.parse(
                        reader,
                        basePath + "/cac:PaymentTerms"
                ))
                // Invoice period
                .invoicePeriod(invoicePeriodParser.parse(
                        reader,
                        basePath + "/cac:InvoicePeriod"
                ))
                // Currency
                .currency(readCurrency(reader, basePath))
                // Taxes
                .taxes(readTaxes(reader, basePath))
                // Header allowances / charges
                .allowanceCharges(
                        readAllowanceCharges(reader, basePath)
                )
                // Monetary summation
                .monetarySummation(
                        monetarySummationParser.parse(
                                reader,
                                basePath + "/cac:LegalMonetaryTotal",
                                basePath + "/cac:TaxTotal"))
                // Lines
                .lines(readInvoiceLines(reader, basePath))
                .build();
    }

    private DocumentTypeCode readDocumentTypeCode(XmlReader reader, String basePath) {
        String code = reader.readString(basePath + "/cbc:InvoiceTypeCode");

        return code == null ? null : DocumentTypeCode.fromCode(code);
    }

    private CurrencyCode readCurrency(XmlReader reader, String basePath) {
        String code = reader.readString(basePath + "/cbc:DocumentCurrencyCode");

        return code == null ? null : CurrencyCode.fromCode(code);
    }

    private List<Tax> readTaxes(XmlReader reader, String basePath) {
        List<Tax> taxes = new ArrayList<>();

        for (String path : reader.readPaths(basePath + "/cac:TaxTotal/cac:TaxSubtotal")) {
            taxes.add(taxParser.parse(reader, path));
        }

        return taxes;
    }

    private List<InvoiceLine> readInvoiceLines(XmlReader reader, String basePath) {
        List<InvoiceLine> lines = new ArrayList<>();

        for (String path : reader.readPaths(basePath + "/cac:InvoiceLine")) {
            lines.add(invoiceLineParser.parse(reader, path));
        }

        return lines;
    }

    private List<AllowanceCharge> readAllowanceCharges(XmlReader reader, String basePath) {
        List<AllowanceCharge> allowanceCharges = new ArrayList<>();

        for (String path : reader.readPaths(basePath + "/cac:AllowanceCharge")) {
            allowanceCharges.add(allowanceChargeParser.parse(reader, path));
        }

        return allowanceCharges;
    }

    private List<DocumentReference> readBillingReferences(XmlReader reader, String basePath) {
        List<DocumentReference> references = new ArrayList<>();

        for (String path : reader.readPaths(basePath + "/cac:BillingReference" + "/cac:InvoiceDocumentReference")) {
            references.add(documentReferenceParser.parse(reader, path));
        }

        return references;
    }

    private List<DocumentReference> readAdditionalDocuments(XmlReader reader, String basePath) {
        List<DocumentReference> documents = new ArrayList<>();

        for (String path : reader.readPaths(basePath + "/cac:AdditionalDocumentReference")) {
            documents.add(documentReferenceParser.parse(reader, path));
        }

        return documents;
    }

    private List<Note> readNotes(XmlReader reader, String basePath) {
        List<Note> notes = new ArrayList<>();

        for (String path : reader.readPaths(basePath + "/cbc:Note")) {
            Note note = noteParser.parse(reader, path);

            if (note != null) {
                notes.add(note);
            }
        }

        return notes;
    }
}