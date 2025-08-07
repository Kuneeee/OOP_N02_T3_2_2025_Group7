package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Supplier {
    private String supplierId;
    private String supplierName;
    private String contactPerson;
    private String phoneNumber;
    private String email;
    private String address;
    private String supplierType;
    private String taxCode;
    private String bankAccount;
    private String bankName;
    private Double creditLimit;
    private List<BigDecimal> purchaseOrders;

    public Supplier() {
        this.purchaseOrders = new ArrayList<>();
    }

    public Supplier(String supplierId, String supplierName, String contactPerson, String phoneNumber) {
        this();
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contactPerson = contactPerson;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getSupplierType() { return supplierType; }
    public void setSupplierType(String supplierType) { this.supplierType = supplierType; }

    public String getTaxCode() { return taxCode; }
    public void setTaxCode(String taxCode) { this.taxCode = taxCode; }

    public String getBankAccount() { return bankAccount; }
    public void setBankAccount(String bankAccount) { this.bankAccount = bankAccount; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public Double getCreditLimit() { return creditLimit; }
    public void setCreditLimit(Double creditLimit) { this.creditLimit = creditLimit; }

    public List<BigDecimal> getPurchaseOrders() { return purchaseOrders; }
    public void setPurchaseOrders(List<BigDecimal> purchaseOrders) { this.purchaseOrders = purchaseOrders; }

    // Helper methods
    public void addPurchaseOrder(BigDecimal amount) {
        this.purchaseOrders.add(amount);
    }

    public BigDecimal getTotalPurchases() {
        return purchaseOrders.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
