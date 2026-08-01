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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjectIdentifier() {
        return objectIdentifier;
    }

    public void setObjectIdentifier(String objectIdentifier) {
        this.objectIdentifier = objectIdentifier;
    }

    public String getBuyerAccountingReference() {
        return buyerAccountingReference;
    }

    public void setBuyerAccountingReference(String buyerAccountingReference) {
        this.buyerAccountingReference = buyerAccountingReference;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSellerItemIdentifier() {
        return sellerItemIdentifier;
    }

    public void setSellerItemIdentifier(String sellerItemIdentifier) {
        this.sellerItemIdentifier = sellerItemIdentifier;
    }

    public String getBuyerItemIdentifier() {
        return buyerItemIdentifier;
    }

    public void setBuyerItemIdentifier(String buyerItemIdentifier) {
        this.buyerItemIdentifier = buyerItemIdentifier;
    }

    public String getStandardItemIdentifier() {
        return standardItemIdentifier;
    }

    public void setStandardItemIdentifier(String standardItemIdentifier) {
        this.standardItemIdentifier = standardItemIdentifier;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public UnitCode getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(UnitCode unitCode) {
        this.unitCode = unitCode;
    }

    public MonetaryAmount getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(MonetaryAmount unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(BigDecimal priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public BigDecimal getPriceDiscountPercentage() {
        return priceDiscountPercentage;
    }

    public void setPriceDiscountPercentage(BigDecimal priceDiscountPercentage) {
        this.priceDiscountPercentage = priceDiscountPercentage;
    }

    public BigDecimal getBaseQuantity() {
        return baseQuantity;
    }

    public void setBaseQuantity(BigDecimal baseQuantity) {
        this.baseQuantity = baseQuantity;
    }

    public MonetaryAmount getLineExtensionAmount() {
        return lineExtensionAmount;
    }

    public void setLineExtensionAmount(MonetaryAmount lineExtensionAmount) {
        this.lineExtensionAmount = lineExtensionAmount;
    }

    public TaxCategoryCode getTaxCategory() {
        return taxCategory;
    }

    public void setTaxCategory(TaxCategoryCode taxCategory) {
        this.taxCategory = taxCategory;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public List<AllowanceCharge> getAllowanceCharges() {
        return allowanceCharges;
    }

    public void addAllowanceCharge(AllowanceCharge allowanceCharge) {
        this.allowanceCharges.add(allowanceCharge);
    }

    public List<ItemProperty> getProperties() {
        return properties;
    }

    public void addProperty(ItemProperty property) {
        this.properties.add(property);
    }
}
