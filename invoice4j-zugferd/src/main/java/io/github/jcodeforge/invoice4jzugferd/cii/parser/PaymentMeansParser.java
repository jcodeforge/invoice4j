package io.github.jcodeforge.invoice4jzugferd.cii.parser;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.PaymentMeansCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PaymentMeans;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlReader;

public final class PaymentMeansParser implements XmlParser<PaymentMeans> {

    private final BankAccountParser bankAccountParser = new BankAccountParser();

    @Override
    public PaymentMeans parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return PaymentMeans.builder()
                .meansCode(readMeansCode(reader, basePath))
                .meansDescription(reader.readString(basePath + "/ram:Information"))
                .remittanceInformation(reader.readString(basePath + "/ram:PaymentReference"))
                .bankAccount(bankAccountParser.parse(reader,
                        basePath + "/ram:PayeePartyCreditorFinancialAccount"))
                .build();
    }

    private PaymentMeansCode readMeansCode(XmlReader reader, String basePath) {
        String code = reader.readString(basePath + "/ram:TypeCode");

        return code == null ? null : PaymentMeansCode.fromCode(code);
    }
}