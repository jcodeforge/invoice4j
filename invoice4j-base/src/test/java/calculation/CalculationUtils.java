package calculation;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import java.math.BigDecimal;

public final class CalculationUtils {

    public static MonetaryAmount createMoney(String amount) {
        return MonetaryAmount.builder()
                .amount(new BigDecimal(amount))
                .currency(CurrencyCode.EUR)
                .build();
    }
}
