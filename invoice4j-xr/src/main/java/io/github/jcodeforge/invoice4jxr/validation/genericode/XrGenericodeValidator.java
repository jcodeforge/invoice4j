package io.github.jcodeforge.invoice4jxr.validation.genericode;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.InvoiceLine;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Party;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Tax;

public final class XrGenericodeValidator {

    private final GenericodeValidator currencyValidator;
    private final GenericodeValidator countryValidator;
    private final GenericodeValidator documentTypeValidator;
    private final GenericodeValidator taxCategoryValidator;
    private final GenericodeValidator paymentMeansValidator;
    private final GenericodeValidator unitCodeRec20Validator;
    private final GenericodeValidator unitCodeRec21Validator;

    public XrGenericodeValidator() {
        this.currencyValidator =
                new GenericodeValidator(GenericodeRegistry.fromResource("/genericode/currency-codes_3.gc"));

        this.countryValidator =
                new GenericodeValidator(GenericodeRegistry.fromResource("/genericode/country-codes_8.gc"));

        this.documentTypeValidator =
                new GenericodeValidator(GenericodeRegistry.fromResource("/genericode/untdid.1001_4.gc"));

        this.taxCategoryValidator =
                new GenericodeValidator(GenericodeRegistry.fromResource("/genericode/untdid.5305_3.gc"));

        this.paymentMeansValidator =
                new GenericodeValidator(GenericodeRegistry.fromResource("/genericode/untdid.4461_3.gc"));

        this.unitCodeRec20Validator =
                new GenericodeValidator(GenericodeRegistry.fromResource("/genericode/rec20_3.gc"));

        this.unitCodeRec21Validator =
                new GenericodeValidator(GenericodeRegistry.fromResource("/genericode/rec21_3.gc"));
    }

    public void validate(Invoice invoice) {
        validateCurrency(invoice);
        validateDocumentType(invoice);
        validateCountries(invoice);
        validateTaxCategories(invoice);
        validatePaymentMeans(invoice);
        validateUnits(invoice);
    }

    private void validateCurrency(Invoice invoice) {
        if (invoice.getCurrency() == null) {
            return;
        }

        String code = invoice.getCurrency().getCode();
        if (!currencyValidator.isValid(code)) {
            throwIllegalArgumentException(code);
        }
    }

    private void validateCountries(Invoice invoice) {
        validateCountry(invoice.getSeller());
        validateCountry(invoice.getBuyer());
        validateCountry(invoice.getPayee());
    }

    private void validateCountry(Party party) {
        if (party == null || party.getAddress() == null || party.getAddress().getCountryCode() == null
                || party.getAddress().getCountryCode().getCode() == null) {
            return;
        }

        String code = party.getAddress().getCountryCode().getCode();
        if (!countryValidator.isValid(code)) {
            throwIllegalArgumentException(code);
        }
    }

    private void validateDocumentType(Invoice invoice) {
        if (invoice.getDocumentTypeCode() == null) {
            return;
        }

        String code = invoice.getDocumentTypeCode().getCode();
        if (!documentTypeValidator.isValid(code)) {
            throwIllegalArgumentException(code);
        }
    }

    private void validateTaxCategories(Invoice invoice) {
        if (invoice.getTaxes() == null) {
            return;
        }

        for (Tax tax : invoice.getTaxes()) {
            if (tax.getCategoryCode() != null) {
                String code = tax.getCategoryCode().getCode();
                if (!taxCategoryValidator.isValid(code)) {
                    throwIllegalArgumentException(code);
                }
            }
        }
    }

    private void validatePaymentMeans(Invoice invoice) {
        if (invoice.getPayment() == null) {
            return;
        }

        if (invoice.getPayment().getMeansCode() != null) {
            String code = invoice.getPayment().getMeansCode().getCode();
            if (!paymentMeansValidator.isValid(code)) {
                throwIllegalArgumentException(code);
            }
        }
    }

    private void validateUnits(Invoice invoice) {
        if (invoice.getLines() == null) {
            return;
        }

        for (InvoiceLine line : invoice.getLines()) {
            if (line.getUnitCode() == null) {
                continue;
            }

            String code = line.getUnitCode().getCode();
            if (!unitCodeRec20Validator.isValid(code) && !unitCodeRec21Validator.isValid(code)) {
                throwIllegalArgumentException(code);
            }
        }
    }

    private void throwIllegalArgumentException(String code) {
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}