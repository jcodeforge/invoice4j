package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.IdentifierScheme;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import org.junit.Test;

import static org.junit.Assert.*;

public class PartyIdentifierTest {

    @Test
    public void shouldCreatePartyIdentifier() {
        PartyIdentifier identifier = CalculationUtils.createPartyIdentifier();

        assertEquals("1234567890123", identifier.getValue());
        assertEquals(IdentifierScheme.GLN, identifier.getScheme());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingValue() {
        PartyIdentifier.builder()
                .scheme(IdentifierScheme.GLN)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankValue() {
        PartyIdentifier.builder()
                .value("   ")
                .scheme(IdentifierScheme.GLN)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingScheme() {
        PartyIdentifier.builder()
                .value("1234567890123")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectTooLongValue() {
        PartyIdentifier.builder()
                .value("A".repeat(257))
                .scheme(IdentifierScheme.GLN)
                .build();
    }

    @Test
    public void shouldTrimIdentifierValue() {
        PartyIdentifier identifier = PartyIdentifier.builder()
                .value(" 1234567890123 ")
                .scheme(IdentifierScheme.GLN)
                .build();

        assertEquals("1234567890123", identifier.getValue());
    }

    @Test
    public void shouldAllowMaximumLengthValue() {
        PartyIdentifier identifier = PartyIdentifier.builder()
                .value("A".repeat(256))
                .scheme(IdentifierScheme.GLN)
                .build();

        assertEquals(256, identifier.getValue().length());
    }

    @Test
    public void shouldAllowDifferentIdentifierSchemes() {
        PartyIdentifier identifier = PartyIdentifier.builder()
                .value("DE123456789")
                .scheme(IdentifierScheme.VAT)
                .build();

        assertEquals(IdentifierScheme.VAT, identifier.getScheme());
    }
}