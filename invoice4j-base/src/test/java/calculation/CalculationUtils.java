package calculation;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.*;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class CalculationUtils {

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
                .unitPrice(createMoney(lineExtensionAmount))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .lineExtensionAmount(createMoney(lineExtensionAmount))
                .build();
    }

    public static InvoiceLine createReducedRateLine(String lineExtensionAmount) {
        return InvoiceLine.builder()
                .id(nextLineId())
                .itemName("Item")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(createMoney(lineExtensionAmount))
                .taxCategory(TaxCategoryCode.REDUCED_RATE)
                .taxRate(new BigDecimal("7"))
                .lineExtensionAmount(createMoney(lineExtensionAmount))
                .build();
    }

    public static InvoiceLine createZeroRatedLine(String lineExtensionAmount) {
        return InvoiceLine.builder()
                .id(nextLineId())
                .itemName("Item")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(createMoney(lineExtensionAmount))
                .taxCategory(TaxCategoryCode.ZERO_RATED)
                .taxRate(BigDecimal.ZERO)
                .lineExtensionAmount(createMoney(lineExtensionAmount))
                .build();
    }

    public static InvoiceLine createExemptLine(String lineExtensionAmount) {
        return InvoiceLine.builder()
                .id(nextLineId())
                .itemName("Item")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(createMoney(lineExtensionAmount))
                .taxCategory(TaxCategoryCode.EXEMPT)
                .taxRate(BigDecimal.ZERO)
                .lineExtensionAmount(createMoney(lineExtensionAmount))
                .build();
    }

    public static InvoiceLine createReverseChargeLine(String lineExtensionAmount) {
        return InvoiceLine.builder()
                .id(nextLineId())
                .itemName("Item")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(createMoney(lineExtensionAmount))
                .taxCategory(TaxCategoryCode.REVERSE_CHARGE)
                .taxRate(BigDecimal.ZERO)
                .lineExtensionAmount(createMoney(lineExtensionAmount))
                .build();
    }

    public static InvoiceLine createOutsideScopeLine(String lineExtensionAmount) {
        return InvoiceLine.builder()
                .id(nextLineId())
                .itemName("Item")
                .quantity(BigDecimal.ONE)
                .unitCode(UnitCode.ONE)
                .unitPrice(createMoney(lineExtensionAmount))
                .taxCategory(TaxCategoryCode.OUTSIDE_SCOPE)
                .taxRate(BigDecimal.ZERO)
                .lineExtensionAmount(createMoney(lineExtensionAmount))
                .build();
    }

    public static AllowanceCharge createAllowance(String amount) {
        return AllowanceCharge.builder()
                .charge(false)
                .amount(createMoney(amount))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();
    }

    public static MonetaryAmount createMoney(String amount) {
        return MonetaryAmount.builder()
                .amount(new BigDecimal(amount))
                .currency(CurrencyCode.EUR)
                .build();
    }

    private static Seller createSeller() {
        return Seller.builder()
                .name("Seller GmbH")
                .address(createAddress())
                .build();
    }

    private static Buyer createBuyer() {
        return Buyer.builder()
                .name("Buyer GmbH")
                .address(createAddress())
                .build();
    }

    public static AllowanceCharge createCharge(String amount) {
        return AllowanceCharge.builder()
                .charge(true)
                .amount(createMoney(amount))
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();
    }

    public static MonetarySummation createMonetarySummation() {
        return MonetarySummation.builder()
                .lineExtensionAmount(createMoney("100.00"))
                .allowanceTotalAmount(createMoney("0.00"))
                .chargeTotalAmount(createMoney("0.00"))
                .taxExclusiveAmount(createMoney("100.00"))
                .taxAmount(createMoney("19.00"))
                .taxInclusiveAmount(createMoney("119.00"))
                .prepaidAmount(createMoney("0.00"))
                .roundingAmount(createMoney("0.00"))
                .payableAmount(createMoney("119.00"))
                .build();
    }

    public static MonetarySummation createMonetarySummation(String prepaidAmount, String roundingAmount) {
        return MonetarySummation.builder()
                .lineExtensionAmount(createMoney("100.00"))
                .allowanceTotalAmount(createMoney("0.00"))
                .chargeTotalAmount(createMoney("0.00"))
                .taxExclusiveAmount(createMoney("100.00"))
                .taxAmount(createMoney("19.00"))
                .taxInclusiveAmount(createMoney("119.00"))
                .prepaidAmount(createMoney(prepaidAmount))
                .roundingAmount(createMoney(roundingAmount))
                .payableAmount(createMoney("119.00"))
                .build();
    }

    private static Address createAddress() {
        return Address.builder()
                .street("Main Street 1")
                .city("Berlin")
                .postcode("10115")
                .countryCode(CountryCode.DE)
                .build();
    }

    private static Delivery createDelivery() {
        LocalDate date = LocalDate.of(2026, 1, 15);

        return Delivery.builder()
                .actualDeliveryDate(date)
                .deliveryPeriodStartDate(date)
                .deliveryPeriodEndDate(date)
                .address(createAddress())
                .build();
    }

    public static Invoice.Builder createSimpleInvoice() {
        return Invoice.builder()
                .currency(CurrencyCode.EUR)
                .seller(createSeller())
                .buyer(createBuyer())
                .issueDate(LocalDate.now())
                .documentTypeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .invoiceNumber("INV-1")
                .addLine(createInvoiceLine("100.00"))
                .delivery(createDelivery())
                .invoicePeriod(createInvoicePeriod())
                .monetarySummation(createMonetarySummation());
    }

    public static Invoice.Builder createCalculatedInvoice() {
        return Invoice.builder()
                .currency(CurrencyCode.EUR)
                .seller(createSeller())
                .buyer(createBuyer())
                .issueDate(LocalDate.now())
                .documentTypeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .invoiceNumber("INV-1")
                .addLine(createInvoiceLine("100.00"))
                .delivery(createDelivery())
                .invoicePeriod(createInvoicePeriod())
                .taxes(List.of(
                        Tax.builder()
                                .categoryCode(TaxCategoryCode.STANDARD)
                                .rate(new BigDecimal("19"))
                                .taxableAmount(createMoney("100.00"))
                                .taxAmount(createMoney("19.00"))
                                .build()
                ))
                .allowanceCharges(List.of())
                .monetarySummation(
                        MonetarySummation.builder()
                                .lineExtensionAmount(createMoney("100.00"))
                                .allowanceTotalAmount(createMoney("0.00"))
                                .chargeTotalAmount(createMoney("0.00"))
                                .taxExclusiveAmount(createMoney("100.00"))
                                .taxAmount(createMoney("19.00"))
                                .taxInclusiveAmount(createMoney("119.00"))
                                .prepaidAmount(createMoney("0.00"))
                                .roundingAmount(createMoney("0.00"))
                                .payableAmount(createMoney("119.00"))
                                .build()
                );
    }

    private static InvoicePeriod createInvoicePeriod() {
        return InvoicePeriod.builder()
                .startDate(LocalDate.of(2026, 1, 15))
                .endDate(LocalDate.of(2026, 1, 15))
                .build();
    }

    public static Tax createStandardTax(String taxableAmount, String taxAmount) {
        return Tax.builder()
                .categoryCode(TaxCategoryCode.STANDARD)
                .rate(new BigDecimal("19"))
                .taxableAmount(createMoney(taxableAmount))
                .taxAmount(createMoney(taxAmount))
                .build();
    }

    public static Tax createReducedRateTax(String taxableAmount, String taxAmount) {
        return Tax.builder()
                .categoryCode(TaxCategoryCode.REDUCED_RATE)
                .rate(new BigDecimal("7"))
                .taxableAmount(createMoney(taxableAmount))
                .taxAmount(createMoney(taxAmount))
                .build();
    }

    public static Tax createZeroRatedTax(String taxableAmount) {
        return Tax.builder()
                .categoryCode(TaxCategoryCode.ZERO_RATED)
                .rate(BigDecimal.ZERO)
                .taxableAmount(createMoney(taxableAmount))
                .taxAmount(createMoney("0.00"))
                .build();
    }

    public static String nextLineId() {
        return String.valueOf(NEXT_ID.incrementAndGet());
    }
}
