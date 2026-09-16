package io.github.jcodeforge.invoice4jbase.ubl.parser;

import io.github.jcodeforge.invoice4jbase.xml.XmlParser;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.BankAccount;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public final class BankAccountParser implements XmlParser<BankAccount> {

    @Override
    public BankAccount parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return BankAccount.builder()
                .iban(reader.readString(
                        basePath + "/cbc:ID"))
                .bic(reader.readString(basePath + "/cac:FinancialInstitutionBranch/cbc:ID"))
                .accountName(reader.readString(
                        basePath + "/cbc:Name"))
                .build();
    }
}