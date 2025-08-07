package model;

public class Customer {
    private String customerId;
    private String customerName;
    private String phoneNumber;
    private String email;
    private String address;
    private String customerType;
    private double totalPurchased;
    private int totalOrders;

    public Customer() {}

    public Customer(String customerId, String customerName, String phoneNumber) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
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

    // Helper methods
    public boolean isVipCustomer() {
        return "VIP".equals(customerType);
    }
}
