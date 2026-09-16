package io.github.jcodeforge.invoice4jbase.ubl.parser;

import io.github.jcodeforge.invoice4jbase.xml.XmlParser;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.PaymentMeansCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PaymentMeans;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public final class PaymentMeansParser implements XmlParser<PaymentMeans> {

    private final BankAccountParser bankAccountParser = new BankAccountParser();

    @Override
    public PaymentMeans parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return PaymentMeans.builder()
                .meansCode(readMeansCode(reader, basePath))
                .meansDescription(reader.readString(
                        basePath + "/cac:InstructionNote"))
                .remittanceInformation(reader.readString(
                        basePath + "/cbc:InstructionID"))
                .bankAccount(bankAccountParser.parse(
                        reader,
                        basePath + "/cac:PayeeFinancialAccount"))
                .build();
    }

    private PaymentMeansCode readMeansCode(XmlReader reader, String basePath) {
        String code = reader.readString(
                basePath + "/cbc:PaymentMeansCode");

        return code == null ? null : PaymentMeansCode.fromCode(code);
    }
}