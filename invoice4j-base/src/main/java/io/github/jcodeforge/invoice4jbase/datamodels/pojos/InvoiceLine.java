package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxCategoryCode;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.UnitCode;
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
    private String sellerItemIdentifier;

    /**
     * BT-131
     * Buyer assigned item identifier.
     */
    private String buyerItemIdentifier;

    /**
     * BT-157
     * Standard item classification identifier.
     */
    private String standardItemIdentifier;

    /**
     * BT-153
     * Invoiced quantity.
     */
    private BigDecimal quantity;

    /**
     * BT-130
     * Unit of measure.
     */
    private UnitCode unitCode;

    /**
     * BT-146
     * Item net price.
     */
    private MonetaryAmount unitPrice;

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
     * Line net amount.
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

    public String getSellerItemIdentifier() {
        return sellerItemIdentifier;
    }

    public String getBuyerItemIdentifier() {
        return buyerItemIdentifier;
    }

    public String getStandardItemIdentifier() {
        return standardItemIdentifier;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public UnitCode getUnitCode() {
        return unitCode;
    }

    public MonetaryAmount getUnitPrice() {
        return unitPrice;
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
        return allowanceCharges;
    }

    public List<ItemProperty> getProperties() {
        return properties;
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

        public Builder sellerItemIdentifier(String sellerItemIdentifier) {
            line.sellerItemIdentifier = sellerItemIdentifier;
            return this;
        }

        public Builder buyerItemIdentifier(String buyerItemIdentifier) {
            line.buyerItemIdentifier = buyerItemIdentifier;
            return this;
        }

        public Builder standardItemIdentifier(String standardItemIdentifier) {
            line.standardItemIdentifier = standardItemIdentifier;
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

        public Builder unitPrice(MonetaryAmount unitPrice) {
            line.unitPrice = unitPrice;
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

        public Builder allowanceCharges(List<AllowanceCharge> allowanceCharges) {
            line.allowanceCharges = new ArrayList<>(allowanceCharges);
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
            return line;
        }
    }
}
