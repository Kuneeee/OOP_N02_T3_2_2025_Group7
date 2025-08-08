package model;

import java.time.LocalDate;

public class Customer {
    private String customerId;
    private String customerName;
    private String phoneNumber;
    private String email;
    private String address;
    private String customerType;
    private double totalPurchased;
    private int totalOrders;
    private Double totalSpent;
    private int loyaltyPoints; // Add loyalty points field
    private LocalDate joinDate;

    public Customer() {
        this.customerType = "Regular";
        this.totalPurchased = 0.0;
        this.totalOrders = 0;
        this.totalSpent = 0.0; // Initialize to prevent null pointer
        this.loyaltyPoints = 0; // Initialize loyalty points
        this.joinDate = LocalDate.now();
    }

    public Customer(String customerId, String customerName, String phoneNumber) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.customerType = "Regular";
        this.totalPurchased = 0.0;
        this.totalOrders = 0;
        this.totalSpent = 0.0; // Initialize to prevent null pointer
        this.loyaltyPoints = 0; // Initialize loyalty points
        this.joinDate = LocalDate.now();
    }

    // Getters and Setters
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCustomerType() { return customerType; }
    public void setCustomerType(String customerType) { this.customerType = customerType; }

    public double getTotalPurchased() { return totalPurchased; }
    public void setTotalPurchased(double totalPurchased) { this.totalPurchased = totalPurchased; }

    public int getTotalOrders() { return totalOrders; }
    public void setTotalOrders(int totalOrders) { this.totalOrders = totalOrders; }

    public Double getTotalSpent() { return totalSpent; }
    public void setTotalSpent(Double totalSpent) { this.totalSpent = totalSpent; }

    public int getLoyaltyPoints() { return loyaltyPoints; }
    public void setLoyaltyPoints(int loyaltyPoints) { this.loyaltyPoints = loyaltyPoints; }

    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }

    // Helper methods
    public boolean isVipCustomer() {
        return "VIP".equals(customerType);
    }
}
