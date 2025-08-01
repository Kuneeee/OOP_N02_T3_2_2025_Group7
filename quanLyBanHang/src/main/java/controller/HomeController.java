package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        model.addAttribute("title", "Bảng Điều Khiển");
        return "dashboard";
    }
    
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Giới Thiệu");
        return "about";
    }
}
