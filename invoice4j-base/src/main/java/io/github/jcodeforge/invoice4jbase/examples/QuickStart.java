package io.github.jcodeforge.invoice4jbase.examples;

import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.*;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Quick start example for invoice4j.
 */
public final class QuickStart {

    public static void main(String[] args) {
        Invoice invoice = Invoice.builder()
                .invoiceNumber("INV-2026-0001")
                .documentTypeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .issueDate(LocalDate.now())
                .currency(CurrencyCode.EUR)
                .seller(createSeller())
                .buyer(createBuyer())
                .delivery(createDelivery())
                .invoicePeriod(createInvoicePeriod())
                .paymentMeans(createPaymentMeans())
                .paymentTerms(createPaymentTerms())
                .addLine(createInvoiceLine())
                .monetarySummation(createMonetarySummation())
                .build();

        // Calculate invoice totals
        Invoice calculated = new InvoiceCalculator().calculate(invoice);

        System.out.println("Invoice Number : " + calculated.getInvoiceNumber());
        System.out.println("Net Amount     : " + calculated.getMonetarySummation().getTaxExclusiveAmount().getAmount());
        System.out.println("VAT Amount     : " + calculated.getMonetarySummation().getTaxAmount().getAmount());
        System.out.println("Gross Amount   : " + calculated.getMonetarySummation().getTaxInclusiveAmount().getAmount());
        System.out.println("Payable Amount : " + calculated.getMonetarySummation().getPayableAmount().getAmount());
    }

    private static InvoiceLine createInvoiceLine() {
        return InvoiceLine.builder()
                .id("1")
                .itemName("Notebook")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(money("100.00"))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(money("100.00"))
                .build();
    }

    private static InvoicePeriod createInvoicePeriod() {
        return InvoicePeriod.builder()
                .startDate(LocalDate.of(2026, 1, 1))
                .endDate(LocalDate.of(2026, 1, 31))
                .build();
    }

    private static Seller createSeller() {
        return Seller.builder()
                .name("JCodeForge GmbH")
                .address(createAddress())
                .build();
    }

    private static Buyer createBuyer() {
        return Buyer.builder()
                .name("Example Customer Ltd.")
                .address(createCustomerAddress())
                .build();
    }

    private static Delivery createDelivery() {
        return Delivery.builder()
                .actualDeliveryDate(LocalDate.of(2026, 1, 15))
                .deliveryPeriodStartDate(LocalDate.of(2026, 1, 1))
                .deliveryPeriodEndDate(LocalDate.of(2026, 1, 31))
                .address(createAddress())
                .build();
    }

    private static PaymentMeans createPaymentMeans() {
        return PaymentMeans.builder()
                .meansCode(PaymentMeansCode.CREDIT_TRANSFER)
                .meansDescription("SEPA Credit Transfer")
                .remittanceInformation("Invoice INV-2026-0001")
                .bankAccount(
                        BankAccount.builder()
                                .iban("DE89370400440532013000")
                                .bic("COBADEFFXXX")
                                .accountName("JCodeForge GmbH")
                                .build()
                )
                .build();
    }

    private static PaymentTerms createPaymentTerms() {
        return PaymentTerms.builder()
                .description("Payment due within 30 days.")
                .dueDate(LocalDate.now().plusDays(30))
                .build();
    }

    private static MonetarySummation createMonetarySummation() {
        return MonetarySummation.builder()
                .lineExtensionAmount(money("100.00"))
                .allowanceTotalAmount(money("0.00"))
                .chargeTotalAmount(money("0.00"))
                .taxExclusiveAmount(money("100.00"))
                .taxAmount(money("19.00"))
                .taxInclusiveAmount(money("119.00"))
                .prepaidAmount(money("0.00"))
                .roundingAmount(money("0.00"))
                .payableAmount(money("119.00"))
                .build();
    }

    private static Address createAddress() {
        return Address.builder()
                .street("Main Street 1")
                .city("Dresden")
                .postcode("01067")
                .countryCode(CountryCode.DE)
                .build();
    }

    private static Address createCustomerAddress() {
        return Address.builder()
                .street("Customer Street 42")
                .city("Berlin")
                .postcode("10115")
                .countryCode(CountryCode.DE)
                .build();
    }

    private static MonetaryAmount money(String amount) {
        return MonetaryAmount.builder()
                .amount(new BigDecimal(amount))
                .currency(CurrencyCode.EUR)
                .build();
    }
}