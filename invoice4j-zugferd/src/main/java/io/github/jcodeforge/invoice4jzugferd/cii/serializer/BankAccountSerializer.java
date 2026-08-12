package io.github.jcodeforge.invoice4jzugferd.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.BankAccount;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiConfigurationOptions;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiProfile;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jzugferd.xml.XmlWriter;
import java.util.Objects;

public final class BankAccountSerializer implements XmlSerializer<BankAccount> {

    private final CiiConfigurationOptions options;

    public BankAccountSerializer(CiiConfigurationOptions options) {
        this.options = Objects.requireNonNull(options, "options must not be null");
    }

    @Override
    public void serialize(XmlWriter writer, BankAccount bankAccount) {
        if (bankAccount == null) {
            return;
        }

        writer.startElement(XmlNamespaces.RAM, "PayeePartyCreditorFinancialAccount");
        writer.writeOptionalElement(XmlNamespaces.RAM, "IBANID", bankAccount.getIban());

        // Account name is not supported by ZUGFeRD BASIC.
        if (options.getProfile() != CiiProfile.ZUGFERD_BASIC && options.getProfile() != CiiProfile.ZUGFERD_BASIC_WL) {
            writer.writeOptionalElement(
                    XmlNamespaces.RAM,
                    "AccountName",
                    bankAccount.getAccountName()
            );
        }

        // BT-84-0
        // ProprietaryID can be added here when your BankAccount
        // model provides the national account number.
        //
        // writer.writeOptionalElement(
        //         XmlNamespaces.RAM,
        //         "ProprietaryID",
        //         bankAccount.getProprietaryId()
        // );

        // Close PayeePartyCreditorFinancialAccount.
        writer.endElement();

        // BT-86
        // PayeeSpecifiedCreditorFinancialInstitution is a sibling
        // of PayeePartyCreditorFinancialAccount.
        if (bankAccount.getBic() != null && options.getProfile() != CiiProfile.ZUGFERD_BASIC
                && options.getProfile() != CiiProfile.ZUGFERD_BASIC_WL) {
            writer.startElement(XmlNamespaces.RAM, "PayeeSpecifiedCreditorFinancialInstitution");
            writer.writeElement(XmlNamespaces.RAM, "BICID", bankAccount.getBic());
            writer.endElement();
        }
    }
}