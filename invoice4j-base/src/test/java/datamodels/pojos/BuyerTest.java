package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.IdentifierScheme;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Buyer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.PartyIdentifier;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import org.junit.Test;

import static org.junit.Assert.*;

public class BuyerTest {

    @Test
    public void shouldCreateBuyer() {
        Buyer buyer = CalculationUtils.createBuyer();

        assertEquals("JCodeForge GmbH", buyer.getName());
        assertEquals("JCodeForge", buyer.getTradingName());
        assertNotNull(buyer.getAddress());
        assertNotNull(buyer.getElectronicAddress());
        assertNotNull(buyer.getContact());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingName() {
        Buyer.builder()
                .address(CalculationUtils.createAddress())
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankName() {
        Buyer.builder()
                .name("   ")
                .address(CalculationUtils.createAddress())
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankTradingName() {
        Buyer.builder()
                .name("Customer GmbH")
                .address(CalculationUtils.createAddress())
                .tradingName("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankLegalRegistrationIdentifier() {
        Buyer.builder()
                .name("Customer GmbH")
                .address(CalculationUtils.createAddress())
                .legalRegistrationIdentifier("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankBuyerReference() {
        Buyer.builder()
                .name("Customer GmbH")
                .address(CalculationUtils.createAddress())
                .buyerReference("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNullIdentifier() {
        Buyer.builder()
                .name("Customer GmbH")
                .address(CalculationUtils.createAddress())
                .addIdentifier(null);
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectDuplicateIdentifierScheme() {
        Buyer.builder()
                .name("Customer GmbH")
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

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNullVatIdentifier() {
        Buyer.builder()
                .name("Customer GmbH")
                .address(CalculationUtils.createAddress())
                .vatIdentifier(null);
    }

    @Test
    public void shouldAllowVatIdentifier() {
        Buyer buyer = Buyer.builder()
                .name("Customer GmbH")
                .address(CalculationUtils.createAddress())
                .vatIdentifier(CalculationUtils.createTaxIdentifier())
                .build();

        assertNotNull(buyer.getVatIdentifier());
    }

    @Test
    public void shouldAllowMultipleIdentifiersWithDifferentSchemes() {
        Buyer buyer = Buyer.builder()
                .name("Customer GmbH")
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

        assertEquals(2, buyer.getIdentifiers().size());
    }

    @Test
    public void shouldAllowBuyerWithoutOptionalFields() {
        Buyer buyer = Buyer.builder()
                .name("Customer GmbH")
                .address(CalculationUtils.createAddress())
                .build();

        assertNull(buyer.getTradingName());
        assertNull(buyer.getElectronicAddress());
        assertNull(buyer.getContact());
        assertNull(buyer.getVatIdentifier());
        assertNull(buyer.getBuyerReference());
    }

    @Test
    public void shouldCreateBuyerWithAllFields() {
        Buyer buyer = Buyer.builder()
                .name("Customer GmbH")
                .tradingName("Customer")
                .address(CalculationUtils.createAddress())
                .electronicAddress(CalculationUtils.createElectronicAddress())
                .contact(CalculationUtils.createContact())
                .vatIdentifier(CalculationUtils.createTaxIdentifier())
                .buyerReference("PO-12345")
                .legalRegistrationIdentifier("HRB 12345")
                .addIdentifier(CalculationUtils.createPartyIdentifier())
                .build();

        assertEquals("Customer GmbH", buyer.getName());
        assertEquals("Customer", buyer.getTradingName());
        assertEquals(1, buyer.getIdentifiers().size());
        assertNotNull(buyer.getVatIdentifier());
        assertEquals("PO-12345", buyer.getBuyerReference());
    }
}