package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.CountryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Address;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddressTest {

    @Test
    public void shouldCreateAddress() {
        Address address = CalculationUtils.createAddress();

        assertEquals("Main Street 1", address.getStreet());
        assertEquals("Building A", address.getAdditionalStreet());
        assertEquals("Berlin", address.getCity());
        assertEquals("10115", address.getPostcode());
        assertEquals("Wedding", address.getCountrySubdivision());
        assertEquals(CountryCode.DE, address.getCountryCode());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingStreet() {
        Address.builder()
                .city("Ber")
                .postcode("10115")
                .countryCode(CountryCode.DE)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankStreet() {
        Address.builder()
                .street("   ")
                .city("Berlin")
                .postcode("10115")
                .countryCode(CountryCode.DE)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingCity() {
        Address.builder()
                .street("Main Street 1")
                .postcode("10115")
                .countryCode(CountryCode.DE)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankCity() {
        Address.builder()
                .street("Main Street 1")
                .city("   ")
                .postcode("10115")
                .countryCode(CountryCode.DE)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingPostcode() {
        Address.builder()
                .street("Main Street 1")
                .city("Berlin")
                .countryCode(CountryCode.DE)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankPostcode() {
        Address.builder()
                .street("Main Street 1")
                .city("Berlin")
                .postcode("   ")
                .countryCode(CountryCode.DE)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingCountryCode() {
        Address.builder()
                .street("Main Street 1")
                .city("Berlin")
                .postcode("10115")
                .build();
    }

    @Test
    public void shouldAllowMissingAdditionalStreet() {
        Address address = Address.builder()
                .street("Main Street 1")
                .city("Berlin")
                .postcode("10115")
                .countryCode(CountryCode.DE)
                .build();

        assertNull(address.getAdditionalStreet());
    }

    @Test
    public void shouldAllowMissingCountrySubdivision() {
        Address address = Address.builder()
                .street("Main Street 1")
                .city("Berlin")
                .postcode("10115")
                .countryCode(CountryCode.DE)
                .build();

        assertNull(address.getCountrySubdivision());
    }
}