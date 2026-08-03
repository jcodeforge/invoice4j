package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Seller;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import org.junit.Test;

import static org.junit.Assert.*;

public class SellerTest {

    @Test
    public void shouldCreateSeller() {
        Seller seller = CalculationUtils.createSeller();

        assertEquals("JCodeForge GmbH", seller.getName());
        assertEquals("JCodeForge", seller.getTradingName());
        assertNotNull(seller.getAddress());
        assertNotNull(seller.getElectronicAddress());
        assertNotNull(seller.getContact());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingName() {
        Seller.builder()
                .address(CalculationUtils.createAddress())
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankName() {
        Seller.builder()
                .name("   ")
                .address(CalculationUtils.createAddress())
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingAddress() {
        Seller.builder()
                .name("JCodeForge GmbH")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankTaxRegistrationIdentifier() {
        Seller.builder()
                .name("JCodeForge GmbH")
                .address(CalculationUtils.createAddress())
                .taxRegistrationIdentifier("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankLegalRegistrationIdentifier() {
        Seller.builder()
                .name("JCodeForge GmbH")
                .address(CalculationUtils.createAddress())
                .legalRegistrationIdentifier("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankLegalInformation() {
        Seller.builder()
                .name("JCodeForge GmbH")
                .address(CalculationUtils.createAddress())
                .legalInformation("   ")
                .build();
    }
}