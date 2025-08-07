package controller;

import model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Hệ Thống Quản Lý Bán Hàng");
        model.addAttribute("welcomeMessage", "Chào mừng bạn đến với hệ thống quản lý bán hàng!");
        return "index";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Lấy dữ liệu thống kê
        SampleDataProvider.SummaryStats stats = SampleDataProvider.getSummaryStats();
        List<Product> lowStockProducts = SampleDataProvider.getSampleProducts().stream()
                .filter(Product::isLowStock)
                .collect(java.util.stream.Collectors.toList());
        List<Order> recentOrders = SampleDataProvider.getSampleOrders();
        List<Customer> vipCustomers = SampleDataProvider.getSampleCustomers().stream()
                .filter(Customer::isVipCustomer)
                .collect(java.util.stream.Collectors.toList());
        
        model.addAttribute("title", "Bảng Điều Khiển");
        model.addAttribute("stats", stats);
        model.addAttribute("lowStockProducts", lowStockProducts);
        model.addAttribute("recentOrders", recentOrders);
        model.addAttribute("vipCustomers", vipCustomers);
        
        return "dashboard";
    }
    
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Giới Thiệu");
        return "about";
    }
}
