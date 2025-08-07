package model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String categoryId;
    private String categoryName;
    private String description;
    private String parentCategoryId;
    private int displayOrder;
    private int productCount;
    private List<Category> subCategories;

    public Category() {
        this.subCategories = new ArrayList<>();
    }

    public Category(String categoryId, String categoryName, String description) {
        this();
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
    }

    public Category(String categoryId, String categoryName, String description, String parentCategoryId) {
        this(categoryId, categoryName, description);
        this.parentCategoryId = parentCategoryId;
    }

    // Getters and Setters
    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getParentCategoryId() { return parentCategoryId; }
    public void setParentCategoryId(String parentCategoryId) { this.parentCategoryId = parentCategoryId; }

    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }

    public int getProductCount() { return productCount; }
    public void setProductCount(int productCount) { this.productCount = productCount; }

    public List<Category> getSubCategories() { return subCategories; }
    public void setSubCategories(List<Category> subCategories) { this.subCategories = subCategories; }

    // Helper methods
    public void addSubCategory(Category subCategory) {
        this.subCategories.add(subCategory);
    }

    public boolean hasSubCategories() {
        return !subCategories.isEmpty();
    }

    public boolean isRootCategory() {
        return parentCategoryId == null;
    }
}
