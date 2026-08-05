package factory;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.AllowanceCharge;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import java.math.BigDecimal;

public final class TestAllowanceChargeFactory {

    public static AllowanceCharge createAllowance(String amount) {
        return AllowanceCharge.builder()
                .charge(false)
                .amount(MonetaryAmount.builder()
                        .amount(new BigDecimal(amount))
                        .currency(CurrencyCode.EUR)
                        .build())
                .taxCategory(TaxCategoryCode.STANDARD)
                .taxRate(new BigDecimal("19"))
                .build();
    }
}
