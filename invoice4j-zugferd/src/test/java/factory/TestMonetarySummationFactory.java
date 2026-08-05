package factory;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetarySummation;
import java.math.BigDecimal;

public final class TestMonetarySummationFactory {

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
