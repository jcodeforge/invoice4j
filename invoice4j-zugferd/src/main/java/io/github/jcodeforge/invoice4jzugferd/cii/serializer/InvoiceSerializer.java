package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.*;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;
import java.time.format.DateTimeFormatter;

public class InvoiceSerializer implements XmlSerializer<Invoice> {

    private final SellerSerializer sellerSerializer = new SellerSerializer();

    private final BuyerSerializer buyerSerializer = new BuyerSerializer();

    private final PayeeSerializer payeeSerializer = new PayeeSerializer();

    private final DeliverySerializer deliverySerializer = new DeliverySerializer();

    private final PaymentMeansSerializer paymentMeansSerializer = new PaymentMeansSerializer();

    private final PaymentTermsSerializer paymentTermsSerializer = new PaymentTermsSerializer();

    private final TaxSerializer taxSerializer = new TaxSerializer();

    private final MonetarySummationSerializer monetarySummationSerializer = new MonetarySummationSerializer();

    private final InvoiceLineSerializer invoiceLineSerializer = new InvoiceLineSerializer();

    private final AllowanceChargeSerializer allowanceChargeSerializer = new AllowanceChargeSerializer();

    private final DocumentReferenceSerializer documentReferenceSerializer = new DocumentReferenceSerializer();

    private final NoteSerializer noteSerializer = new NoteSerializer();

    private final InvoicePeriodSerializer invoicePeriodSerializer = new InvoicePeriodSerializer();

    @Override
    public void serialize(XmlWriter writer, Invoice invoice) {
        writer.startDocument();
        writer.startElement(XmlNamespaces.RSM, "CrossIndustryInvoice");
        writer.writeNamespace("rsm", XmlNamespaces.RSM);
        writer.writeNamespace("ram", XmlNamespaces.RAM);
        writer.writeNamespace("udt", XmlNamespaces.UDT);
        writer.writeNamespace("qdt", XmlNamespaces.QDT);
        writeExchangedDocumentContext(writer, invoice);
        writeExchangedDocument(writer, invoice);
        writeSupplyChainTradeTransaction(writer, invoice);
        writer.endElement();
        writer.endDocument();
    }

    private void writeExchangedDocumentContext(XmlWriter writer, Invoice invoice) {
        writer.startElement(XmlNamespaces.RSM, "ExchangedDocumentContext");

        /*
         * BT-23
         * Business process identifier (optional)
         */
        if (invoice.getBusinessProcessIdentifier() != null) {
            writer.startElement(XmlNamespaces.RAM, "BusinessProcessSpecifiedDocumentContextParameter");
            writer.writeElement(XmlNamespaces.RAM, "ID", invoice.getBusinessProcessIdentifier());
            writer.endElement();
        }

        /*
         * BT-24
         * Specification identifier (mandatory for EN16931/ZUGFeRD profiles)
         */
        if (invoice.getCustomizationIdentifier() != null) {
            writer.startElement(XmlNamespaces.RAM, "GuidelineSpecifiedDocumentContextParameter");
            writer.writeElement(XmlNamespaces.RAM, "ID", invoice.getCustomizationIdentifier());
            writer.endElement();
        }

        writer.endElement();
    }

    private void writeExchangedDocument(XmlWriter writer, Invoice invoice) {
        writer.startElement(XmlNamespaces.RSM, "ExchangedDocument");

        /*
         * BT-1
         * Invoice number
         */
        writer.writeElement(XmlNamespaces.RAM, "ID", invoice.getInvoiceNumber());

        /*
         * BT-3
         * Invoice type code
         */
        writer.writeElement(XmlNamespaces.RAM, "TypeCode", invoice.getDocumentTypeCode().getCode());

        /*
         * BT-2
         * Issue date
         */
        writer.startElement(XmlNamespaces.RAM, "IssueDateTime");
        writer.startElement(XmlNamespaces.UDT, "DateTimeString");
        writer.writeAttribute("format", "102");
        writer.writeCharacters(invoice.getIssueDate().format(DateTimeFormatter.BASIC_ISO_DATE));
        writer.endElement();
        writer.endElement();

        /*
         * BG-1
         * Invoice notes
         */
        for (Note note : invoice.getNotes()) {
            noteSerializer.serialize(writer, note);
        }

        writer.endElement();
    }

    private void writeSupplyChainTradeTransaction(XmlWriter writer, Invoice invoice) {
        writer.startElement(XmlNamespaces.RSM, "SupplyChainTradeTransaction");

        /*
         * BG-25
         * Invoice lines
         */
        for (InvoiceLine line : invoice.getLines()) {
            invoiceLineSerializer.serialize(writer, line);
        }

        writeApplicableHeaderTradeAgreement(writer, invoice);
        writeApplicableHeaderTradeDelivery(writer, invoice);
        writeApplicableHeaderTradeSettlement(writer, invoice);

        writer.endElement();
    }

