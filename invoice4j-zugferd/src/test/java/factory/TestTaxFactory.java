package factory;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Tax;
import java.math.BigDecimal;

public final class TestTaxFactory {

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
}
