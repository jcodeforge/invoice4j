package io.github.jcodeforge.invoice4jzugferd.cii.parser;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.BankAccount;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlReader;

public final class BankAccountParser implements XmlParser<BankAccount> {

    @Override
    public BankAccount parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return BankAccount.builder()
                .iban(reader.readString(basePath + "/ram:IBANID"))
                .bic(reader.readString(
                        basePath
                                + "/../ram:PayeeSpecifiedCreditorFinancialInstitution"
                                + "/ram:BICID"))
                .accountName(reader.readString(basePath + "/ram:AccountName"))
                .build();
    }
}