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
}
