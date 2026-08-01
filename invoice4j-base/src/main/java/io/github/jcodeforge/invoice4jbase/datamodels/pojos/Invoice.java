package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.DocumentTypeCode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class Invoice {

    /**
     * BT-1
     */
    private final String invoiceNumber;

    /**
     * BT-2
     */
    private final LocalDate issueDate;

    /**
     * BT-3
     */
    private final DocumentTypeCode documentTypeCode;

    /**
     * BT-5
     */
    private final CurrencyCode currency;

    /**
     * BT-6
     */
    private final CurrencyCode taxCurrency;

    /**
     * BT-7
     */
    private final LocalDate taxPointDate;

    /**
     * BT-8
     */
    private final String taxPointDateCode;

    /**
     * BT-9
     */
    private final LocalDate dueDate;

    /**
     * BT-10
     */
    private final String buyerReference;

    /**
     * BT-11
     */
    private final String projectReference;

    /**
     * BT-12
     */
    private final String contractReference;

    /**
     * BT-13
     */
    private final String purchaseOrderReference;

    /**
     * BT-14
     */
    private final String salesOrderReference;

    /**
     * BT-15 / BT-16
     */
    private final InvoicePeriod invoicePeriod;

    /**
     * BT-17
     */
    private final String tenderReference;

    /**
     * BT-18
     */
    private final String objectIdentifier;

    /**
     * BT-19
     */
    private final String buyerAccountingReference;

    /**
     * BT-23
     */
    private final String profileIdentifier;

    /**
     * BT-24
     */
    private final String customizationIdentifier;

    /**
     * PEPPOL Business Process Identifier
     */
    private final String businessProcessIdentifier;

    /**
     * BG-1
     */
    private final List<Note> notes;

    /**
     * BG-3
     */
    private final List<DocumentReference> billingReferences;

    /**
     * BG-4
     */
    private final Seller seller;

    /**
     * BG-7
     */
    private final Buyer buyer;

    /**
     * BG-10
     */
    private final Payee payee;

    /**
     * BG-13
     */
    private final ShipTo shipTo;

    /**
     * BG-14
     */
    private final Delivery delivery;

    /**
     * BG-16
     */
    private final Payment payment;

    /**
     * BG-19
     */
    private final PaymentTerms paymentTerms;

    /**
     * BG-20 / BG-21
     */
    private final List<AllowanceCharge> allowanceCharges;

    /**
     * BG-22
     */
    private final MonetarySummation monetarySummation;

    /**
     * BG-23
     */
    private final List<Tax> taxes;

    /**
     * BG-24
     */
    private final List<DocumentReference> additionalDocuments;

    /**
     * BG-25
     */
    private final List<InvoiceLine> lines;

    private Invoice(Builder builder) {
        this.invoiceNumber = builder.invoiceNumber;
        this.issueDate = builder.issueDate;
        this.documentTypeCode = builder.documentTypeCode;
        this.currency = builder.currency;
        this.taxCurrency = builder.taxCurrency;
        this.taxPointDate = builder.taxPointDate;
        this.taxPointDateCode = builder.taxPointDateCode;
        this.dueDate = builder.dueDate;
        this.buyerReference = builder.buyerReference;
        this.projectReference = builder.projectReference;
        this.contractReference = builder.contractReference;
        this.purchaseOrderReference = builder.purchaseOrderReference;
        this.salesOrderReference = builder.salesOrderReference;
        this.invoicePeriod = builder.invoicePeriod;
        this.tenderReference = builder.tenderReference;
        this.objectIdentifier = builder.objectIdentifier;
        this.buyerAccountingReference = builder.buyerAccountingReference;
        this.profileIdentifier = builder.profileIdentifier;
        this.customizationIdentifier = builder.customizationIdentifier;
        this.businessProcessIdentifier = builder.businessProcessIdentifier;
        this.notes = List.copyOf(builder.notes);
        this.billingReferences = List.copyOf(builder.billingReferences);
        this.seller = builder.seller;
        this.buyer = builder.buyer;
        this.payee = builder.payee;
        this.shipTo = builder.shipTo;
        this.delivery = builder.delivery;
        this.payment = builder.payment;
        this.paymentTerms = builder.paymentTerms;
        this.allowanceCharges = List.copyOf(builder.allowanceCharges);
        this.monetarySummation = builder.monetarySummation;
        this.taxes = List.copyOf(builder.taxes);
        this.additionalDocuments = List.copyOf(builder.additionalDocuments);
        this.lines = List.copyOf(builder.lines);
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public DocumentTypeCode getDocumentTypeCode() {
        return documentTypeCode;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }

    public CurrencyCode getTaxCurrency() {
        return taxCurrency;
    }

    public LocalDate getTaxPointDate() {
        return taxPointDate;
    }

    public String getTaxPointDateCode() {
        return taxPointDateCode;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getBuyerReference() {
        return buyerReference;
    }

    public String getProjectReference() {
        return projectReference;
    }

    public String getContractReference() {
        return contractReference;
    }

    public String getPurchaseOrderReference() {
        return purchaseOrderReference;
    }

    public String getSalesOrderReference() {
        return salesOrderReference;
    }

    public InvoicePeriod getInvoicePeriod() {
        return invoicePeriod;
    }

    public String getTenderReference() {
        return tenderReference;
    }

    public String getObjectIdentifier() {
        return objectIdentifier;
    }

    public String getBuyerAccountingReference() {
        return buyerAccountingReference;
    }

    public String getProfileIdentifier() {
        return profileIdentifier;
    }

    public String getCustomizationIdentifier() {
        return customizationIdentifier;
    }

    public String getBusinessProcessIdentifier() {
        return businessProcessIdentifier;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public List<DocumentReference> getBillingReferences() {
        return billingReferences;
    }

    public Seller getSeller() {
        return seller;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public Payee getPayee() {
        return payee;
    }

    public ShipTo getShipTo() {
        return shipTo;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public Payment getPayment() {
        return payment;
    }

    public PaymentTerms getPaymentTerms() {
        return paymentTerms;
    }

    public List<AllowanceCharge> getAllowanceCharges() {
        return allowanceCharges;
    }

    public MonetarySummation getMonetarySummation() {
        return monetarySummation;
    }

    public List<Tax> getTaxes() {
        return taxes;
    }

    public List<DocumentReference> getAdditionalDocuments() {
        return additionalDocuments;
    }

    public List<InvoiceLine> getLines() {
        return lines;
    }

    public static final class Builder {

        private String invoiceNumber;
        private LocalDate issueDate;
        private DocumentTypeCode documentTypeCode;
        private CurrencyCode currency;
        private CurrencyCode taxCurrency;
        private LocalDate taxPointDate;
        private String taxPointDateCode;
        private LocalDate dueDate;
        private String buyerReference;
        private String projectReference;
        private String contractReference;
        private String purchaseOrderReference;
        private String salesOrderReference;
        private InvoicePeriod invoicePeriod;
        private String tenderReference;
        private String objectIdentifier;
        private String buyerAccountingReference;
        private String profileIdentifier;
        private String customizationIdentifier;
        private String businessProcessIdentifier;
        private final List<Note> notes = new ArrayList<>();
        private final List<DocumentReference> billingReferences = new ArrayList<>();
        private Seller seller;
        private Buyer buyer;
        private Payee payee;
        private ShipTo shipTo;
        private Delivery delivery;
        private Payment payment;
        private PaymentTerms paymentTerms;
        private final List<AllowanceCharge> allowanceCharges = new ArrayList<>();
        private MonetarySummation monetarySummation;
        private final List<Tax> taxes = new ArrayList<>();
        private final List<DocumentReference> additionalDocuments = new ArrayList<>();
        private final List<InvoiceLine> lines = new ArrayList<>();

        private Builder() {
        }

        public Builder invoiceNumber(String invoiceNumber) {
            this.invoiceNumber = invoiceNumber;
            return this;
        }

        public Builder issueDate(LocalDate issueDate) {
            this.issueDate = issueDate;
            return this;
        }

        public Builder documentTypeCode(DocumentTypeCode documentTypeCode) {
            this.documentTypeCode = documentTypeCode;
            return this;
        }

        public Builder currency(CurrencyCode currency) {
            this.currency = currency;
            return this;
        }

        public Builder taxCurrency(CurrencyCode taxCurrency) {
            this.taxCurrency = taxCurrency;
            return this;
        }

        public Builder taxPointDate(LocalDate taxPointDate) {
            this.taxPointDate = taxPointDate;
            return this;
        }

        public Builder taxPointDateCode(String taxPointDateCode) {
            this.taxPointDateCode = taxPointDateCode;
            return this;
        }

        public Builder dueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder buyerReference(String buyerReference) {
            this.buyerReference = buyerReference;
            return this;
        }

        public Builder projectReference(String projectReference) {
            this.projectReference = projectReference;
            return this;
        }

        public Builder contractReference(String contractReference) {
            this.contractReference = contractReference;
            return this;
        }

        public Builder purchaseOrderReference(String purchaseOrderReference) {
            this.purchaseOrderReference = purchaseOrderReference;
            return this;
        }

        public Builder salesOrderReference(String salesOrderReference) {
            this.salesOrderReference = salesOrderReference;
            return this;
        }

        public Builder invoicePeriod(InvoicePeriod invoicePeriod) {
            this.invoicePeriod = invoicePeriod;
            return this;
        }

        public Builder tenderReference(String tenderReference) {
            this.tenderReference = tenderReference;
            return this;
        }

        public Builder objectIdentifier(String objectIdentifier) {
            this.objectIdentifier = objectIdentifier;
            return this;
        }

        public Builder buyerAccountingReference(String buyerAccountingReference) {
            this.buyerAccountingReference = buyerAccountingReference;
            return this;
        }

        public Builder profileIdentifier(String profileIdentifier) {
            this.profileIdentifier = profileIdentifier;
            return this;
        }

        public Builder customizationIdentifier(String customizationIdentifier) {
            this.customizationIdentifier = customizationIdentifier;
            return this;
        }

        public Builder businessProcessIdentifier(String businessProcessIdentifier) {
            this.businessProcessIdentifier = businessProcessIdentifier;
            return this;
        }

        public Builder addNote(Note note) {
            this.notes.add(note);
            return this;
        }

        public Builder notes(List<Note> notes) {
            this.notes.clear();
            this.notes.addAll(notes);
            return this;
        }

        public Builder addBillingReference(DocumentReference reference) {
            this.billingReferences.add(reference);
            return this;
        }

        public Builder billingReferences(List<DocumentReference> references) {
            this.billingReferences.clear();
            this.billingReferences.addAll(references);
            return this;
        }

        public Builder seller(Seller seller) {
            this.seller = seller;
            return this;
        }

        public Builder buyer(Buyer buyer) {
            this.buyer = buyer;
            return this;
        }

        public Builder payee(Payee payee) {
            this.payee = payee;
            return this;
        }

        public Builder shipTo(ShipTo shipTo) {
            this.shipTo = shipTo;
            return this;
        }

        public Builder delivery(Delivery delivery) {
            this.delivery = delivery;
            return this;
        }

        public Builder payment(Payment payment) {
            this.payment = payment;
            return this;
        }

        public Builder paymentTerms(PaymentTerms paymentTerms) {
            this.paymentTerms = paymentTerms;
            return this;
        }

        public Builder addAllowanceCharge(AllowanceCharge allowanceCharge) {
            this.allowanceCharges.add(allowanceCharge);
            return this;
        }

        public Builder monetarySummation(MonetarySummation monetarySummation) {
            this.monetarySummation = monetarySummation;
            return this;
        }

        public Builder addTax(Tax tax) {
            this.taxes.add(tax);
            return this;
        }

        public Builder addAdditionalDocument(DocumentReference document) {
            this.additionalDocuments.add(document);
            return this;
        }

        public Builder addLine(InvoiceLine line) {
            this.lines.add(line);
            return this;
        }

        public Invoice build() {
            return new Invoice(this);
        }
    }
}
