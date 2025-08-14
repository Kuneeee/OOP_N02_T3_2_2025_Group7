package controller;

import entity.Ban;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.BanService;

import java.util.List;

@Controller
@RequestMapping("/ban")
public class BanController {
    
    @Autowired
    private BanService banService;
    
    // Trang danh sách phiếu bán
    @GetMapping
    public String listBan(Model model) {
        List<Ban> banList = banService.getAllBan();
        model.addAttribute("banList", banList);
        model.addAttribute("newBan", new Ban());
        return "orders/index";
    }
    
    // Tạo mới phiếu bán
    @PostMapping
    public String createBan(@ModelAttribute Ban ban) {
        banService.createBan(ban);
        return "redirect:/ban";
    }
    
    // Trang chỉnh sửa phiếu bán
    @GetMapping("/{id}/edit")
    public String editBan(@PathVariable Long id, Model model) {
        Ban ban = banService.getBanById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu bán"));
        model.addAttribute("ban", ban);
        return "orders/edit";
    }
    
    // Cập nhật phiếu bán
    @PostMapping("/{id}")
    public String updateBan(@PathVariable Long id, @ModelAttribute Ban ban) {
        banService.updateBan(id, ban);
        return "redirect:/ban";
    }
    
    // Xóa phiếu bán
    @PostMapping("/{id}/delete")
    public String deleteBan(@PathVariable Long id) {
        banService.deleteBan(id);
        return "redirect:/ban";
    }
    
    // API endpoints
    @GetMapping("/api")
    @ResponseBody
    public List<Ban> getBanAPI() {
        return banService.getAllBan();
    }
    
    @PostMapping("/api")
    @ResponseBody
    public Ban createBanAPI(@RequestBody Ban ban) {
        return banService.createBan(ban);
    }
    
    @GetMapping("/api/{id}")
    @ResponseBody
    public Ban getBanAPI(@PathVariable Long id) {
        return banService.getBanById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu bán"));
    }
    
    @PutMapping("/api/{id}")
    @ResponseBody
    public Ban updateBanAPI(@PathVariable Long id, @RequestBody Ban ban) {
        return banService.updateBan(id, ban);
    }
    
    @DeleteMapping("/api/{id}")
    @ResponseBody
    public String deleteBanAPI(@PathVariable Long id) {
        boolean deleted = banService.deleteBan(id);
        return deleted ? "Đã xóa thành công" : "Không thể xóa";
    }
}
