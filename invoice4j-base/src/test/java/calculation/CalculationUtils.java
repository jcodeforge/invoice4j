package calculation;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.*;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.IdentifierScheme;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.*;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class CalculationUtils {

    private static final LocalDate ISSUE_DATE = LocalDate.of(2026, 1, 1);

    private static final AtomicInteger NEXT_ID = new AtomicInteger();

    public static InvoiceLine createInvoiceLine(String lineExtensionAmount) {
        return createStandardLine(lineExtensionAmount);
    }

    public static InvoiceLine createStandardLine(String lineExtensionAmount) {
        return InvoiceLine.builder()
                .id(nextLineId())
                .itemName("Item")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(createEUMoney(lineExtensionAmount))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(createEUMoney(lineExtensionAmount))
                .build();
    }

    public static InvoiceLine createStandardLine(String id, String lineExtensionAmount) {
        return InvoiceLine.builder()
                .id(id)
                .itemName("Item")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(createEUMoney(lineExtensionAmount))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(createEUMoney(lineExtensionAmount))
                .build();
    }

    public static InvoiceLine createReducedRateLine(String lineExtensionAmount) {
        return InvoiceLine.builder()
                .id(nextLineId())
                .itemName("Item")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(createEUMoney(lineExtensionAmount))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("7"))
                .lineExtensionAmount(createEUMoney(lineExtensionAmount))
                .build();
    }

    public static InvoiceLine createZeroRatedLine(String lineExtensionAmount) {
        return InvoiceLine.builder()
                .id(nextLineId())
                .itemName("Item")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(createEUMoney(lineExtensionAmount))
                .taxCategory(TaxCategoryCode.ZERO_RATED)
                .taxRate(BigDecimal.ZERO)
                .lineExtensionAmount(createEUMoney(lineExtensionAmount))
                .build();
    }

    public static DocumentReference createDocumentReference() {
        return DocumentReference.builder()
                .id("PO-12345")
                .typeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .name("Purchase Order")
                .description("Customer purchase order")
                .issueDate(LocalDate.of(2026, 1, 1))
                .uri(URI.create("https://example.com/order.pdf"))
                .attachment(createAttachment())
                .build();
    }

    public static Attachment createAttachment() {
        return Attachment.builder()
                .fileName("invoice.pdf")
                .mimeType(MimeType.PDF)
                .content(new byte[]{1, 2, 3})
                .build();
    }

    public static Payee createPayee() {
        return Payee.builder()
                .name("JCodeForge GmbH")
                .tradingName("JCodeForge")
                .address(createAddress())
                .electronicAddress(createElectronicAddress())
                .contact(createContact())
                .addIdentifier(createPartyIdentifier())
                .build();
    }

    public static InvoiceLine createExemptLine(String lineExtensionAmount) {
        return InvoiceLine.builder()
                .id(nextLineId())
                .itemName("Item")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(createEUMoney(lineExtensionAmount))
                .taxCategory(TaxCategoryCode.EXEMPT)
                .taxRate(BigDecimal.ZERO)
                .lineExtensionAmount(createEUMoney(lineExtensionAmount))
                .build();
    }

    public static PartyIdentifier createPartyIdentifier() {
        return PartyIdentifier.builder()
                .value("1234567890123")
                .scheme(IdentifierScheme.GLN)
                .build();
    }

    public static ShipTo createShipTo() {
        return ShipTo.builder()
                .name("Customer Warehouse")
                .tradingName("Warehouse")
                .address(createAddress())
                .electronicAddress(createElectronicAddress())
                .contact(createContact())
                .addIdentifier(createPartyIdentifier())
                .build();
    }

    public static InvoiceLine createReverseChargeLine(String lineExtensionAmount) {
        return InvoiceLine.builder()
                .id(nextLineId())
                .itemName("Item")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(createEUMoney(lineExtensionAmount))
                .taxCategory(TaxCategoryCode.REVERSE_CHARGE)
                .taxRate(BigDecimal.ZERO)
                .lineExtensionAmount(createEUMoney(lineExtensionAmount))
                .build();
    }

    public static InvoiceLine createOutsideScopeLine(String lineExtensionAmount) {
        return InvoiceLine.builder()
                .id(nextLineId())
                .itemName("Item")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(createEUMoney(lineExtensionAmount))
                .taxCategory(TaxCategoryCode.OUTSIDE_SCOPE)
                .taxRate(BigDecimal.ZERO)
                .lineExtensionAmount(createEUMoney(lineExtensionAmount))
                .build();
    }

    public static AllowanceCharge createAllowance(String amount) {
        return AllowanceCharge.builder()
                .charge(false)
                .amount(createEUMoney(amount))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();
    }

    public static MonetaryAmount createEUMoney(String amount) {
        return MonetaryAmount.builder()
                .amount(new BigDecimal(amount))
                .currency(CurrencyCode.EUR)
                .build();
    }

    public static MonetaryAmount createUsdMoney(String amount) {
        return MonetaryAmount.builder()
                .amount(new BigDecimal(amount))
                .currency(CurrencyCode.USD)
                .build();
    }

    public static Contact createContact() {
        return Contact.builder()
                .name("Max Mustermann")
                .telephone("+49 351 1234567")
                .email("max.mustermann@jcodeforge.io")
                .department("Accounting")
                .note("Primary contact")
                .build();
    }

    public static Seller createSeller() {
        return Seller.builder()
                .name("JCodeForge GmbH")
                .tradingName("JCodeForge")
                .address(createAddress())
                .electronicAddress(createElectronicAddress())
                .contact(createContact())
                .build();
    }

    public static ElectronicAddress createElectronicAddress() {
        return ElectronicAddress.builder()
                .value("info@jcodeforge.io")
                .scheme(IdentifierScheme.EMAIL)
                .build();
    }

    public static Buyer createBuyer() {
        return Buyer.builder()
                .name("JCodeForge GmbH")
                .tradingName("JCodeForge")
                .address(createAddress())
                .electronicAddress(createElectronicAddress())
                .contact(createContact())
                .build();
    }

    public static AllowanceCharge createCharge(String amount) {
        return AllowanceCharge.builder()
                .charge(true)
                .amount(createEUMoney(amount))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();
    }

    public static MonetarySummation createMonetarySummation() {
        return MonetarySummation.builder()
                .lineExtensionAmount(createEUMoney("100.00"))
                .allowanceTotalAmount(createEUMoney("0.00"))
                .chargeTotalAmount(createEUMoney("0.00"))
                .taxExclusiveAmount(createEUMoney("100.00"))
                .taxAmount(createEUMoney("19.00"))
                .taxInclusiveAmount(createEUMoney("119.00"))
                .prepaidAmount(createEUMoney("0.00"))
                .roundingAmount(createEUMoney("0.00"))
                .payableAmount(createEUMoney("119.00"))
                .build();
    }

    public static MonetarySummation createMonetarySummation(String prepaidAmount, String roundingAmount) {
        return MonetarySummation.builder()
                .lineExtensionAmount(createEUMoney("100.00"))
                .allowanceTotalAmount(createEUMoney("0.00"))
                .chargeTotalAmount(createEUMoney("0.00"))
                .taxExclusiveAmount(createEUMoney("100.00"))
                .taxAmount(createEUMoney("19.00"))
                .taxInclusiveAmount(createEUMoney("119.00"))
                .prepaidAmount(createEUMoney(prepaidAmount))
                .roundingAmount(createEUMoney(roundingAmount))
                .payableAmount(createEUMoney("119.00"))
                .build();
    }

    public static Address createAddress() {
        return Address.builder()
                .street("Main Street 1")
                .additionalStreet("Building A")
                .city("Berlin")
                .postcode("10115")
                .countryCode(CountryCode.DE)
                .countrySubdivision("Wedding")
                .build();
    }

    public static Delivery createDelivery() {
        return Delivery.builder()
                .actualDeliveryDate(LocalDate.of(2026, 1, 15))
                .deliveryPeriodStartDate(LocalDate.of(2026, 1, 1))
                .deliveryPeriodEndDate(LocalDate.of(2026, 1, 31))
                .address(createAddress())
                .build();
    }

    public static Invoice.Builder createSimpleInvoice() {
        return Invoice.builder()
                .currency(CurrencyCode.EUR)
                .seller(createSeller())
                .buyer(createBuyer())
                .issueDate(ISSUE_DATE)
                .documentTypeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .invoiceNumber("INV-1")
                .addLine(createInvoiceLine("100.00"))
                .delivery(createDelivery())
                .invoicePeriod(createInvoicePeriod())
                .paymentMeans(createPaymentMeans())
                .paymentTerms(createPaymentTerms())
                .monetarySummation(createMonetarySummation());
    }

    public static Invoice.Builder createCalculatedInvoice() {
        return Invoice.builder()
                .currency(CurrencyCode.EUR)
                .seller(createSeller())
                .buyer(createBuyer())
                .issueDate(ISSUE_DATE)
                .documentTypeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .invoiceNumber("INV-1")
                .addLine(createInvoiceLine("100.00"))
                .delivery(createDelivery())
                .invoicePeriod(createInvoicePeriod())
                .paymentMeans(createPaymentMeans())
                .paymentTerms(createPaymentTerms())
                .taxes(List.of(
                        Tax.builder()
                                .categoryCode(TaxCategoryCode.STANDARD)
                                .rate(new BigDecimal("19"))
                                .taxableAmount(createEUMoney("100.00"))
                                .taxAmount(createEUMoney("19.00"))
                                .build()
                ))
                .allowanceCharges(List.of())
                .monetarySummation(
                        MonetarySummation.builder()
                                .lineExtensionAmount(createEUMoney("100.00"))
                                .allowanceTotalAmount(createEUMoney("0.00"))
                                .chargeTotalAmount(createEUMoney("0.00"))
                                .taxExclusiveAmount(createEUMoney("100.00"))
                                .taxAmount(createEUMoney("19.00"))
                                .taxInclusiveAmount(createEUMoney("119.00"))
                                .prepaidAmount(createEUMoney("0.00"))
                                .roundingAmount(createEUMoney("0.00"))
                                .payableAmount(createEUMoney("119.00"))
                                .build()
                );
    }

    public static Invoice.Builder createInvoiceBuilder() {
        return Invoice.builder()
                .invoiceNumber("INV-2026-0001")
                .documentTypeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .issueDate(ISSUE_DATE)
                .currency(CurrencyCode.EUR)
                .seller(createSeller())
                .buyer(createBuyer())
                .delivery(createDelivery())
                .paymentMeans(createPaymentMeans())
                .paymentTerms(createPaymentTerms())
                .invoicePeriod(createInvoicePeriod())
                .allowanceCharges(new ArrayList<>())
                .lines(List.of(
                        createStandardLine("100.00")
                ))

                .taxes(List.of(
                        createStandardTax("100.00", "19.00")
                ))
                .monetarySummation(createMonetarySummation());
    }

    public static InvoicePeriod createInvoicePeriod() {
        return InvoicePeriod.builder()
                .startDate(LocalDate.of(2026, 1, 1))
                .endDate(LocalDate.of(2026, 1, 31))
                .build();
    }

    public static Tax createStandardTax(String taxableAmount, String taxAmount) {
        return Tax.builder()
                .categoryCode(TaxCategoryCode.STANDARD)
                .rate(new BigDecimal("19"))
                .taxableAmount(createEUMoney(taxableAmount))
                .taxAmount(createEUMoney(taxAmount))
                .build();
    }

    public static Tax createReducedRateTax(String taxableAmount, String taxAmount) {
        return Tax.builder()
                .categoryCode(TaxCategoryCode.STANDARD)
                .rate(new BigDecimal("7"))
                .taxableAmount(createEUMoney(taxableAmount))
                .taxAmount(createEUMoney(taxAmount))
                .build();
    }

    public static Tax createZeroRatedTax(String taxableAmount) {
        return Tax.builder()
                .categoryCode(TaxCategoryCode.ZERO_RATED)
                .rate(BigDecimal.ZERO)
                .taxableAmount(createEUMoney(taxableAmount))
                .taxAmount(createEUMoney("0.00"))
                .build();
    }

    public static BankAccount createBankAccount() {
        return BankAccount.builder()
                .iban("DE89370400440532013000")
                .bic("COBADEFFXXX")
                .accountName("JCodeForge GmbH")
                .build();
    }

    public static PaymentTerms createPaymentTerms() {
        return PaymentTerms.builder()
                .description("Payment due within 30 days.")
                .dueDate(LocalDate.of(2026, 1, 31))
                .build();
    }

    public static PaymentMeans createPaymentMeans() {
        return PaymentMeans.builder()
                .meansCode(PaymentMeansCode.CREDIT_TRANSFER)
                .meansDescription("SEPA Credit Transfer")
                .remittanceInformation("Invoice INV-2026-0001")
                .bankAccount(createBankAccount())
                .build();
    }

    public static PaymentDiscount createPaymentDiscount() {
        return PaymentDiscount.builder()
                .percentage(new BigDecimal("2.00"))
                .amount(createEUMoney("2.00"))
                .paymentDays(14)
                .build();
    }

    public static PaymentDiscount createPercentagePaymentDiscount() {
        return PaymentDiscount.builder()
                .percentage(new BigDecimal("2.00"))
                .paymentDays(14)
                .build();
    }

    public static PaymentDiscount createAmountPaymentDiscount() {
        return PaymentDiscount.builder()
                .amount(createEUMoney("2.00"))
                .paymentDays(14)
                .build();
    }

    public static PaymentDiscount createPercentageAndAmountPaymentDiscount() {
        return PaymentDiscount.builder()
                .percentage(new BigDecimal("2.00"))
                .amount(createEUMoney("2.00"))
                .paymentDays(14)
                .build();
    }

    public static TaxIdentifier createTaxIdentifier() {
        return TaxIdentifier.builder()
                .value("DE123456789")
                .type(TaxRegistrationScheme.VAT_REGISTRATION_NUMBER)
                .build();
    }

    public static String nextLineId() {
        return String.valueOf(NEXT_ID.incrementAndGet());
    }
}
