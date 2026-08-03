package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.IdentifierScheme;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.ElectronicAddress;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ElectronicAddressTest {

    @Test
    public void shouldCreateElectronicAddress() {
        ElectronicAddress address = CalculationUtils.createElectronicAddress();

        assertEquals("info@jcodeforge.io", address.getValue());
        assertEquals(IdentifierScheme.EMAIL, address.getScheme());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingValue() {
        ElectronicAddress.builder()
                .scheme(IdentifierScheme.EMAIL)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankValue() {
        ElectronicAddress.builder()
                .value("   ")
                .scheme(IdentifierScheme.EMAIL)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingScheme() {
        ElectronicAddress.builder()
                .value("info@jcodeforge.io")
                .build();
    }

    @Test
    public void shouldAllowEmailAddress() {
        ElectronicAddress address = ElectronicAddress.builder()
                .value("info@jcodeforge.io")
                .scheme(IdentifierScheme.EMAIL)
                .build();

        assertEquals("info@jcodeforge.io", address.getValue());
        assertEquals(IdentifierScheme.EMAIL, address.getScheme());
    }

    @Test
    public void shouldAllowVatIdentifier() {
        ElectronicAddress address = ElectronicAddress.builder()
                .value("123456789")
                .scheme(IdentifierScheme.VAT)
                .build();

        assertEquals("123456789", address.getValue());
        assertEquals(IdentifierScheme.VAT, address.getScheme());
    }

    @Test
    public void shouldAllowGlnIdentifier() {
        ElectronicAddress address = ElectronicAddress.builder()
                .value("1234567890123")
                .scheme(IdentifierScheme.GLN)
                .build();

        assertEquals("1234567890123", address.getValue());
        assertEquals(IdentifierScheme.GLN, address.getScheme());
    }
}