package io.github.jcodeforge.invoice4jzugferd.zugferd.validation;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Seller;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.util.Objects;

public final class BrCo26Validator implements InvoiceBusinessRule {

    /**
     * BR-CO-26
     *
     * In order for the buyer to automatically identify a supplier,
     * at least one of the following seller identifiers must be present:
     *
     * BT-29 Seller identifier
     * BT-30 Seller legal registration identifier
     * BT-31 Seller VAT identifier
     *
     * @param invoice invoice to validate
     * @throws NullPointerException if invoice is null
     * @throws InvoiceValidationException if BR-CO-26 is violated
     */
    @Override
    public void validate(Invoice invoice) {
        Objects.requireNonNull(invoice, "invoice must not be null");

        Seller seller = invoice.getSeller();

        if (seller == null) {
            throw new InvoiceValidationException("BR-CO-26: Seller must be present.");
        }

        boolean hasSellerIdentifier = seller.getIdentifiers() != null && !seller.getIdentifiers().isEmpty();

        boolean hasLegalRegistrationIdentifier = seller.getLegalRegistrationIdentifier() != null
                && !seller.getLegalRegistrationIdentifier().isBlank();

        boolean hasVatIdentifier = seller.getVatIdentifier() != null;

        if (!hasSellerIdentifier && !hasLegalRegistrationIdentifier && !hasVatIdentifier) {
            throw new InvoiceValidationException(
                    "BR-CO-26: At least one of BT-29 Seller identifier, "
                            + "BT-30 Seller legal registration identifier, "
                            + "or BT-31 Seller VAT identifier must be present."
            );
        }
    }
}
