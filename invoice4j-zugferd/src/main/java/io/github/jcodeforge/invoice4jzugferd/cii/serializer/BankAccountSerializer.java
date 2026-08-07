package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.BankAccount;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;

public final class BankAccountSerializer implements XmlSerializer<BankAccount> {

    @Override
    public void serialize(XmlWriter writer, BankAccount bankAccount) {
        if (bankAccount == null) {
            return;
        }

        writer.startElement(XmlNamespaces.RAM, "PayeePartyCreditorFinancialAccount");
        writer.writeOptionalElement(XmlNamespaces.RAM, "IBANID", bankAccount.getIban());
        writer.writeOptionalElement(XmlNamespaces.RAM, "AccountName", bankAccount.getAccountName());
        writer.endElement();

        if (bankAccount.getBic() != null) {
            writer.startElement(XmlNamespaces.RAM, "PayeeSpecifiedCreditorFinancialInstitution");
            writer.writeElement(XmlNamespaces.RAM, "BICID", bankAccount.getBic());
            writer.endElement();
        }
    }
}