    private void writeApplicableHeaderTradeAgreement(XmlWriter writer, Invoice invoice) {
        writer.startElement(XmlNamespaces.RAM, "ApplicableHeaderTradeAgreement");
        // BT-10
        writer.writeElement(XmlNamespaces.RAM, "BuyerReference", invoice.getBuyerReference());
        // BG-4
        sellerSerializer.serialize(writer, invoice.getSeller());
        // BG-7
        buyerSerializer.serialize(writer, invoice.getBuyer());
        // BG-10
        payeeSerializer.serialize(writer, invoice.getPayee());

        // BT-13
        if (invoice.getSalesOrderReference() != null) {
            writer.startElement(XmlNamespaces.RAM, "SellerOrderReferencedDocument");
            writer.writeElement(XmlNamespaces.RAM, "IssuerAssignedID", invoice.getSalesOrderReference());
            writer.endElement();
        }

        // BT-14
        if (invoice.getPurchaseOrderReference() != null) {
            writer.startElement(XmlNamespaces.RAM, "BuyerOrderReferencedDocument");
            writer.writeElement(XmlNamespaces.RAM, "IssuerAssignedID", invoice.getPurchaseOrderReference());
            writer.endElement();
        }

        // BT-12
        if (invoice.getContractReference() != null) {
            writer.startElement(XmlNamespaces.RAM, "ContractReferencedDocument");
            writer.writeElement(XmlNamespaces.RAM, "IssuerAssignedID", invoice.getContractReference());
            writer.endElement();
        }

        // BT-11
        if (invoice.getProjectReference() != null) {
            writer.startElement(XmlNamespaces.RAM, "SpecifiedProcuringProject");
            writer.writeElement(XmlNamespaces.RAM, "ID", invoice.getProjectReference());
            writer.endElement();
        }

        // BT-17
        if (invoice.getTenderReference() != null) {
            writer.startElement(XmlNamespaces.RAM, "TenderReferencedDocument");
            writer.writeElement(XmlNamespaces.RAM, "IssuerAssignedID", invoice.getTenderReference());
            writer.endElement();
        }

        writer.endElement();
    }

    private void writeApplicableHeaderTradeDelivery(XmlWriter writer, Invoice invoice) {
        writer.startElement(XmlNamespaces.RAM, "ApplicableHeaderTradeDelivery");

        if (invoice.getDelivery() != null) {
            deliverySerializer.serialize(writer, invoice.getDelivery());
        }

        writer.endElement();
    }

    private void writeApplicableHeaderTradeSettlement(XmlWriter writer, Invoice invoice) {
        writer.startElement(XmlNamespaces.RAM, "ApplicableHeaderTradeSettlement");

        // BT-5
        writer.writeElement(XmlNamespaces.RAM, "InvoiceCurrencyCode", invoice.getCurrency().getCode());

        // BT-6
        if (invoice.getTaxCurrency() != null) {
            writer.writeElement(XmlNamespaces.RAM, "TaxCurrencyCode", invoice.getTaxCurrency().getCode());
        }

        // BT-83
        writer.writeElement(XmlNamespaces.RAM, "PaymentReference",
                invoice.getPayment().getRemittanceInformation());

        // BT-19
        if (invoice.getBuyerAccountingReference() != null) {
            writer.startElement(XmlNamespaces.RAM, "ReceivableSpecifiedTradeAccountingAccount");
            writer.writeElement(XmlNamespaces.RAM, "ID", invoice.getBuyerAccountingReference());
            writer.endElement();
        }

        // BG-16
        paymentMeansSerializer.serialize(writer, invoice.getPayment());

        // BG-19
        if (invoice.getPaymentTerms() != null) {
            paymentTermsSerializer.serialize(writer, invoice.getPaymentTerms());
        }

        // BG-23
        for (Tax tax : invoice.getTaxes()) {
            taxSerializer.serialize(writer, tax);
        }

        // BG-20 / BG-21
        for (AllowanceCharge allowanceCharge : invoice.getAllowanceCharges()) {
            allowanceChargeSerializer.serialize(writer, allowanceCharge);
        }

        // BG-14
        if (invoice.getInvoicePeriod() != null) {
            invoicePeriodSerializer.serialize(writer, invoice.getInvoicePeriod());
        }

        // BG-24
        for (DocumentReference reference : invoice.getAdditionalDocuments()) {
            documentReferenceSerializer.serialize(writer, reference);
        }

        // BG-22
        for (DocumentReference reference : invoice.getBillingReferences()) {
            documentReferenceSerializer.serialize(writer, "InvoiceReferencedDocument", reference);
        }

        // BG-22
        monetarySummationSerializer.serialize(writer, invoice.getMonetarySummation());

        writer.endElement();
    }
}