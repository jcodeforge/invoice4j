package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.*;
import io.github.jcodeforge.invoice4jbase.ubl.UblConfigurationOptions;
import io.github.jcodeforge.invoice4jbase.ubl.UblProfile;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class InvoiceSerializer implements XmlSerializer<Invoice> {

    private final SellerSerializer sellerSerializer = new SellerSerializer();
    private final BuyerSerializer buyerSerializer = new BuyerSerializer();
    private final PayeeSerializer payeeSerializer = new PayeeSerializer();
    private final DeliverySerializer deliverySerializer = new DeliverySerializer();
    private final PaymentMeansSerializer paymentMeansSerializer = new PaymentMeansSerializer();
    private final PaymentTermsSerializer paymentTermsSerializer = new PaymentTermsSerializer();
    private final TaxTotalSerializer taxTotalSerializer = new TaxTotalSerializer();
    private final MonetarySummationSerializer monetarySummationSerializer = new MonetarySummationSerializer();
    private final InvoiceLineSerializer invoiceLineSerializer = new InvoiceLineSerializer();
    private final AllowanceChargeSerializer allowanceChargeSerializer = new AllowanceChargeSerializer();
    private final DocumentReferenceSerializer documentReferenceSerializer = new DocumentReferenceSerializer();
    private final NoteSerializer noteSerializer = new NoteSerializer();
    private final InvoicePeriodSerializer invoicePeriodSerializer = new InvoicePeriodSerializer();

    private final UblConfigurationOptions options;

    public InvoiceSerializer(UblConfigurationOptions options) {
        this.options = options;
    }

    @Override
    public void serialize(XmlWriter writer, Invoice invoice) {
        writer.startDocument();
        writer.startElement(
                "",
                XmlNamespaces.UBL_INVOICE,
                "Invoice"
        );

        writer.writeDefaultNamespace(
                XmlNamespaces.UBL_INVOICE
        );

        writer.writeNamespace(
                "cac",
                XmlNamespaces.UBL_CAC
        );

        writer.writeNamespace(
                "cbc",
                XmlNamespaces.UBL_CBC
        );

        writeHeader(writer, invoice);

        writeParties(writer, invoice);

        writeDelivery(writer, invoice);

        writePayment(writer, invoice);

        writePaymentTerms(writer, invoice);

        writeAllowanceCharges(writer, invoice);

        writeTaxes(writer, invoice);

        writeMonetarySummation(writer, invoice);

        writeInvoiceLines(writer, invoice);

        writer.endElement();

        writer.endDocument();
    }

    private void writeHeader(XmlWriter writer, Invoice invoice) {
        UblProfile profile = options.getProfile();

        // BT-24
        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "CustomizationID",
                profile.getCustomizationId()
        );

        // BT-23
        if (profile.hasProfileId()) {
            writer.writeOptionalElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "ProfileID",
                    profile.getProfileId()
            );
        }

        // BT-1
        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "ID",
                invoice.getInvoiceNumber()
        );

        // BT-2
        if (invoice.getIssueDate() != null) {
            writer.writeElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "IssueDate",
                    invoice.getIssueDate().toString()
            );
        }

        // BT-9
        if (invoice.getDueDate() != null) {
            writer.writeElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "DueDate",
                    invoice.getDueDate().toString()
            );
        }

        // BT-3
        if (invoice.getDocumentTypeCode() != null) {
            writer.writeElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "InvoiceTypeCode",
                    invoice.getDocumentTypeCode().getCode()
            );
        }

        // BT-22
        for (Note note : invoice.getNotes()) {
            noteSerializer.serialize(writer, note);
        }

        // BT-7
        if (invoice.getTaxPointDate() != null) {
            writer.writeElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "TaxPointDate",
                    invoice.getTaxPointDate().toString()
            );
        }

        // BT-5
        if (invoice.getCurrency() != null) {
            writer.writeElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "DocumentCurrencyCode",
                    invoice.getCurrency().getCode()
            );
        }

        // BT-6
        if (invoice.getTaxCurrency() != null) {
            writer.writeElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "TaxCurrencyCode",
                    invoice.getTaxCurrency().getCode()
            );
        }

        // BT-10
        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "BuyerReference",
                invoice.getBuyerReference()
        );

        writeInvoicePeriod(writer, invoice);

        writeOrderReference(writer, invoice);

        writeBillingReferences(writer, invoice);

        writeContractReference(writer, invoice);

        writeAdditionalDocuments(writer, invoice);

        writeProjectReference(writer, invoice);
    }

    private void writeInvoicePeriod(XmlWriter writer, Invoice invoice) {
        if (invoice.getInvoicePeriod() == null) {
            return;
        }

        invoicePeriodSerializer.serialize(writer, invoice.getInvoicePeriod());
    }

    private void writeOrderReference(XmlWriter writer, Invoice invoice) {
        if (invoice.getPurchaseOrderReference() == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "OrderReference"
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "ID",
                invoice.getPurchaseOrderReference()
        );

        writer.endElement();
    }

    private void writeBillingReferences(XmlWriter writer, Invoice invoice) {
        for (DocumentReference reference : invoice.getBillingReferences()) {
            documentReferenceSerializer.serializeBillingReference(writer, reference);
        }
    }

    private void writeContractReference(
            XmlWriter writer,
            Invoice invoice) {

        if (invoice.getContractReference() == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "ContractDocumentReference"
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "ID",
                invoice.getContractReference()
        );

        writer.endElement();
    }

    private void writeAdditionalDocuments(XmlWriter writer, Invoice invoice) {
        for (DocumentReference reference : invoice.getAdditionalDocuments()) {
            documentReferenceSerializer.serialize(writer, reference);
        }
    }

    private void writeProjectReference(
            XmlWriter writer,
            Invoice invoice) {

        if (invoice.getProjectReference() == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "ProjectReference"
        );

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "ID",
                invoice.getProjectReference()
        );

        writer.endElement();
    }

    private void writeParties(XmlWriter writer, Invoice invoice) {
        sellerSerializer.serialize(writer, invoice.getSeller());
        buyerSerializer.serialize(writer, invoice.getBuyer());

        if (invoice.getPayee() != null) {
            payeeSerializer.serialize(writer, invoice.getPayee());
        }
    }

    private void writeDelivery(XmlWriter writer, Invoice invoice) {
        if (invoice.getDelivery() != null) {
            deliverySerializer.serialize(writer, invoice.getDelivery());
        }
    }

    private void writePayment(XmlWriter writer, Invoice invoice) {
        if (invoice.getPayment() != null) {
            paymentMeansSerializer.serialize(writer, invoice.getPayment());
        }
    }

    private void writePaymentTerms(XmlWriter writer, Invoice invoice) {
        if (invoice.getPaymentTerms() != null) {
            paymentTermsSerializer.serialize(writer, invoice.getPaymentTerms());
        }
    }

    private void writeAllowanceCharges(XmlWriter writer, Invoice invoice) {
        for (AllowanceCharge allowanceCharge : invoice.getAllowanceCharges()) {
            allowanceChargeSerializer.serialize(writer, allowanceCharge);
        }
    }

    private void writeTaxes(XmlWriter writer, Invoice invoice) {
        taxTotalSerializer.serialize(writer, invoice.getTaxes());
    }

    private void writeMonetarySummation(XmlWriter writer, Invoice invoice) {
        monetarySummationSerializer.serialize(writer, invoice.getMonetarySummation());
    }

    private void writeInvoiceLines(XmlWriter writer, Invoice invoice) {
        for (InvoiceLine line : invoice.getLines()) {
            invoiceLineSerializer.serialize(writer, line);
        }
    }
}