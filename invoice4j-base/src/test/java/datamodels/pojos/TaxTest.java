package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.CurrencyCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MonetaryAmount;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Tax;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.math.BigDecimal;
import org.junit.Test;

import static org.junit.Assert.*;

public class TaxTest {

    @Test
    public void shouldCreateTax() {
        Tax tax = Tax.builder()
                .categoryCode(TaxCategoryCode.STANDARD)
                .rate(new BigDecimal("19"))
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .build();

        assertEquals(TaxCategoryCode.STANDARD, tax.getCategoryCode());
        assertEquals(new BigDecimal("19"), tax.getRate());
        assertEquals(new BigDecimal("100.00"), tax.getTaxableAmount().getAmount());
        assertEquals(new BigDecimal("19.00"), tax.getTaxAmount().getAmount());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingCategoryCode() {
        Tax.builder()
                .rate(new BigDecimal("19"))
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingRate() {
        Tax.builder()
                .categoryCode(TaxCategoryCode.STANDARD)
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingTaxableAmount() {
        Tax.builder()
                .categoryCode(TaxCategoryCode.STANDARD)
                .rate(new BigDecimal("19"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingTaxAmount() {
        Tax.builder()
                .categoryCode(TaxCategoryCode.STANDARD)
                .rate(new BigDecimal("19"))
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativeRate() {
        Tax.builder()
                .categoryCode(TaxCategoryCode.STANDARD)
                .rate(new BigDecimal("-1"))
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectRateGreaterThan100() {
        Tax.builder()
                .categoryCode(TaxCategoryCode.STANDARD)
                .rate(new BigDecimal("101"))
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativeTaxableAmount() {
        Tax.builder()
                .categoryCode(TaxCategoryCode.STANDARD)
                .rate(new BigDecimal("19"))
                .taxableAmount(CalculationUtils.createEUMoney("-100.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectNegativeTaxAmount() {
        Tax.builder()
                .categoryCode(TaxCategoryCode.STANDARD)
                .rate(new BigDecimal("19"))
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("-19.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectDifferentCurrencies() {
        MonetaryAmount taxableAmount = MonetaryAmount.builder()
                .amount(new BigDecimal("100.00"))
                .currency(CurrencyCode.USD)
                .build();

        Tax.builder()
                .categoryCode(TaxCategoryCode.STANDARD)
                .rate(new BigDecimal("19"))
                .taxableAmount(taxableAmount)
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .build();
    }

    @Test
    public void shouldCreateTaxWithExemptionReason() {
        Tax tax = Tax.builder()
                .categoryCode(TaxCategoryCode.EXEMPT)
                .rate(BigDecimal.ZERO)
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("0.00"))
                .exemptionReason("Medical services")
                .build();

        assertEquals("Medical services", tax.getExemptionReason());
    }

    @Test
    public void shouldCreateTaxWithExemptionReasonCode() {
        Tax tax = Tax.builder()
                .categoryCode(TaxCategoryCode.EXEMPT)
                .rate(BigDecimal.ZERO)
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("0.00"))
                .exemptionReasonCode("VATEX-EU-132")
                .build();

        assertEquals("VATEX-EU-132", tax.getExemptionReasonCode());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankExemptionReason() {
        Tax.builder()
                .categoryCode(TaxCategoryCode.EXEMPT)
                .rate(BigDecimal.ZERO)
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("0.00"))
                .exemptionReason("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankExemptionReasonCode() {
        Tax.builder()
                .categoryCode(TaxCategoryCode.EXEMPT)
                .rate(BigDecimal.ZERO)
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("0.00"))
                .exemptionReasonCode("   ")
                .build();
    }

    @Test
    public void shouldAllowZeroVatRateForExemptCategory() {
        Tax tax = Tax.builder()
                .categoryCode(TaxCategoryCode.EXEMPT)
                .rate(BigDecimal.ZERO)
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("0.00"))
                .build();

        assertEquals(BigDecimal.ZERO, tax.getRate());
    }

    @Test
    public void shouldAllowNullVatRateForExemptCategory() {
        Tax tax = Tax.builder()
                .categoryCode(TaxCategoryCode.EXEMPT)
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("0.00"))
                .build();

        assertNull(tax.getRate());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingVatRateForStandardCategory() {
        Tax.builder()
                .categoryCode(TaxCategoryCode.STANDARD)
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectExemptionReasonForStandardCategory() {
        Tax.builder()
                .categoryCode(TaxCategoryCode.STANDARD)
                .rate(new BigDecimal("19"))
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .exemptionReason("Medical services")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectExemptionReasonCodeForStandardCategory() {
        Tax.builder()
                .categoryCode(TaxCategoryCode.STANDARD)
                .rate(new BigDecimal("19"))
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("19.00"))
                .exemptionReasonCode("VATEX")
                .build();
    }

    @Test
    public void shouldTrimExemptionReason() {
        Tax tax = Tax.builder()
                .categoryCode(TaxCategoryCode.EXEMPT)
                .rate(BigDecimal.ZERO)
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("0.00"))
                .exemptionReason("  Medical services  ")
                .build();

        assertEquals("Medical services", tax.getExemptionReason());
    }

    @Test
    public void shouldTrimExemptionReasonCode() {
        Tax tax = Tax.builder()
                .categoryCode(TaxCategoryCode.EXEMPT)
                .rate(BigDecimal.ZERO)
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("0.00"))
                .exemptionReasonCode("  VATEX  ")
                .build();

        assertEquals("VATEX", tax.getExemptionReasonCode());
    }

    @Test
    public void shouldAllowNullExemptionInformation() {
        Tax tax = Tax.builder()
                .categoryCode(TaxCategoryCode.EXEMPT)
                .rate(BigDecimal.ZERO)
                .taxableAmount(CalculationUtils.createEUMoney("100.00"))
                .taxAmount(CalculationUtils.createEUMoney("0.00"))
                .build();

        assertNull(tax.getExemptionReason());
        assertNull(tax.getExemptionReasonCode());
    }
}