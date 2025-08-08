package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product {
    private String productId;
    private String productName;
    private String categoryId;
    private String categoryName;
    private BigDecimal price;
    private BigDecimal costPrice;
    private int stockQuantity;
    private int minStockLevel;
    private String unit;
    private String brand;
    private String description;
    private String imageUrl;
    private String supplierId;
    private String status;

    public Product() {}

    public Product(String productId, String productName, String categoryId, BigDecimal price, int stockQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // Getters and Setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public BigDecimal getCostPrice() { return costPrice; }
    public void setCostPrice(BigDecimal costPrice) { this.costPrice = costPrice; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public int getMinStockLevel() { return minStockLevel; }
    public void setMinStockLevel(int minStockLevel) { this.minStockLevel = minStockLevel; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Helper methods
    public boolean isLowStock() {
        return stockQuantity <= minStockLevel;
    }

    public double getProfitMargin() {
        if (costPrice != null && price != null && costPrice.compareTo(BigDecimal.ZERO) > 0) {
            return price.subtract(costPrice).divide(costPrice, 4, RoundingMode.HALF_UP).doubleValue();
        }
        return 0.0;
    }
}
