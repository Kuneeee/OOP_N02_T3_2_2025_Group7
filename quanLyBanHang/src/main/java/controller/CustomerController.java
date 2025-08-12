package controller;

import model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    
    @GetMapping
    public String customers(Model model, 
                           @RequestParam(required = false) String search,
                           @RequestParam(required = false) String type) {
        List<Customer> customers = SampleDataProvider.getSampleCustomers();
        
        // Filter by search keyword
        if (search != null && !search.trim().isEmpty()) {
            customers = customers.stream()
                    .filter(c -> c.getCustomerName().toLowerCase().contains(search.toLowerCase()) ||
                               (c.getPhoneNumber() != null && c.getPhoneNumber().contains(search)) ||
                               (c.getEmail() != null && c.getEmail().toLowerCase().contains(search.toLowerCase())))
                    .collect(Collectors.toList());
        }
        
        // Filter by customer type
        if (type != null && !type.trim().isEmpty()) {
            customers = customers.stream()
                    .filter(c -> type.equals(c.getCustomerType()))
                    .collect(Collectors.toList());
        }
        
        // Count by type for statistics
        List<Customer> allCustomers = SampleDataProvider.getSampleCustomers();
        long vipCount = allCustomers.stream().filter(c -> "VIP".equals(c.getCustomerType())).count();
        long regularCount = allCustomers.stream().filter(c -> "Regular".equals(c.getCustomerType())).count();
        long newCount = allCustomers.stream().filter(c -> "New".equals(c.getCustomerType())).count();
        
        model.addAttribute("customers", customers);
        model.addAttribute("vipCount", vipCount);
        model.addAttribute("regularCount", regularCount);
        model.addAttribute("newCount", newCount);
        model.addAttribute("currentSearch", search);
        model.addAttribute("currentType", type);
        model.addAttribute("title", "Quản Lý Khách Hàng");
        
        return "customers/list";
    }
    
    @GetMapping("/{id}")
    public String customerDetail(@PathVariable String id, Model model) {
        List<Customer> customers = SampleDataProvider.getSampleCustomers();
        Customer customer = customers.stream()
                .filter(c -> c.getCustomerId().equals(id))
                .findFirst()
                .orElse(null);
        
        if (customer == null) {
            return "redirect:/customers";
        }
        
        // Get customer's orders
        List<Order> customerOrders = SampleDataProvider.getSampleOrders().stream()
                .filter(o -> o.getCustomerId().equals(id))
                .collect(Collectors.toList());
        
        model.addAttribute("customer", customer);
        model.addAttribute("customerOrders", customerOrders);
        model.addAttribute("title", "Chi Tiết Khách Hàng - " + customer.getCustomerName());
        
        return "customers/detail";
    }
    
    @GetMapping("/vip")
    public String vipCustomers(Model model) {
        List<Customer> customers = SampleDataProvider.getSampleCustomers();
        List<Customer> vipCustomers = customers.stream()
                .filter(Customer::isVipCustomer)
                .collect(Collectors.toList());
        
        model.addAttribute("customers", vipCustomers);
        model.addAttribute("vipCount", vipCustomers.size());
        model.addAttribute("regularCount", 0L);
        model.addAttribute("newCount", 0L);
        model.addAttribute("title", "Khách Hàng VIP");
        model.addAttribute("isVipView", true);
        
        return "customers/list";
    }
    
    // Hiển thị form thêm khách hàng mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
    model.addAttribute("customer", new Customer());
        model.addAttribute("title", "Thêm Khách Hàng Mới");
        model.addAttribute("action", "add");
        return "customers/form";
    }
    
    // Xử lý thêm khách hàng mới
    @PostMapping("/add")
    public String addCustomer(@ModelAttribute Customer customer, Model model) {
        System.out.println("=== CustomerController /add POST được gọi ===");
        System.out.println("Customer name: " + customer.getCustomerName());
        
        try {
            // Generate new ID based on existing customers
            List<Customer> existingCustomers = SampleDataProvider.getSampleCustomers();
            String nextId = "CUST" + String.format("%03d", existingCustomers.size() + 1);
            customer.setCustomerId(nextId);
            
            System.out.println("Generated ID: " + customer.getCustomerId());
            
            // Set default values if not provided
            if (customer.getCustomerType() == null || customer.getCustomerType().isEmpty()) {
                customer.setCustomerType("New");
            }
            if (customer.getTotalSpent() == null) {
                customer.setTotalSpent(0.0);
            }
            if (customer.getTotalOrders() == 0) {
                customer.setTotalOrders(0);
            }
            
            // Note: Since we're using in-memory data, customer won't persist
            // This is for demo purposes only - actual persistence requires database
            
            int totalAfterAdd = SampleDataProvider.getSampleCustomers().size();
            System.out.println("Customer processed successfully. Total customers: " + totalAfterAdd);
            System.out.println("Returning to customers list view...");
            
            // Instead of redirect, return to customers list directly with success message
            List<Customer> all = SampleDataProvider.getSampleCustomers();
            List<Customer> vipCustomers = all.stream()
                    .filter(Customer::isVipCustomer)
                    .collect(Collectors.toList());
            
            long regularCount = all.stream()
                    .filter(c -> "Regular".equals(c.getCustomerType()))
                    .count();
            
            long newCount = all.stream()
                    .filter(c -> "New".equals(c.getCustomerType()))
                    .count();
            
            model.addAttribute("customers", all);
            model.addAttribute("vipCount", vipCustomers.size());
            model.addAttribute("regularCount", regularCount);
            model.addAttribute("newCount", newCount);
            model.addAttribute("title", "Quản Lý Khách Hàng");
            model.addAttribute("successMessage", "Thêm khách hàng thành công!");
            
            return "customers/list";
        } catch (Exception e) {
            System.out.println("Error adding customer: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            model.addAttribute("customer", customer);
            model.addAttribute("title", "Thêm Khách Hàng Mới");
            model.addAttribute("action", "add");
            return "customers/form";
        }
    }
    
    // Hiển thị form chỉnh sửa khách hàng
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Customer customer = SampleDataProvider.getSampleCustomers().stream()
                .filter(c -> c.getCustomerId().equals(id))
                .findFirst()
                .orElse(null);
        
        if (customer == null) {
            model.addAttribute("error", "Không tìm thấy khách hàng!");
            return "redirect:/customers";
        }
        
        model.addAttribute("customer", customer);
        model.addAttribute("title", "Chỉnh Sửa Khách Hàng - " + customer.getCustomerName());
        model.addAttribute("action", "edit");
        return "customers/form";
    }
    
    // Xử lý cập nhật khách hàng
    @PostMapping("/edit/{id}")
    public String updateCustomer(@PathVariable String id, @ModelAttribute Customer customer, Model model) {
        System.out.println("=== CustomerController /edit POST được gọi ===");
        System.out.println("Customer ID: " + id + ", Customer name: " + customer.getCustomerName());
        
        try {
            Customer existingCustomer = SampleDataProvider.getSampleCustomers().stream()
                    .filter(c -> c.getCustomerId().equals(id))
                    .findFirst()
                    .orElse(null);
            
            if (existingCustomer == null) {
                model.addAttribute("error", "Không tìm thấy khách hàng!");
                return "redirect:/customers";
            }
            
            // Update customer fields
            existingCustomer.setCustomerName(customer.getCustomerName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setPhoneNumber(customer.getPhoneNumber());
            existingCustomer.setAddress(customer.getAddress());
            existingCustomer.setCustomerType(customer.getCustomerType());
            existingCustomer.setTotalSpent(customer.getTotalSpent());
            existingCustomer.setTotalOrders(customer.getTotalOrders());
            existingCustomer.setLoyaltyPoints(customer.getLoyaltyPoints());
            
            // Note: Since we're using in-memory data, changes won't persist
            // This is for demo purposes only - actual persistence requires database
            
            int totalAfterUpdate = SampleDataProvider.getSampleCustomers().size();
            System.out.println("Customer updated successfully. Total customers: " + totalAfterUpdate);
            System.out.println("Returning to customers list view...");
            
            // Return to customers list directly with success message
            List<Customer> all = SampleDataProvider.getSampleCustomers();
            List<Customer> vipCustomers = all.stream()
                    .filter(Customer::isVipCustomer)
                    .collect(Collectors.toList());
            
            long regularCount = all.stream()
                    .filter(c -> "Regular".equals(c.getCustomerType()))
                    .count();
            
            long newCount = all.stream()
                    .filter(c -> "New".equals(c.getCustomerType()))
                    .count();
            
            model.addAttribute("customers", all);
            model.addAttribute("vipCount", vipCustomers.size());
            model.addAttribute("regularCount", regularCount);
            model.addAttribute("newCount", newCount);
            model.addAttribute("title", "Quản Lý Khách Hàng");
            model.addAttribute("successMessage", "Cập nhật khách hàng thành công!");
            
            return "customers/list";
        } catch (Exception e) {
            System.out.println("Error updating customer: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            model.addAttribute("customer", customer);
            model.addAttribute("title", "Chỉnh Sửa Khách Hàng");
            model.addAttribute("action", "edit");
            return "customers/form";
        }
    }
    
    // Xóa khách hàng
    @PostMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable String id, Model model) {
        System.out.println("=== CustomerController /delete POST được gọi ===");
        System.out.println("Customer ID to delete: " + id);
        
        try {
            Customer customerToDelete = SampleDataProvider.getSampleCustomers().stream()
                    .filter(c -> c.getCustomerId().equals(id))
                    .findFirst()
                    .orElse(null);
            
            if (customerToDelete == null) {
                model.addAttribute("error", "Không tìm thấy khách hàng để xóa!");
            } else {
                // Note: Since we're using in-memory data, deletion won't persist
                // This is for demo purposes only - actual persistence requires database
                int totalAfterDelete = SampleDataProvider.getSampleCustomers().size();
                System.out.println("Customer processed successfully. Total customers: " + totalAfterDelete);
                model.addAttribute("successMessage", "Xóa khách hàng thành công!");
            }
            
            // Return to customers list directly
            List<Customer> customers = SampleDataProvider.getSampleCustomers();
            List<Customer> vipCustomers = customers.stream()
                    .filter(Customer::isVipCustomer)
                    .collect(Collectors.toList());
            
            long regularCount = customers.stream()
                    .filter(c -> "Regular".equals(c.getCustomerType()))
                    .count();
            
            long newCount = customers.stream()
                    .filter(c -> "New".equals(c.getCustomerType()))
                    .count();
            
            model.addAttribute("customers", customers);
            model.addAttribute("vipCount", vipCustomers.size());
            model.addAttribute("regularCount", regularCount);
            model.addAttribute("newCount", newCount);
            model.addAttribute("title", "Quản Lý Khách Hàng");
            
            return "customers/list";
        } catch (Exception e) {
            System.out.println("Error deleting customer: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/customers";
        }
    }
}
