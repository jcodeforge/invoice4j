package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.DocumentTypeCode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Invoice {

    private Invoice() {
    }

    /**
     * BT-1
     */
    private String invoiceNumber;

    /**
     * BT-2
     */
    private LocalDate issueDate;

    /**
     * BT-3
     */
    private DocumentTypeCode documentTypeCode;

    /**
     * BT-5
     */
    private CurrencyCode currency;

    /**
     * BT-6
     */
    private CurrencyCode taxCurrency;

    /**
     * BT-7
     */
    private LocalDate taxPointDate;

    /**
     * BT-8
     */
    private String taxPointDateCode;

    /**
     * BT-9
     */
    private LocalDate dueDate;

    /**
     * BT-10
     */
    private String buyerReference;

    /**
     * BT-11
     */
    private String projectReference;

    /**
     * BT-12
     */
    private String contractReference;

    /**
     * BT-13
     */
    private String purchaseOrderReference;

    /**
     * BT-14
     */
    private String salesOrderReference;

    /**
     * BT-15 / BT-16
     */
    private InvoicePeriod invoicePeriod;

    /**
     * BT-17
     */
    private String tenderReference;

    /**
     * BT-18
     */
    private String objectIdentifier;

    /**
     * BT-19
     */
    private String buyerAccountingReference;

    /**
     * BT-23
     */
    private String profileIdentifier;

    /**
     * BT-24
     */
    private String customizationIdentifier;

    /**
     * PEPPOL Business Process Identifier
     */
    private String businessProcessIdentifier;

    /**
     * BG-1
     */
    private List<Note> notes = new ArrayList<>();

    /**
     * BG-3
     */
    private List<DocumentReference> billingReferences = new ArrayList<>();

    /**
     * BG-4
     */
    private Seller seller;

    /**
     * BG-7
     */
    private Buyer buyer;

    /**
     * BG-10
     */
    private Payee payee;

    /**
     * BG-13
     */
    private ShipTo shipTo;

    /**
     * BG-14
     */
    private Delivery delivery;

    /**
     * BG-16
     */
    private Payment payment;

    /**
     * BG-19
     */
    private PaymentTerms paymentTerms;

    /**
     * BG-20 / BG-21
     */
    private List<AllowanceCharge> allowanceCharges = new ArrayList<>();

    /**
     * BG-22
     */
    private MonetarySummation monetarySummation;

    /**
     * BG-23
     */
    private List<Tax> taxes = new ArrayList<>();

    /**
     * BG-24
     */
    private List<DocumentReference> additionalDocuments = new ArrayList<>();

    /**
     * BG-25
     */
    private List<InvoiceLine> lines = new ArrayList<>();

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public DocumentTypeCode getDocumentTypeCode() {
        return documentTypeCode;
    }

    public void setDocumentTypeCode(DocumentTypeCode documentTypeCode) {
        this.documentTypeCode = documentTypeCode;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyCode currency) {
        this.currency = currency;
    }

    public CurrencyCode getTaxCurrency() {
        return taxCurrency;
    }

    public void setTaxCurrency(CurrencyCode taxCurrency) {
        this.taxCurrency = taxCurrency;
    }

    public LocalDate getTaxPointDate() {
        return taxPointDate;
    }

    public void setTaxPointDate(LocalDate taxPointDate) {
        this.taxPointDate = taxPointDate;
    }

    public String getTaxPointDateCode() {
        return taxPointDateCode;
    }

    public void setTaxPointDateCode(String taxPointDateCode) {
        this.taxPointDateCode = taxPointDateCode;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getBuyerReference() {
        return buyerReference;
    }

    public void setBuyerReference(String buyerReference) {
        this.buyerReference = buyerReference;
    }

    public String getProjectReference() {
        return projectReference;
    }

    public void setProjectReference(String projectReference) {
        this.projectReference = projectReference;
    }

    public String getContractReference() {
        return contractReference;
    }

    public void setContractReference(String contractReference) {
        this.contractReference = contractReference;
    }

    public String getPurchaseOrderReference() {
        return purchaseOrderReference;
    }

    public void setPurchaseOrderReference(String purchaseOrderReference) {
        this.purchaseOrderReference = purchaseOrderReference;
    }

    public String getSalesOrderReference() {
        return salesOrderReference;
    }

    public void setSalesOrderReference(String salesOrderReference) {
        this.salesOrderReference = salesOrderReference;
    }

    public InvoicePeriod getInvoicePeriod() {
        return invoicePeriod;
    }

    public void setInvoicePeriod(InvoicePeriod invoicePeriod) {
        this.invoicePeriod = invoicePeriod;
    }

    public String getTenderReference() {
        return tenderReference;
    }

    public void setTenderReference(String tenderReference) {
        this.tenderReference = tenderReference;
    }

    public String getObjectIdentifier() {
        return objectIdentifier;
    }

    public void setObjectIdentifier(String objectIdentifier) {
        this.objectIdentifier = objectIdentifier;
    }

    public String getBuyerAccountingReference() {
        return buyerAccountingReference;
    }

    public void setBuyerAccountingReference(String buyerAccountingReference) {
        this.buyerAccountingReference = buyerAccountingReference;
    }

    public String getProfileIdentifier() {
        return profileIdentifier;
    }

    public void setProfileIdentifier(String profileIdentifier) {
        this.profileIdentifier = profileIdentifier;
    }

    public String getCustomizationIdentifier() {
        return customizationIdentifier;
    }

    public void setCustomizationIdentifier(String customizationIdentifier) {
        this.customizationIdentifier = customizationIdentifier;
    }

    public String getBusinessProcessIdentifier() {
        return businessProcessIdentifier;
    }

    public void setBusinessProcessIdentifier(String businessProcessIdentifier) {
        this.businessProcessIdentifier = businessProcessIdentifier;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void addNote(Note note) {
        this.notes.add(note);
    }


    public List<DocumentReference> getBillingReferences() {
        return billingReferences;
    }

    public void setBillingReferences(List<DocumentReference> billingReferences) {
        this.billingReferences = billingReferences;
    }

    public void addBillingReference(DocumentReference reference) {
        this.billingReferences.add(reference);
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Payee getPayee() {
        return payee;
    }

    public void setPayee(Payee payee) {
        this.payee = payee;
    }

    public ShipTo getShipTo() {
        return shipTo;
    }

    public void setShipTo(ShipTo shipTo) {
        this.shipTo = shipTo;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public PaymentTerms getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(PaymentTerms paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public List<AllowanceCharge> getAllowanceCharges() {
        return allowanceCharges;
    }

    public void setAllowanceCharges(List<AllowanceCharge> allowanceCharges) {
        this.allowanceCharges = allowanceCharges;
    }

    public void addAllowanceCharge(AllowanceCharge allowanceCharge) {
        this.allowanceCharges.add(allowanceCharge);
    }

    public MonetarySummation getMonetarySummation() {
        return monetarySummation;
    }

    public void setMonetarySummation(MonetarySummation monetarySummation) {
        this.monetarySummation = monetarySummation;
    }

    public List<Tax> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<Tax> taxes) {
        this.taxes = taxes;
    }

    public void addTax(Tax tax) {
        this.taxes.add(tax);
    }

    public List<DocumentReference> getAdditionalDocuments() {
        return additionalDocuments;
    }

    public void setAdditionalDocuments(List<DocumentReference> additionalDocuments) {
        this.additionalDocuments = additionalDocuments;
    }

    public void addAdditionalDocument(DocumentReference document) {
        this.additionalDocuments.add(document);
    }

    public List<InvoiceLine> getLines() {
        return lines;
    }

    public void setLines(List<InvoiceLine> lines) {
        this.lines = lines;
    }

    public void addLine(InvoiceLine line) {
        this.lines.add(line);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Invoice invoice;

        private Builder() {
            this.invoice = new Invoice();
        }

        public Builder invoiceNumber(String invoiceNumber) {
            invoice.setInvoiceNumber(invoiceNumber);
            return this;
        }

        public Builder issueDate(LocalDate issueDate) {
            invoice.setIssueDate(issueDate);
            return this;
        }

        public Builder documentTypeCode(DocumentTypeCode documentTypeCode) {
            invoice.setDocumentTypeCode(documentTypeCode);
            return this;
        }

        public Builder currency(CurrencyCode currency) {
            invoice.setCurrency(currency);
            return this;
        }

        public Builder taxCurrency(CurrencyCode taxCurrency) {
            invoice.setTaxCurrency(taxCurrency);
            return this;
        }

        public Builder taxPointDate(LocalDate taxPointDate) {
            invoice.setTaxPointDate(taxPointDate);
            return this;
        }

        public Builder taxPointDateCode(String taxPointDateCode) {
            invoice.setTaxPointDateCode(taxPointDateCode);
            return this;
        }

        public Builder dueDate(LocalDate dueDate) {
            invoice.setDueDate(dueDate);
            return this;
        }

        public Builder buyerReference(String buyerReference) {
            invoice.setBuyerReference(buyerReference);
            return this;
        }

        public Builder projectReference(String projectReference) {
            invoice.setProjectReference(projectReference);
            return this;
        }

        public Builder contractReference(String contractReference) {
            invoice.setContractReference(contractReference);
            return this;
        }

        public Builder purchaseOrderReference(String purchaseOrderReference) {
            invoice.setPurchaseOrderReference(purchaseOrderReference);
            return this;
        }

        public Builder salesOrderReference(String salesOrderReference) {
            invoice.setSalesOrderReference(salesOrderReference);
            return this;
        }

        public Builder invoicePeriod(InvoicePeriod invoicePeriod) {
            invoice.setInvoicePeriod(invoicePeriod);
            return this;
        }

        public Builder tenderReference(String tenderReference) {
            invoice.setTenderReference(tenderReference);
            return this;
        }

        public Builder objectIdentifier(String objectIdentifier) {
            invoice.setObjectIdentifier(objectIdentifier);
            return this;
        }

        public Builder buyerAccountingReference(String buyerAccountingReference) {
            invoice.setBuyerAccountingReference(buyerAccountingReference);
            return this;
        }

        public Builder profileIdentifier(String profileIdentifier) {
            invoice.setProfileIdentifier(profileIdentifier);
            return this;
        }

        public Builder customizationIdentifier(String customizationIdentifier) {
            invoice.setCustomizationIdentifier(customizationIdentifier);
            return this;
        }

        public Builder businessProcessIdentifier(String businessProcessIdentifier) {
            invoice.setBusinessProcessIdentifier(businessProcessIdentifier);
            return this;
        }

        public Builder notes(List<Note> notes) {
            invoice.setNotes(notes);
            return this;
        }

        public Builder addNote(Note note) {
            if (invoice.getNotes() == null) {
                invoice.setNotes(new ArrayList<>());
            }
            invoice.addNote(note);
            return this;
        }

        public Builder billingReferences(List<DocumentReference> billingReferences) {
            invoice.setBillingReferences(billingReferences);
            return this;
        }

        public Builder addBillingReference(DocumentReference reference) {
            if (invoice.getBillingReferences() == null) {
                invoice.setBillingReferences(new ArrayList<>());
            }
            invoice.addBillingReference(reference);
            return this;
        }

        public Builder seller(Seller seller) {
            invoice.setSeller(seller);
            return this;
        }

        public Builder buyer(Buyer buyer) {
            invoice.setBuyer(buyer);
            return this;
        }

        public Builder payee(Payee payee) {
            invoice.setPayee(payee);
            return this;
        }

        public Builder shipTo(ShipTo shipTo) {
            invoice.setShipTo(shipTo);
            return this;
        }

        public Builder delivery(Delivery delivery) {
            invoice.setDelivery(delivery);
            return this;
        }

        public Builder payment(Payment payment) {
            invoice.setPayment(payment);
            return this;
        }

        public Builder paymentTerms(PaymentTerms paymentTerms) {
            invoice.setPaymentTerms(paymentTerms);
            return this;
        }

        public Builder allowanceCharges(List<AllowanceCharge> allowanceCharges) {
            invoice.setAllowanceCharges(allowanceCharges);
            return this;
        }

        public Builder addAllowanceCharge(AllowanceCharge allowanceCharge) {
            if (invoice.getAllowanceCharges() == null) {
                invoice.setAllowanceCharges(new ArrayList<>());
            }
            invoice.addAllowanceCharge(allowanceCharge);
            return this;
        }

        public Builder monetarySummation(MonetarySummation monetarySummation) {
            invoice.setMonetarySummation(monetarySummation);
            return this;
        }

        public Builder taxes(List<Tax> taxes) {
            invoice.setTaxes(taxes);
            return this;
        }

        public Builder addTax(Tax tax) {
            if (invoice.getTaxes() == null) {
                invoice.setTaxes(new ArrayList<>());
            }
            invoice.addTax(tax);
            return this;
        }

        public Builder additionalDocuments(List<DocumentReference> additionalDocuments) {
            invoice.setAdditionalDocuments(additionalDocuments);
            return this;
        }

        public Builder addAdditionalDocument(DocumentReference document) {
            if (invoice.getAdditionalDocuments() == null) {
                invoice.setAdditionalDocuments(new ArrayList<>());
            }
            invoice.addAdditionalDocument(document);
            return this;
        }

        public Builder lines(List<InvoiceLine> lines) {
            invoice.setLines(lines);
            return this;
        }

        public Builder addLine(InvoiceLine line) {
            if (invoice.getLines() == null) {
                invoice.setLines(new ArrayList<>());
            }
            invoice.addLine(line);
            return this;
        }

        public Invoice build() {
            return invoice;
        }
    }
}
