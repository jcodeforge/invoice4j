package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.BankAccount;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import org.junit.Test;

import static org.junit.Assert.*;

public class BankAccountTest {

    @Test
    public void shouldCreateBankAccount() {
        BankAccount account = CalculationUtils.createBankAccount();

        assertEquals("DE89370400440532013000", account.getIban());
        assertEquals("COBADEFFXXX", account.getBic());
        assertEquals("JCodeForge GmbH", account.getAccountName());
    }

    @Test
    public void shouldAllowOnlyBic() {
        BankAccount account = BankAccount.builder()
                .bic("COBADEFFXXX")
                .accountName("JCodeForge GmbH")
                .build();

        assertEquals("COBADEFFXXX", account.getBic());
        assertNull(account.getIban());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankIban() {
        BankAccount.builder()
                .iban("   ")
                .bic("COBADEFFXXX")
                .accountName("JCodeForge GmbH")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankBic() {
        BankAccount.builder()
                .iban("DE89370400440532013000")
                .bic("   ")
                .accountName("JCodeForge GmbH")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankAccountName() {
        BankAccount.builder()
                .iban("DE89370400440532013000")
                .bic("COBADEFFXXX")
                .accountName("   ")
                .build();
    }

    @Test
    public void shouldAllowMissingBic() {
        BankAccount account = BankAccount.builder()
                .iban("DE89370400440532013000")
                .accountName("JCodeForge GmbH")
                .build();

        assertNull(account.getBic());
    }

    @Test
    public void shouldAllowMissingAccountName() {
        BankAccount account = BankAccount.builder()
                .iban("DE89370400440532013000")
                .bic("COBADEFFXXX")
                .build();

        assertNull(account.getAccountName());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingIbanAndBic() {
        BankAccount.builder()
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectTooShortIban() {
        BankAccount.builder()
                .iban("DE123")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectTooLongIban() {
        BankAccount.builder()
                .iban("DE123456789012345678901234567890123")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectIbanWithInvalidCharacters() {
        BankAccount.builder()
                .iban("DE89-370400440532013000")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectInvalidBicLength() {
        BankAccount.builder()
                .bic("ABC123")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBicWithInvalidCharacters() {
        BankAccount.builder()
                .bic("COBA-DEFF")
                .build();
    }

    @Test
    public void shouldAllowOnlyIban() {
        BankAccount account = BankAccount.builder()
                .iban("DE89370400440532013000")
                .build();

        assertEquals("DE89370400440532013000", account.getIban());
        assertNull(account.getBic());
    }

    @Test
    public void shouldAllowEightCharacterBic() {
        BankAccount account = BankAccount.builder()
                .bic("DEUTDEFF")
                .build();

        assertEquals("DEUTDEFF", account.getBic());
    }

    @Test
    public void shouldAllowElevenCharacterBic() {
        BankAccount account = BankAccount.builder()
                .bic("COBADEFFXXX")
                .build();

        assertEquals("COBADEFFXXX", account.getBic());
    }
}