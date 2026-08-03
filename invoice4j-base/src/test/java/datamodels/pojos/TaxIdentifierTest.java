package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxIdentifierType;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.TaxIdentifier;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import org.junit.Test;

import static org.junit.Assert.*;

public class TaxIdentifierTest {

    @Test
    public void shouldCreateTaxIdentifier() {
        TaxIdentifier identifier = CalculationUtils.createTaxIdentifier();

        assertEquals("DE123456789", identifier.getValue());
        assertEquals(TaxIdentifierType.VAT_REGISTRATION_NUMBER, identifier.getType());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingValue() {
        TaxIdentifier.builder()
                .type(TaxIdentifierType.VAT_REGISTRATION_NUMBER)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankValue() {
        TaxIdentifier.builder()
                .value("   ")
                .type(TaxIdentifierType.VAT_REGISTRATION_NUMBER)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingType() {
        TaxIdentifier.builder()
                .value("DE123456789")
                .build();
    }

    @Test
    public void shouldAllowVatIdentifier() {
        TaxIdentifier identifier = TaxIdentifier.builder()
                .value("DE123456789")
                .type(TaxIdentifierType.VAT_REGISTRATION_NUMBER)
                .build();

        assertEquals("DE123456789", identifier.getValue());
        assertEquals(TaxIdentifierType.VAT_REGISTRATION_NUMBER, identifier.getType());
    }

    @Test
    public void shouldAllowTaxRegistrationIdentifier() {
        TaxIdentifier identifier = TaxIdentifier.builder()
                .value("123/456/789")
                .type(TaxIdentifierType.TAX_IDENTIFICATION_NUMBER)
                .build();

        assertEquals("123/456/789", identifier.getValue());
        assertEquals(TaxIdentifierType.TAX_IDENTIFICATION_NUMBER, identifier.getType());
    }
}