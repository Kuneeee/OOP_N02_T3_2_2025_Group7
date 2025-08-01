package controller;

import entity.Ban;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ban")
public class BanController {
    
    // Danh sách bán hàng tạm thời (thay thế database)
    private static List<Ban> banList = new ArrayList<>();
    private static int nextId = 1;
    
    static {
        // Dữ liệu mẫu
        banList.add(new Ban("HH001", "Laptop Dell", 16000000.0, 2));
        banList.add(new Ban("HH002", "Điện thoại iPhone", 26000000.0, 1));
        banList.add(new Ban("HH003", "Máy ảnh Canon", 13000000.0, 1));
        nextId = 4;
    }
    
    // Hiển thị danh sách bán hàng
    @GetMapping({"", "/", "/list"})
    public String listBan(Model model) {
        // Tính tổng doanh thu
        double totalRevenue = banList.stream()
                .mapToDouble(b -> b.getGiaBan() * b.getSoLuongBan())
                .sum();
        
        model.addAttribute("banList", banList);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("title", "Danh Sách Bán Hàng");
        return "ban/list";
    }
    
    // Hiển thị form thêm bán hàng mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("ban", new Ban());
        model.addAttribute("title", "Thêm Bán Hàng Mới");
        model.addAttribute("action", "add");
        return "ban/form";
    }
    
    // Xử lý thêm bán hàng mới
    @PostMapping("/add")
    public String addBan(@ModelAttribute Ban ban, RedirectAttributes redirectAttributes) {
        try {
            // Tạo ID tự động nếu chưa có
            if (ban.getHangHoaID() == null || ban.getHangHoaID().isEmpty()) {
                ban.setHangHoaID("HH" + String.format("%03d", nextId++));
            }
            banList.add(ban);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm bán hàng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thêm bán hàng: " + e.getMessage());
        }
        return "redirect:/ban/list";
    }
    
    // Hiển thị form sửa bán hàng
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Ban> ban = banList.stream()
                .filter(b -> b.getHangHoaID().equals(id))
                .findFirst();
        
        if (ban.isPresent()) {
            model.addAttribute("ban", ban.get());
            model.addAttribute("title", "Sửa Bán Hàng");
            model.addAttribute("action", "edit");
            return "ban/form";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy bán hàng với ID: " + id);
            return "redirect:/ban/list";
        }
    }
    
    // Xử lý cập nhật bán hàng
    @PostMapping("/edit/{id}")
    public String updateBan(@PathVariable String id, @ModelAttribute Ban ban, RedirectAttributes redirectAttributes) {
        try {
            for (int i = 0; i < banList.size(); i++) {
                if (banList.get(i).getHangHoaID().equals(id)) {
                    ban.setHangHoaID(id); // Giữ nguyên ID
                    banList.set(i, ban);
                    redirectAttributes.addFlashAttribute("successMessage", "Cập nhật bán hàng thành công!");
                    return "redirect:/ban/list";
                }
            }
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy bán hàng để cập nhật!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật bán hàng: " + e.getMessage());
        }
        return "redirect:/ban/list";
    }
    
    // Hiển thị chi tiết bán hàng
    @GetMapping("/view/{id}")
    public String viewBan(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Ban> ban = banList.stream()
                .filter(b -> b.getHangHoaID().equals(id))
                .findFirst();
        
        if (ban.isPresent()) {
            model.addAttribute("ban", ban.get());
            model.addAttribute("title", "Chi Tiết Bán Hàng");
            return "ban/view";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy bán hàng với ID: " + id);
            return "redirect:/ban/list";
        }
    }
    
    // Xóa bán hàng
    @GetMapping("/delete/{id}")
    public String deleteBan(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            boolean removed = banList.removeIf(b -> b.getHangHoaID().equals(id));
            if (removed) {
                redirectAttributes.addFlashAttribute("successMessage", "Xóa bán hàng thành công!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy bán hàng để xóa!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa bán hàng: " + e.getMessage());
        }
        return "redirect:/ban/list";
    }
    
    // Tìm kiếm bán hàng
    @GetMapping("/search")
    public String searchBan(@RequestParam(required = false) String keyword, Model model) {
        List<Ban> searchResults = banList;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            String searchKeyword = keyword.toLowerCase().trim();
            searchResults = banList.stream()
                    .filter(b -> b.getTenHangHoa().toLowerCase().contains(searchKeyword) ||
                               b.getHangHoaID().toLowerCase().contains(searchKeyword))
                    .toList();
        }
        
        model.addAttribute("banList", searchResults);
        model.addAttribute("title", "Kết Quả Tìm Kiếm Bán Hàng");
        model.addAttribute("keyword", keyword);
        return "ban/list";
    }
    
    // Thống kê bán hàng
    @GetMapping("/statistics")
    public String statistics(Model model) {
        int totalSales = banList.size();
        int totalQuantity = banList.stream().mapToInt(Ban::getSoLuongBan).sum();
        double totalRevenue = banList.stream()
                .mapToDouble(b -> b.getGiaBan() * b.getSoLuongBan())
                .sum();
        double averagePrice = banList.stream()
                .mapToDouble(Ban::getGiaBan)
                .average()
                .orElse(0.0);
        
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("averagePrice", averagePrice);
        model.addAttribute("title", "Thống Kê Bán Hàng");
        
        return "ban/statistics";
    }
}
