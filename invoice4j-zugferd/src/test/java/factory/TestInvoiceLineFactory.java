package factory;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.UnitCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoiceLine;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import java.math.BigDecimal;

public final class TestInvoiceLineFactory {

    public static InvoiceLine createInvoiceLine() {
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
}
