package io.github.jcodeforge.invoice4jzugferd.examples;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.*;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public final class MinimalInvoiceFactory {

    private static final LocalDate ISSUE_DATE = LocalDate.of(2026, 1, 1);

    public static Invoice createMinimalInvoice() {
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
                .allowanceCharges(List.of())
                .lines(List.of(createFirstInvoiceLine()))
                .taxes(List.of(createTax()))
                .monetarySummation(createMonetarySummation())
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


    private static Seller createSeller() {
        return Seller.builder()
                .name("Müller & Söhne GmbH")
                .tradingName("JCodeForge")
                .address(createAddress())
                .electronicAddress(createElectronicAddress())
                .contact(createContact())
                .build();
    }

    private static Buyer createBuyer() {
        return Buyer.builder()
                .name("Müller & Söhne GmbH")
                .tradingName("JCodeForge")
                .address(createAddress())
                .electronicAddress(createElectronicAddress())
                .contact(createContact())
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

    public static PartyIdentifier createPartyIdentifier() {
        return PartyIdentifier.builder()
                .value("1234567890123")
                .scheme(IdentifierScheme.GLN)
                .build();
    }

    public static Address createAddress() {
        return Address.builder()
                .street("Example Street 1")
                .city("Leipzig")
                .postcode("04109")
                .countryCode(CountryCode.DE)
                .build();
    }

    private static Contact createContact() {
        return Contact.builder()
                .name("Müller Sühne")
                .email("info@example.com")
                .telephone("+49 341 123456")
                .build();
    }

    private static ElectronicAddress createElectronicAddress() {
        return ElectronicAddress.builder()
                .scheme(IdentifierScheme.EMAIL)
                .value("info@example.com")
                .build();
    }

    public static InvoicePeriod createInvoicePeriod() {
        return InvoicePeriod.builder()
                .startDate(LocalDate.of(2026, 1, 1))
                .endDate(LocalDate.of(2026, 1, 31))
                .build();
    }

    public static InvoiceLine createFirstInvoiceLine() {
        return InvoiceLine.builder()
                .id("1")
                .itemName("Item")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .netPrice(MonetaryAmount.builder()
                        .amount(new BigDecimal("100.00"))
                        .currency(CurrencyCode.EUR)
                        .build())
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(MonetaryAmount.builder()
                        .amount(new BigDecimal("100.00"))
                        .currency(CurrencyCode.EUR)
                        .build())
                .build();
    }

    public static Tax createTax() {
        return Tax.builder()
                .categoryCode(TaxCategoryCode.STANDARD)
                .rate(new BigDecimal("19"))
                .taxableAmount(MonetaryAmount.builder()
                        .amount(new BigDecimal("100.00"))
                        .currency(CurrencyCode.EUR)
                        .build())
                .taxAmount(MonetaryAmount.builder()
                        .amount(new BigDecimal("19"))
                        .currency(CurrencyCode.EUR)
                        .build())
                .build();
    }

    private static MonetaryAmount createEUMoney(String amount) {
        return MonetaryAmount.builder()
                .amount(new BigDecimal(amount))
                .currency(CurrencyCode.EUR)
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
}
