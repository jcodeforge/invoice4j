package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.*;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiConfigurationOptions;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiProfile;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;
import java.time.format.DateTimeFormatter;

public final class InvoiceSerializer implements XmlSerializer<Invoice> {

    private final SellerSerializer sellerSerializer;
    private final BuyerSerializer buyerSerializer;
    private final PayeeSerializer payeeSerializer;
    private final DeliverySerializer deliverySerializer;
    private final PaymentMeansSerializer paymentMeansSerializer;
    private final PaymentTermsSerializer paymentTermsSerializer;
    private final TaxSerializer taxSerializer;
    private final MonetarySummationSerializer monetarySummationSerializer;
    private final InvoiceLineSerializer invoiceLineSerializer;
    private final AllowanceChargeSerializer allowanceChargeSerializer;
    private final DocumentReferenceSerializer documentReferenceSerializer;
    private final NoteSerializer noteSerializer;
    private final InvoicePeriodSerializer invoicePeriodSerializer;

    private final CiiConfigurationOptions options;

    public InvoiceSerializer(CiiConfigurationOptions options) {
        this.options = options;

        this.sellerSerializer = new SellerSerializer(options);
        this.buyerSerializer = new BuyerSerializer(options);
        this.payeeSerializer = new PayeeSerializer(options);
        this.deliverySerializer = new DeliverySerializer(options);
        this.paymentMeansSerializer = new PaymentMeansSerializer(options);
        this.paymentTermsSerializer = new PaymentTermsSerializer();
        this.taxSerializer = new TaxSerializer();
        this.monetarySummationSerializer = new MonetarySummationSerializer(options);
        this.invoiceLineSerializer = new InvoiceLineSerializer();
        this.allowanceChargeSerializer = new AllowanceChargeSerializer();
        this.documentReferenceSerializer = new DocumentReferenceSerializer();
        this.noteSerializer = new NoteSerializer();
        this.invoicePeriodSerializer = new InvoicePeriodSerializer();
    }

    @Override
    public void serialize(XmlWriter writer, Invoice invoice) {
        writer.startDocument();

        writer.startElement(
                XmlNamespaces.RSM,
                "CrossIndustryInvoice");

        writer.writeNamespace("rsm", XmlNamespaces.RSM);
        writer.writeNamespace("ram", XmlNamespaces.RAM);
        writer.writeNamespace("udt", XmlNamespaces.UDT);
        writer.writeNamespace("qdt", XmlNamespaces.QDT);

        writeExchangedDocumentContext(writer);
        writeExchangedDocument(writer, invoice);
        writeSupplyChainTradeTransaction(writer, invoice);

        writer.endElement();
        writer.endDocument();
    }

    private void writeExchangedDocumentContext(XmlWriter writer) {
        writer.startElement(
                XmlNamespaces.RSM,
                "ExchangedDocumentContext");

        CiiProfile profile = options.getProfile();

        if (profile.hasBusinessProcessId()) {
            writer.startElement(
                    XmlNamespaces.RAM,
                    "BusinessProcessSpecifiedDocumentContextParameter");

            writer.writeElement(
                    XmlNamespaces.RAM,
                    "ID",
                    profile.getBusinessProcessId());

            writer.endElement();
        }

        writer.startElement(
                XmlNamespaces.RAM,
                "GuidelineSpecifiedDocumentContextParameter");

        writer.writeElement(
                XmlNamespaces.RAM,
                "ID",
                profile.getGuidelineId());

        writer.endElement();
        writer.endElement();
    }

    private void writeExchangedDocument(
            XmlWriter writer,
            Invoice invoice) {

        writer.startElement(
                XmlNamespaces.RSM,
                "ExchangedDocument");

        // BT-1
        writer.writeElement(
                XmlNamespaces.RAM,
                "ID",
                invoice.getInvoiceNumber());

        // BT-3
        writer.writeElement(
                XmlNamespaces.RAM,
                "TypeCode",
                invoice.getDocumentTypeCode().getCode());

        // BT-2
        writer.startElement(
                XmlNamespaces.RAM,
                "IssueDateTime");

        writer.startElement(
                XmlNamespaces.UDT,
                "DateTimeString");

        writer.writeAttribute("format", "102");

        writer.writeCharacters(
                invoice.getIssueDate()
                        .format(DateTimeFormatter.BASIC_ISO_DATE));

        writer.endElement();
        writer.endElement();

        // BG-1
        for (Note note : invoice.getNotes()) {
            noteSerializer.serialize(writer, note);
        }

        writer.endElement();
    }

    private void writeSupplyChainTradeTransaction(
            XmlWriter writer,
            Invoice invoice) {

        writer.startElement(
                XmlNamespaces.RSM,
                "SupplyChainTradeTransaction");

        // BG-25
        for (InvoiceLine line : invoice.getLines()) {
            invoiceLineSerializer.serialize(writer, line);
        }

        writeApplicableHeaderTradeAgreement(writer, invoice);
        writeApplicableHeaderTradeDelivery(writer, invoice);
        writeApplicableHeaderTradeSettlement(writer, invoice);

        writer.endElement();
    }

