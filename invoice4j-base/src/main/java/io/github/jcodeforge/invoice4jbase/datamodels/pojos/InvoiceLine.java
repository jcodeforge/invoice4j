package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.UnitCode;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * BG-25
 *
 * Invoice line.
 */
public class InvoiceLine {

    /**
     * BT-126
     * Invoice line identifier.
     */
    private String id;

    /**
     * BT-127
     * Line object identifier.
     */
    private String objectIdentifier;

    /**
     * BT-155
     * Buyer accounting reference at line level.
     */
    private String buyerAccountingReference;

    /**
     * BT-128
     * Item name.
     */
    private String itemName;

    /**
     * BT-129
     * Item description.
     */
    private String description;

    /**
     * BT-130
     * Seller assigned item identifier.
     */
    private String sellerAssignedIdentifier;

    /**
     * BT-131
     * Buyer assigned item identifier.
     */
    private String buyerAssignedIdentifier;

    /**
     * BT-158
     * Standard item classification identifier.
     */
    private String itemClassificationIdentifier;

    /**
     * BT-153
     * Invoiced quantity.
     */
    private BigDecimal quantity;

    private UnitCode unitCode;

    /**
     * BT-146
     * Item net price.
     */
    private MonetaryAmount netPrice;

    /**
     * BT-147
     * Price discount amount.
     */
    private BigDecimal priceDiscount;

    /**
     * BT-148
     * Price discount percentage.
     */
    private BigDecimal priceDiscountPercentage;

    /**
     * BT-149
     * Base quantity for price calculation.
     */
    private BigDecimal baseQuantity;

    /**
     * BT-131
     * Invoice line net amount.
     */
    private MonetaryAmount lineExtensionAmount;

    /**
     * VAT category.
     */
    private TaxCategoryCode taxCategory;

    /**
     * VAT rate.
     */
    private BigDecimal taxRate;

    /**
     * Line level allowances and charges.
     */
    private List<AllowanceCharge> allowanceCharges = new ArrayList<>();

    /**
     * Additional item properties.
     */
    private List<ItemProperty> properties = new ArrayList<>();

    private InvoiceLine() {
    }

    public String getId() {
        return id;
    }

    public String getObjectIdentifier() {
        return objectIdentifier;
    }

