package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.BankAccount;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class BankAccountSerializer implements XmlSerializer<BankAccount> {

    @Override
    public void serialize(XmlWriter writer, BankAccount bankAccount) {
        if (bankAccount == null) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "PayeeFinancialAccount"
        );

        // BT-84
        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "ID",
                bankAccount.getIban()
        );

        // Account name
        writer.writeOptionalElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "Name",
                bankAccount.getAccountName()
        );

        // BT-86
        if (bankAccount.getBic() != null
                && !bankAccount.getBic().isBlank()) {

            writer.startElement(
                    "cac",
                    XmlNamespaces.UBL_CAC,
                    "FinancialInstitutionBranch"
            );

            writer.writeElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "ID",
                    bankAccount.getBic()
            );

            writer.endElement(); // FinancialInstitutionBranch
        }

        writer.endElement(); // PayeeFinancialAccount
    }
}