    private void writeApplicableHeaderTradeAgreement(XmlWriter writer, Invoice invoice) {
        writer.startElement(
                XmlNamespaces.RAM,
                "ApplicableHeaderTradeAgreement"
        );

        // BT-10
        writer.writeOptionalElement(
                XmlNamespaces.RAM,
                "BuyerReference",
                invoice.getBuyerReference()
        );

        // BG-4
        sellerSerializer.serialize(
                writer,
                invoice.getSeller()
        );

        // BG-7
        buyerSerializer.serialize(
                writer,
                invoice.getBuyer()
        );

        // BG-10
        if (options.getProfile() != CiiProfile.EN16931
                && options.getProfile() != CiiProfile.ZUGFERD_BASIC) {

            payeeSerializer.serialize(
                    writer,
                    invoice.getPayee()
            );
        }

        // BT-13
        if (invoice.getSalesOrderReference() != null
                && options.getProfile() != CiiProfile.ZUGFERD_BASIC) {

            writer.startElement(
                    XmlNamespaces.RAM,
                    "SellerOrderReferencedDocument"
            );

            writer.writeElement(
                    XmlNamespaces.RAM,
                    "IssuerAssignedID",
                    invoice.getSalesOrderReference()
            );

            writer.endElement();
        }

        // BT-14
        if (invoice.getPurchaseOrderReference() != null) {
            writer.startElement(
                    XmlNamespaces.RAM,
                    "BuyerOrderReferencedDocument"
            );

            writer.writeElement(
                    XmlNamespaces.RAM,
                    "IssuerAssignedID",
                    invoice.getPurchaseOrderReference()
            );

            writer.endElement();
        }

        // BT-12
        if (invoice.getContractReference() != null) {
            writer.startElement(
                    XmlNamespaces.RAM,
                    "ContractReferencedDocument"
            );

            writer.writeElement(
                    XmlNamespaces.RAM,
                    "IssuerAssignedID",
                    invoice.getContractReference()
            );

            writer.endElement();
        }

        // BG-24 / AdditionalReferencedDocument
        if (options.getProfile() != CiiProfile.ZUGFERD_BASIC) {
            for (DocumentReference reference : invoice.getAdditionalDocuments()) {
                documentReferenceSerializer.serialize(
                        writer,
                        reference
                );
            }
        }

        // BT-11
        if (invoice.getProjectReference() != null && options.getProfile() != CiiProfile.ZUGFERD_BASIC) {
            writer.startElement(
                    XmlNamespaces.RAM,
                    "SpecifiedProcuringProject"
            );

            writer.writeElement(
                    XmlNamespaces.RAM,
                    "ID",
                    invoice.getProjectReference()
            );

            writer.writeElement(
                    XmlNamespaces.RAM,
                    "Name",
                    invoice.getProjectName()
            );

            writer.endElement();
        }

        writer.endElement();
    }

    private void writeApplicableHeaderTradeDelivery(
            XmlWriter writer,
            Invoice invoice) {

        writer.startElement(
                XmlNamespaces.RAM,
                "ApplicableHeaderTradeDelivery");

        if (invoice.getDelivery() != null) {
            deliverySerializer.serialize(
                    writer,
                    invoice.getDelivery());
        }

        writer.endElement();
    }

    private void writeApplicableHeaderTradeSettlement(
            XmlWriter writer,
            Invoice invoice) {

        writer.startElement(
                XmlNamespaces.RAM,
                "ApplicableHeaderTradeSettlement"
        );

        // BT-6
        if (invoice.getTaxCurrency() != null && invoice.getTaxCurrency() != invoice.getCurrency()) {
            writer.writeElement(
                    XmlNamespaces.RAM,
                    "TaxCurrencyCode",
                    invoice.getTaxCurrency().getCode()
            );
        }

        // BT-5
        writer.writeElement(
                XmlNamespaces.RAM,
                "InvoiceCurrencyCode",
                invoice.getCurrency().getCode()
        );

        // BG-16
        paymentMeansSerializer.serialize(
                writer,
                invoice.getPayment()
        );

        // BG-23
        for (Tax tax : invoice.getTaxes()) {
            taxSerializer.serialize(
                    writer,
                    tax
            );
        }

        // BG-18
        invoicePeriodSerializer.serialize(
                writer,
                invoice.getInvoicePeriod()
        );

        // BG-20 / BG-21
        for (AllowanceCharge allowanceCharge : invoice.getAllowanceCharges()) {
            allowanceChargeSerializer.serialize(
                    writer,
                    allowanceCharge
            );
        }

        // BG-19
        paymentTermsSerializer.serialize(
                writer,
                invoice.getPaymentTerms()
        );

        // BG-22
        monetarySummationSerializer.serialize(
                writer,
                invoice.getMonetarySummation()
        );

        // Billing references
        for (DocumentReference reference : invoice.getBillingReferences()) {
            documentReferenceSerializer.serialize(
                    writer,
                    "InvoiceReferencedDocument", reference
            );
        }

        writer.endElement();
    }
}