    public String getBuyerAccountingReference() {
        return buyerAccountingReference;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public String getSellerAssignedIdentifier() {
        return sellerAssignedIdentifier;
    }

    public String getBuyerAssignedIdentifier() {
        return buyerAssignedIdentifier;
    }

    public String getItemClassificationIdentifier() {
        return itemClassificationIdentifier;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public UnitCode getUnitCode() {
        return unitCode;
    }

    public MonetaryAmount getNetPrice() {
        return netPrice;
    }

    public BigDecimal getPriceDiscount() {
        return priceDiscount;
    }

    public BigDecimal getPriceDiscountPercentage() {
        return priceDiscountPercentage;
    }

    public BigDecimal getBaseQuantity() {
        return baseQuantity;
    }

    public MonetaryAmount getLineExtensionAmount() {
        return lineExtensionAmount;
    }

    public TaxCategoryCode getTaxCategory() {
        return taxCategory;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public List<AllowanceCharge> getAllowanceCharges() {
        return List.copyOf(allowanceCharges);
    }

    public List<ItemProperty> getProperties() {
        return List.copyOf(properties);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final InvoiceLine line;

        private Builder() {
            this.line = new InvoiceLine();
        }

        public Builder id(String id) {
            line.id = id;
            return this;
        }

        public Builder objectIdentifier(String objectIdentifier) {
            line.objectIdentifier = objectIdentifier;
            return this;
        }

        public Builder buyerAccountingReference(String buyerAccountingReference) {
            line.buyerAccountingReference = buyerAccountingReference;
            return this;
        }

        public Builder itemName(String itemName) {
            line.itemName = itemName;
            return this;
        }

        public Builder description(String description) {
            line.description = description;
            return this;
        }

        public Builder sellerAssignedIdentifier(String sellerItemIdentifier) {
            line.sellerAssignedIdentifier = sellerItemIdentifier;
            return this;
        }

        public Builder buyerAssignedIdentifier(String buyerItemIdentifier) {
            line.buyerAssignedIdentifier = buyerItemIdentifier;
            return this;
        }

        public Builder itemClassificationIdentifier(String identifier) {
            line.itemClassificationIdentifier = identifier;
            return this;
        }

        public Builder quantity(BigDecimal quantity) {
            line.quantity = quantity;
            return this;
        }

        public Builder unitCode(UnitCode unitCode) {
            line.unitCode = unitCode;
            return this;
        }

        public Builder netPrice(MonetaryAmount unitPrice) {
            line.netPrice = unitPrice;
            return this;
        }

        public Builder priceDiscount(BigDecimal priceDiscount) {
            line.priceDiscount = priceDiscount;
            return this;
        }

        public Builder priceDiscountPercentage(BigDecimal priceDiscountPercentage) {
            line.priceDiscountPercentage = priceDiscountPercentage;
            return this;
        }

        public Builder baseQuantity(BigDecimal baseQuantity) {
            line.baseQuantity = baseQuantity;
            return this;
        }

        public Builder lineExtensionAmount(MonetaryAmount amount) {
            line.lineExtensionAmount = amount;
            return this;
        }

        public Builder taxCategory(TaxCategoryCode taxCategory) {
            line.taxCategory = taxCategory;
            return this;
        }

        public Builder taxRate(BigDecimal taxRate) {
            line.taxRate = taxRate;
            return this;
        }

        public Builder addAllowanceCharge(AllowanceCharge allowanceCharge) {
            line.allowanceCharges.add(allowanceCharge);
            return this;
        }

        public Builder allowanceCharges(List<AllowanceCharge> charges) {
            line.allowanceCharges = new ArrayList<>(charges);
            return this;
        }

        public Builder addProperty(ItemProperty property) {
            line.properties.add(property);
            return this;
        }

        public Builder properties(List<ItemProperty> properties) {
            line.properties = new ArrayList<>(properties);
            return this;
        }

        public InvoiceLine build() {
            if (line.id == null || line.id.isBlank()) {
                throw new InvoiceValidationException("BT-126 Invoice line identifier is required.");
            }
            if (line.itemName == null || line.itemName.isBlank()) {
                throw new InvoiceValidationException("BT-128 Item name is required.");
            }
            if (line.quantity == null) {
                throw new InvoiceValidationException("BT-153 Invoiced quantity is required.");
            }
            if (line.unitCode == null) {
                throw new InvoiceValidationException("BT-130 Unit code is required.");
            }
            if (line.netPrice == null) {
                throw new InvoiceValidationException("BT-146 Item net price is required.");
            }
            if (line.netPrice.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                throw new InvoiceValidationException("BT-146 Item net price must not be negative.");
            }
            if (line.lineExtensionAmount != null && line.lineExtensionAmount.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                throw new InvoiceValidationException("Line extension amount must not be negative.");
            }
            if (line.taxCategory == null) {
                throw new InvoiceValidationException("Invoice line VAT category is required.");
            }
            if (line.taxRate == null) {
                throw new InvoiceValidationException("Invoice line VAT rate is required.");
            }
            if (line.taxRate.compareTo(BigDecimal.ZERO) < 0) {
                throw new InvoiceValidationException("VAT rate must not be negative.");
            }
            if (line.taxRate.compareTo(new BigDecimal("100")) > 0) {
                throw new InvoiceValidationException("VAT rate must not exceed 100."
                );
            }
            if (line.objectIdentifier != null && line.objectIdentifier.isBlank()) {
                throw new InvoiceValidationException("BT-127 Object identifier must not be blank.");
            }
            if (line.buyerAccountingReference != null && line.buyerAccountingReference.isBlank()) {
                throw new InvoiceValidationException("BT-155 Buyer accounting reference must not be blank.");
            }
            if (line.description != null && line.description.isBlank()) {
                throw new InvoiceValidationException("BT-129 Item description must not be blank.");
            }
            if (line.sellerAssignedIdentifier != null && line.sellerAssignedIdentifier.isBlank()) {
                throw new InvoiceValidationException("Seller item identifier must not be blank.");
            }
            if (line.buyerAssignedIdentifier != null && line.buyerAssignedIdentifier.isBlank()) {
                throw new InvoiceValidationException("Buyer item identifier must not be blank.");
            }
            if (line.itemClassificationIdentifier != null && line.itemClassificationIdentifier.isBlank()) {
                throw new InvoiceValidationException("Standard item identifier must not be blank.");
            }
            if (line.priceDiscount != null && line.priceDiscount.compareTo(BigDecimal.ZERO) < 0) {
                throw new InvoiceValidationException("Price discount must not be negative.");
            }
            if (line.priceDiscountPercentage != null) {
                if (line.priceDiscountPercentage.compareTo(BigDecimal.ZERO) < 0) {
                    throw new InvoiceValidationException("Price discount percentage must not be negative.");
                }
                if (line.priceDiscountPercentage.compareTo(new BigDecimal("100")) > 0) {
                    throw new InvoiceValidationException("Price discount percentage must not exceed 100.");
                }
            }
            if (line.baseQuantity != null && line.baseQuantity.compareTo(BigDecimal.ZERO) <= 0) {
                throw new InvoiceValidationException("BT-149 Base quantity must be greater than zero.");
            }
            if (line.priceDiscountPercentage != null && line.baseQuantity == null) {
                throw new InvoiceValidationException(
                        "BT-149 Base quantity is required when a discount percentage is specified.");
            }
            if (line.lineExtensionAmount != null && line.lineExtensionAmount.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                throw new InvoiceValidationException("Line extension amount must not be negative.");
            }
            if (line.quantity.compareTo(BigDecimal.ZERO) <= 0) {
                throw new InvoiceValidationException("BT-153 Quantity must be greater than zero."
                );
            }

            for (AllowanceCharge allowanceCharge : line.allowanceCharges) {
                if (allowanceCharge == null) {
                    throw new InvoiceValidationException("Invoice line contains a null allowance/charge.");
                }
            }

            for (ItemProperty property : line.properties) {
                if (property == null) {
                    throw new InvoiceValidationException("Invoice line contains a null item property.");
                }
            }

            return line;
        }
    }
}
