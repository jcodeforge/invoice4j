package factory;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.DocumentTypeCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import java.time.LocalDate;
import java.util.ArrayList;
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
                .allowanceCharges(new ArrayList<>())
                .lines(List.of(TestInvoiceLineFactory.createInvoiceLine()))
                .taxes(List.of(TestTaxFactory.createTax()))
                .monetarySummation(TestMonetarySummationFactory.createMonetarySummation())
                .build();
    }
}
