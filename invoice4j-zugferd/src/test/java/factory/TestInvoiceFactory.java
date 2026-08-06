package factory;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.DocumentTypeCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Note;
import java.time.LocalDate;
import java.util.List;

public final class TestInvoiceFactory {

    private static final LocalDate ISSUE_DATE = LocalDate.of(2026, 1, 1);

    public static Invoice createMinimalInvoice() {
        return Invoice.builder()
                .invoiceNumber("INV-2026-0001")
                .documentTypeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .issueDate(ISSUE_DATE)
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
    }

    public static Invoice createCompleteInvoice() {
        return Invoice.builder()
                // Header
                .invoiceNumber("INV-2026-0002")
                .documentTypeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .issueDate(ISSUE_DATE)
                .currency(CurrencyCode.EUR)
                .taxCurrency(CurrencyCode.EUR)
                // References
                .buyerReference("BUYER-REF-001")
                .contractReference("CONTRACT-2026")
                .projectReference("PROJECT-42")
                .purchaseOrderReference("PO-4711")
                .salesOrderReference("SO-12345")
                .tenderReference("TENDER-01")
                // Parties
                .seller(TestPartyFactory.createSeller())
                .buyer(TestPartyFactory.createBuyer())
                .payee(TestPartyFactory.createPayee())
                // Delivery
                .delivery(TestDeliveryFactory.createDelivery())
                // Payment
                .paymentMeans(TestPaymentFactory.createPaymentMeans())
                .paymentTerms(TestPaymentFactory.createPaymentTerms())
                // Period
                .invoicePeriod(TestInvoicePeriodFactory.createInvoicePeriod())
                // Notes
                .notes(List.of(
                        Note.builder()
                                .text("Thank you for your business.")
                                .build(),
                        Note.builder()
                                .text("Payment due within 30 days.")
                                .subjectCode("AAI")
                                .build()
                ))
                // Invoice lines
                .lines(List.of(TestInvoiceLineFactory.createFirstInvoiceLine(),
                        TestInvoiceLineFactory.createSecoundInvoiceLine()))
                // Taxes
                .taxes(List.of(TestTaxFactory.createTax()))
                // Allowance / Charge
                .allowanceCharges(List.of(TestAllowanceChargeFactory.createAllowance("10.00")))
                // Billing references
                .billingReferences(List.of(TestDocumentReferenceFactory.createBillingReference()))
                // Additional documents
                .additionalDocuments(List.of(TestDocumentReferenceFactory.createAdditionalDocument()))
                // Totals
                .monetarySummation(TestMonetarySummationFactory.createMonetarySummation())
                .build();
    }

    public static Invoice createInvoiceWithAllowanceAndCharge() {
        return Invoice.builder()
                .invoiceNumber("INV-2026-0003")
                .documentTypeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .issueDate(ISSUE_DATE)
                .currency(CurrencyCode.EUR)
                .taxCurrency(CurrencyCode.EUR)
                // Parties
                .seller(TestPartyFactory.createSeller())
                .buyer(TestPartyFactory.createBuyer())
                .payee(TestPartyFactory.createPayee())
                // Delivery
                .delivery(TestDeliveryFactory.createDelivery())
                // Payment
                .paymentMeans(TestPaymentFactory.createPaymentMeans())
                .paymentTerms(TestPaymentFactory.createPaymentTerms())
                // Period
                .invoicePeriod(TestInvoicePeriodFactory.createInvoicePeriod())
                // Invoice lines
                .lines(List.of(
                        TestInvoiceLineFactory.createFirstInvoiceLine(),
                        TestInvoiceLineFactory.createSecoundInvoiceLine()
                ))
                // Taxes
                .taxes(List.of(
                        TestTaxFactory.createTax()
                ))
                // One allowance + one charge
                .allowanceCharges(List.of(
                        TestAllowanceChargeFactory.createAllowance("10.00"),
                        TestAllowanceChargeFactory.createCharge("15.00")
                ))
                // Totals
                .monetarySummation(TestMonetarySummationFactory.createMonetarySummation())
                .build();
    }
}
