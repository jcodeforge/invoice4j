package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.DocumentTypeCode;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private final String projectName;

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
    private final PaymentMeans paymentMeans;

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
        this.projectName = builder.projectName;
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
        this.paymentMeans = builder.paymentMeans;
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

    public String getProjectName() {
        return projectName;
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

    public PaymentMeans getPayment() {
        return paymentMeans;
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

    public static Builder builder() {
        return new Builder();
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
        private String projectName;
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
        private PaymentMeans paymentMeans;
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

        public Builder projectName(String projectName) {
            this.projectName = projectName;
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
            if (note == null) {
                throw new InvoiceValidationException("Invoice note must not be null.");
            }
            this.notes.add(note);
            return this;
        }

        public Builder notes(List<Note> notes) {
            if (notes == null) {
                throw new InvoiceValidationException("Invoice note must not be null.");
            }

            this.notes.clear();
            for (Note note: notes) {
                addNote(note);
            }

            return this;
        }

        public Builder addBillingReference(DocumentReference reference) {
            if (reference == null) {
                throw new InvoiceValidationException("Billing references must not be null.");
            }
            for (DocumentReference existing : billingReferences) {
                if (existing.getId().equals(reference.getId())) {
                    throw new InvoiceValidationException("Duplicate billing reference identifier: " + existing.getId());
                }
            }

            this.billingReferences.add(reference);
            return this;
        }

        public Builder billingReferences(List<DocumentReference> billingReferences) {
            if (billingReferences == null) {
                throw new InvoiceValidationException("Billing references must not be null.");
            }

            this.billingReferences.clear();
            for (DocumentReference reference: billingReferences) {
                addBillingReference(reference);
            }

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

        public Builder paymentMeans(PaymentMeans paymentMeans) {
            this.paymentMeans = paymentMeans;
            return this;
        }

        public Builder paymentTerms(PaymentTerms paymentTerms) {
            this.paymentTerms = paymentTerms;
            return this;
        }

        public Builder addAllowanceCharge(AllowanceCharge allowanceCharge) {
            if (allowanceCharge == null) {
                throw new InvoiceValidationException("Document level allowance or charge must not be null.");
            }

            this.allowanceCharges.add(allowanceCharge);
            return this;
        }

        public Builder allowanceCharges(List<AllowanceCharge> allowanceCharges) {
            if (allowanceCharges == null) {
                throw new InvoiceValidationException("Allowance charges must not be null.");
            }

            this.allowanceCharges.clear();
            for (AllowanceCharge allowanceCharge : allowanceCharges) {
                addAllowanceCharge(allowanceCharge);
            }

            return this;
        }

        public Builder monetarySummation(MonetarySummation monetarySummation) {
            this.monetarySummation = monetarySummation;
            return this;
        }

        public Builder addTax(Tax tax) {
            if (tax == null) {
                throw new InvoiceValidationException("BG-23 VAT breakdown information must not be null.");
            }
            this.taxes.add(tax);
            return this;
        }

        public Builder taxes(List<Tax> taxes) {
            if (taxes == null) {
                throw new InvoiceValidationException("Taxes must not be null.");
            }

            this.taxes.clear();
            for (Tax tax : taxes) {
                addTax(tax);
            }

            return this;
        }

        public Builder addAdditionalDocument(DocumentReference document) {
            if (document == null) {
                throw new InvoiceValidationException("Billing references (BG-3) - Additional supporting documents (BG-24) must not be null.");
            }
            for (DocumentReference existing : additionalDocuments) {
                if (existing.getId().equals(document.getId())) {
                    throw new InvoiceValidationException("Duplicate document reference identifier: " + existing.getId());
                }
            }

            this.additionalDocuments.add(document);
            return this;
        }

        public Builder additionalDocuments(List<DocumentReference> documents) {
            if (documents == null) {
                throw new InvoiceValidationException("Referenced documents must not be null.");
            }

            this.additionalDocuments.clear();
            for (DocumentReference reference : documents) {
                addAdditionalDocument(reference);
            }

            return this;
        }

        public Builder addLine(InvoiceLine line) {
            if (line == null) {
                throw new InvoiceValidationException("Invoice line must not be null.");
            }
            if (line.getQuantity() == null || line.getQuantity().signum() <= 0) {
                throw new InvoiceValidationException("BT-153 Quantity must be greater than zero.");
            }
            if (line.getNetPrice() == null) {
                throw new InvoiceValidationException("BT-146 Net price is required.");
            }

            for (InvoiceLine existing : lines) {
                if (existing.getId().equals(line.getId())) {
                    throw new InvoiceValidationException("Duplicate invoice line identifier: " + existing.getId());
                }
            }

            this.lines.add(line);
            return this;
        }

        public Builder lines(List<InvoiceLine> lines) {
            if (lines == null) {
                throw new InvoiceValidationException("Invoice lines must not be null.");
            }

            this.lines.clear();
            for (InvoiceLine line : lines) {
                addLine(line);
            }

            return this;
        }

        public Builder from(Invoice invoice) {
            if (invoice == null) {
                throw new InvoiceValidationException("Invoice must not be null.");
            }

            invoiceNumber(invoice.getInvoiceNumber());
            issueDate(invoice.getIssueDate());
            documentTypeCode(invoice.getDocumentTypeCode());
            currency(invoice.getCurrency());
            taxCurrency(invoice.getTaxCurrency());
            taxPointDate(invoice.getTaxPointDate());
            taxPointDateCode(invoice.getTaxPointDateCode());
            dueDate(invoice.getDueDate());
            buyerReference(invoice.getBuyerReference());
            projectReference(invoice.getProjectReference());
            projectName(invoice.getProjectName());
            contractReference(invoice.getContractReference());
            purchaseOrderReference(invoice.getPurchaseOrderReference());
            salesOrderReference(invoice.getSalesOrderReference());
            invoicePeriod(invoice.getInvoicePeriod());
            tenderReference(invoice.getTenderReference());
            objectIdentifier(invoice.getObjectIdentifier());
            buyerAccountingReference(invoice.getBuyerAccountingReference());
            profileIdentifier(invoice.getProfileIdentifier());
            customizationIdentifier(invoice.getCustomizationIdentifier());
            businessProcessIdentifier(invoice.getBusinessProcessIdentifier());
            notes(invoice.getNotes());
            billingReferences(invoice.getBillingReferences());
            seller(invoice.getSeller());
            buyer(invoice.getBuyer());
            payee(invoice.getPayee());
            shipTo(invoice.getShipTo());
            delivery(invoice.getDelivery());
            paymentMeans(invoice.getPayment());
            paymentTerms(invoice.getPaymentTerms());
            allowanceCharges(invoice.getAllowanceCharges());
            monetarySummation(invoice.getMonetarySummation());
            taxes(invoice.getTaxes());
            additionalDocuments(invoice.getAdditionalDocuments());
            lines(invoice.getLines());

            return this;
        }

        public Invoice build() {
            if (invoiceNumber == null || invoiceNumber.isBlank()) {
                throw new InvoiceValidationException("BT-1 Invoice identifier must not be null or blank.");
            }
            if (issueDate == null) {
                throw new InvoiceValidationException("BT-2 Issue date must not be null.");
            }
            if (documentTypeCode == null) {
                throw new InvoiceValidationException("BT-3 Invoice type code must not be null.");
            }
            if (taxPointDate != null && taxPointDateCode != null) {
                throw new InvoiceValidationException("BT-7 and BT-8 cannot both be specified.");
            }
            if (delivery == null) {
                throw new InvoiceValidationException("BT-72 BT-75 Delivery should not be null.");
            }
            if (delivery.getActualDeliveryDate() == null) {
                throw new InvoiceValidationException("BT-72 Actual delivery date must not be null.");
            }
            if (dueDate != null && dueDate.isBefore(issueDate)) {
                throw new InvoiceValidationException("BT-9 Payment due date must not be before BT-2 issue date.");
            }
            if (currency == null) {
                throw new InvoiceValidationException("BT-5 Currency is required.");
            }
            if (seller == null) {
                throw new InvoiceValidationException("BG-4 Seller is required.");
            }
            if (buyer == null) {
                throw new InvoiceValidationException("BG-7 Buyer is required.");
            }
            if (lines.isEmpty()) {
                throw new InvoiceValidationException("BG-25 At least one invoice line is required.");
            }
            if (monetarySummation == null) {
                throw new InvoiceValidationException("BG-22 Monetary summation is required.");
            }
            if (paymentMeans == null) {
                throw new InvoiceValidationException("BG-16 payment Means is required.");
            }
            if (paymentTerms == null) {
                throw new InvoiceValidationException("BG-19 payment Terms is required.");
            }
            if (paymentTerms != null && paymentTerms.getDueDate() != null && paymentTerms.getDueDate().isBefore(issueDate)) {
                throw new InvoiceValidationException("BT-9 Payment due date must not be before BT-2 Issue date.");
            }

            if (invoicePeriod != null) {
                if (invoicePeriod.getStartDate() == null || invoicePeriod.getEndDate() == null) {
                    throw new InvoiceValidationException("Invoice period start date and invoice period end date must not be null.");
                }

                if (invoicePeriod.getStartDate().isAfter(invoicePeriod.getEndDate())) {
                    throw new InvoiceValidationException("BT-73 Invoice period start date must not be after BT-74 invoice period end date.");
                }
            }

            if (projectReference != null && projectName == null) {
                throw new InvoiceValidationException("Project name must not be null when project reference is specified.");
            }

            for (Tax tax : taxes) {
                if (tax.getTaxableAmount() == null || tax.getTaxAmount() == null || tax.getRate() == null) {
                    throw new InvoiceValidationException("BG-23 VAT breakdown is incomplete.");
                }

                BigDecimal expectedTax = tax.getTaxableAmount().getAmount().multiply(tax.getRate())
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

                BigDecimal difference = expectedTax.subtract(tax.getTaxAmount().getAmount()).abs();

                if (difference.compareTo(new BigDecimal("0.01")) > 0) {
                    throw new InvoiceValidationException("BT-117 VAT amount mismatch.");
                }
            }

            return new Invoice(this);
        }
    }
}
