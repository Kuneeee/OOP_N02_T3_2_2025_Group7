package controller;

import model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrderController {
    
    @GetMapping
    public String orders(Model model, 
                        @RequestParam(required = false) String search,
                        @RequestParam(required = false) String status,
                        @RequestParam(required = false) String customer) {
        List<Order> orders = SampleDataProvider.getSampleOrders();
        List<Customer> customers = SampleDataProvider.getSampleCustomers();        // Filter by search keyword
        if (search != null && !search.trim().isEmpty()) {
            orders = orders.stream()
                    .filter(o -> o.getOrderId().toLowerCase().contains(search.toLowerCase()) ||
                               (o.getCustomerName() != null && o.getCustomerName().toLowerCase().contains(search.toLowerCase())))
                    .collect(Collectors.toList());
        }
        
        // Filter by status
        if (status != null && !status.trim().isEmpty()) {
            orders = orders.stream()
                    .filter(o -> status.equals(o.getStatus()))
                    .collect(Collectors.toList());
        }
        
        // Filter by customer
        if (customer != null && !customer.trim().isEmpty()) {
            orders = orders.stream()
                    .filter(o -> customer.equals(o.getCustomerId()))
                    .collect(Collectors.toList());
        }
        
        // Count by status for statistics
        List<Order> allOrders = SampleDataProvider.getSampleOrders();
        long pendingCount = allOrders.stream().filter(o -> "PENDING".equals(o.getStatus())).count();
        long processingCount = allOrders.stream().filter(o -> "PROCESSING".equals(o.getStatus())).count();
        long shippedCount = allOrders.stream().filter(o -> "SHIPPED".equals(o.getStatus())).count();
        long deliveredCount = allOrders.stream().filter(o -> "DELIVERED".equals(o.getStatus()) || "COMPLETED".equals(o.getStatus())).count();
        long cancelledCount = allOrders.stream().filter(o -> "CANCELLED".equals(o.getStatus())).count();
        
        model.addAttribute("orders", orders);
        model.addAttribute("customers", customers);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("processingCount", processingCount);
        model.addAttribute("shippedCount", shippedCount);
        model.addAttribute("deliveredCount", deliveredCount);
        model.addAttribute("cancelledCount", cancelledCount);
        model.addAttribute("currentSearch", search);
        model.addAttribute("currentStatus", status);
        model.addAttribute("currentCustomer", customer);
        model.addAttribute("title", "Quản Lý Đơn Hàng");
        
        return "orders/list";
    }
    
    @GetMapping("/{id}")
    public String orderDetail(@PathVariable String id, Model model) {
        List<Order> orders = SampleDataProvider.getSampleOrders();
        Order order = orders.stream()
                .filter(o -> o.getOrderId().equals(id))
                .findFirst()
                .orElse(null);
        
        if (order == null) {
            return "redirect:/orders";
        }
        
        // Get customer details
        Customer customer = SampleDataProvider.getSampleCustomers().stream()
                .filter(c -> c.getCustomerId().equals(order.getCustomerId()))
                .findFirst()
                .orElse(null);
        
        model.addAttribute("order", order);
        model.addAttribute("customer", customer);
        model.addAttribute("title", "Chi Tiết Đơn Hàng - " + order.getOrderId());
        
        return "orders/detail";
    }
    
    @GetMapping("/pending")
    public String pendingOrders(Model model) {
        List<Order> orders = SampleDataProvider.getSampleOrders();
        List<Order> pendingOrders = orders.stream()
                .filter(o -> "PENDING".equals(o.getStatus()))
                .collect(Collectors.toList());
        
        model.addAttribute("orders", pendingOrders);
        model.addAttribute("customers", SampleDataProvider.getSampleCustomers());
        model.addAttribute("pendingCount", pendingOrders.size());
        model.addAttribute("processingCount", 0L);
        model.addAttribute("shippedCount", 0L);
        model.addAttribute("deliveredCount", 0L);
        model.addAttribute("cancelledCount", 0L);
        model.addAttribute("title", "Đơn Hàng Chờ Xử Lý");
        model.addAttribute("isPendingView", true);
        
        return "orders/list";
    }
    
    @GetMapping("/completed")
    public String completedOrders(Model model) {
        List<Order> orders = SampleDataProvider.getSampleOrders();
        List<Order> completedOrders = orders.stream()
                .filter(o -> "COMPLETED".equals(o.getStatus()) || "DELIVERED".equals(o.getStatus()))
                .collect(Collectors.toList());
        
        model.addAttribute("orders", completedOrders);
        model.addAttribute("customers", SampleDataProvider.getSampleCustomers());
        model.addAttribute("pendingCount", 0L);
        model.addAttribute("processingCount", 0L);
        model.addAttribute("shippedCount", 0L);
        model.addAttribute("deliveredCount", completedOrders.size());
        model.addAttribute("cancelledCount", 0L);
        model.addAttribute("title", "Đơn Hàng Hoàn Thành");
        model.addAttribute("isCompletedView", true);
        
        return "orders/list";
    }
}
