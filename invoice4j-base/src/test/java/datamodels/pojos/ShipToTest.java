package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.IdentifierScheme;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.ShipTo;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShipToTest {

    @Test
    public void shouldCreateShipTo() {
        ShipTo shipTo = CalculationUtils.createShipTo();

        assertEquals("Customer Warehouse", shipTo.getName());
        assertEquals("Warehouse", shipTo.getTradingName());
        assertNotNull(shipTo.getAddress());
        assertNotNull(shipTo.getElectronicAddress());
        assertNotNull(shipTo.getContact());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingName() {
        ShipTo.builder()
                .address(CalculationUtils.createAddress())
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankName() {
        ShipTo.builder()
                .name("   ")
                .address(CalculationUtils.createAddress())
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingAddress() {
        ShipTo.builder()
                .name("Customer Warehouse")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankTradingName() {
        ShipTo.builder()
                .name("Customer Warehouse")
                .address(CalculationUtils.createAddress())
                .tradingName("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNullIdentifier() {
        ShipTo.builder()
                .name("Customer Warehouse")
                .address(CalculationUtils.createAddress())
                .addIdentifier(null);
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectDuplicateIdentifierScheme() {
        ShipTo.builder()
                .name("Customer Warehouse")
                .address(CalculationUtils.createAddress())
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
        ShipTo shipTo = ShipTo.builder()
                .name("Customer Warehouse")
                .address(CalculationUtils.createAddress())
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

        assertEquals(2, shipTo.getIdentifiers().size());
    }

    @Test
    public void shouldAllowShipToWithoutOptionalFields() {
        ShipTo shipTo = ShipTo.builder()
                .name("Customer Warehouse")
                .address(CalculationUtils.createAddress())
                .build();

        assertNull(shipTo.getTradingName());
        assertNull(shipTo.getElectronicAddress());
        assertNull(shipTo.getContact());
    }

    @Test
    public void shouldCreateShipToWithAllFields() {
        ShipTo shipTo = ShipTo.builder()
                .name("Customer Warehouse")
                .tradingName("Warehouse")
                .address(CalculationUtils.createAddress())
                .electronicAddress(CalculationUtils.createElectronicAddress())
                .contact(CalculationUtils.createContact())
                .addIdentifier(CalculationUtils.createPartyIdentifier())
                .build();

        assertEquals("Customer Warehouse", shipTo.getName());
        assertEquals("Warehouse", shipTo.getTradingName());
        assertNotNull(shipTo.getAddress());
        assertNotNull(shipTo.getElectronicAddress());
        assertNotNull(shipTo.getContact());
        assertEquals(1, shipTo.getIdentifiers().size());
    }
}