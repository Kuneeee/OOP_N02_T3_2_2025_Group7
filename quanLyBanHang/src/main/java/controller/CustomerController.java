package controller;

import entity.KhachHang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.CustomerService;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    // Trang danh sách khách hàng
    @GetMapping
    public String listCustomers(Model model) {
        List<KhachHang> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        model.addAttribute("newCustomer", new KhachHang());
        return "customers/index";
    }
    
    // Tạo mới khách hàng
    @PostMapping
    public String createCustomer(@ModelAttribute KhachHang khachHang) {
        customerService.createCustomer(khachHang);
        return "redirect:/customers";
    }
    
    // Trang chỉnh sửa khách hàng
    @GetMapping("/{id}/edit")
    public String editCustomer(@PathVariable String id, Model model) {
        KhachHang khachHang = customerService.getCustomerById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
        model.addAttribute("customer", khachHang);
        return "customers/edit";
    }
    
    // Cập nhật khách hàng
    @PostMapping("/{id}")
    public String updateCustomer(@PathVariable String id, @ModelAttribute KhachHang khachHang) {
        customerService.updateCustomer(id, khachHang);
        return "redirect:/customers";
    }
    
    // Xóa khách hàng
    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }
    
    // API endpoints cho AJAX
    @GetMapping("/api")
    @ResponseBody
    public List<KhachHang> getCustomersAPI() {
        return customerService.getAllCustomers();
    }
    
    @PostMapping("/api")
    @ResponseBody
    public KhachHang createCustomerAPI(@RequestBody KhachHang khachHang) {
        return customerService.createCustomer(khachHang);
    }
    
    @GetMapping("/api/{id}")
    @ResponseBody
    public KhachHang getCustomerAPI(@PathVariable String id) {
        return customerService.getCustomerById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
    }
    
    @PutMapping("/api/{id}")
    @ResponseBody
    public KhachHang updateCustomerAPI(@PathVariable String id, @RequestBody KhachHang khachHang) {
        return customerService.updateCustomer(id, khachHang);
    }
    
    @DeleteMapping("/api/{id}")
    @ResponseBody
    public String deleteCustomerAPI(@PathVariable String id) {
        boolean deleted = customerService.deleteCustomer(id);
        return deleted ? "Đã xóa thành công" : "Không thể xóa";
    }
}
