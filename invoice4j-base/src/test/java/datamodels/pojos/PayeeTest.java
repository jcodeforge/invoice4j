package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.IdentifierScheme;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Payee;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import org.junit.Test;

import static org.junit.Assert.*;

public class PayeeTest {

    @Test
    public void shouldCreatePayee() {
        Payee payee = CalculationUtils.createPayee();

        assertEquals("JCodeForge GmbH", payee.getName());
        assertEquals("JCodeForge", payee.getTradingName());
        assertNotNull(payee.getAddress());
        assertNotNull(payee.getElectronicAddress());
        assertNotNull(payee.getContact());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingName() {
        Payee.builder()
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankName() {
        Payee.builder()
                .name("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankTradingName() {
        Payee.builder()
                .name("JCodeForge GmbH")
                .tradingName("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNullIdentifier() {
        Payee.builder()
                .name("JCodeForge GmbH")
                .addIdentifier(null);
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectDuplicateIdentifierScheme() {
        Payee.builder()
                .name("JCodeForge GmbH")
                .addIdentifier(
                        PartyIdentifier.builder()
                                .value("1234567890123")
                                .scheme(IdentifierScheme.GLN)
                                .build())
                .addIdentifier(
                        PartyIdentifier.builder()
                                .value("9876543210123")
                                .scheme(IdentifierScheme.GLN)
                                .build());
    }

    @Test
    public void shouldAllowMultipleIdentifierSchemes() {
        Payee payee = Payee.builder()
                .name("JCodeForge GmbH")
                .addIdentifier(
                        PartyIdentifier.builder()
                                .value("1234567890123")
                                .scheme(IdentifierScheme.GLN)
                                .build())
                .addIdentifier(
                        PartyIdentifier.builder()
                                .value("DE123456789")
                                .scheme(IdentifierScheme.VAT)
                                .build())
                .build();

        assertEquals(2, payee.getIdentifiers().size());
    }

    @Test
    public void shouldAllowPayeeWithoutOptionalFields() {
        Payee payee = Payee.builder()
                .name("JCodeForge GmbH")
                .build();

        assertNull(payee.getTradingName());
        assertNull(payee.getAddress());
        assertNull(payee.getElectronicAddress());
        assertNull(payee.getContact());
    }

    @Test
    public void shouldCreatePayeeWithAllFields() {
        Payee payee = Payee.builder()
                .name("JCodeForge GmbH")
                .tradingName("JCodeForge")
                .address(CalculationUtils.createAddress())
                .electronicAddress(CalculationUtils.createElectronicAddress())
                .contact(CalculationUtils.createContact())
                .addIdentifier(CalculationUtils.createPartyIdentifier())
                .build();

        assertEquals("JCodeForge GmbH", payee.getName());
        assertEquals("JCodeForge", payee.getTradingName());
        assertNotNull(payee.getAddress());
        assertNotNull(payee.getElectronicAddress());
        assertNotNull(payee.getContact());
        assertEquals(1, payee.getIdentifiers().size());
    }
}