package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private String customerId;
    private String customerName;
    private String employeeId;
    private String employeeName;
    private LocalDateTime orderDate;
    private String status;
    private String paymentMethod;
    private String paymentStatus;
    private List<OrderItem> items;
    private BigDecimal discountAmount;
    private String notes;

    public Order() {
        this.items = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
        this.discountAmount = BigDecimal.ZERO;
    }

    public Order(String orderId, String customerId, String employeeId) {
        this();
        this.orderId = orderId;
        this.customerId = customerId;
        this.employeeId = employeeId;
    }

    // Getters and Setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public BigDecimal getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    // Helper methods
    public void addOrderItem(OrderItem item) {
        this.items.add(item);
    }

    public BigDecimal getSubtotal() {
        return items.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getFinalAmount() {
        return getSubtotal().subtract(discountAmount != null ? discountAmount : BigDecimal.ZERO);
    }

    public boolean isCompleted() {
        return "COMPLETED".equals(status);
    }

    // Inner class for Order Items
    public static class OrderItem {
        private String productId;
        private String productName;
        private String categoryId;
        private BigDecimal unitPrice;
        private int quantity;

        public OrderItem() {}

        public OrderItem(String productId, String productName, BigDecimal unitPrice, int quantity) {
            this.productId = productId;
            this.productName = productName;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
        }

        // Getters and Setters
        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }

        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }

        public String getCategoryId() { return categoryId; }
        public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

        public BigDecimal getUnitPrice() { return unitPrice; }
        public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public BigDecimal getTotalPrice() {
            return unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
    }
}
