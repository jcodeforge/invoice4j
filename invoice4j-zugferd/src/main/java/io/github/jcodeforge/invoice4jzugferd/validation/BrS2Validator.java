package io.github.jcodeforge.invoice4jzugferd.validation;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Seller;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;

public final class BrS2Validator implements InvoiceBusinessRule {

    /**
     * BR-S-2
     *
     * A standard-rated invoice line requires BT-31, BT-32
     * or BT-63.
     */
    @Override
    public void validate(Invoice invoice) {
        if (!containsStandardRatedVat(invoice)) {
            return;
        }

        Seller seller = invoice.getSeller();

        boolean hasVatIdentifier = seller.getVatIdentifier() != null;

        boolean hasTaxRegistrationIdentifier = seller.getTaxRegistrationIdentifier() != null
                && !seller.getTaxRegistrationIdentifier().isBlank();

        if (!hasVatIdentifier && !hasTaxRegistrationIdentifier) {
            throw new InvoiceValidationException(
                    "BR-S-2: Seller VAT identifier (BT-31) "
                            + "or seller tax registration identifier (BT-32) "
                            + "must be present for standard-rated VAT."
            );
        }
    }

    private boolean containsStandardRatedVat(Invoice invoice) {
        return invoice.getLines().stream().anyMatch(line ->
                line.getTaxCategory() == TaxCategoryCode.STANDARD);
    }
}
