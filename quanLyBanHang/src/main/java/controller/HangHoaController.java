package controller;

import entity.HangHoa;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/hanghoa")
public class HangHoaController {
    
    // Danh sách hàng hóa tạm thời (thay thế database)
    private static List<HangHoa> hangHoaList = new ArrayList<>();
    private static int nextId = 1;
    
    static {
        // Dữ liệu mẫu
        hangHoaList.add(new HangHoa("HH001", "Laptop Dell", 10, "2025-01-01", 15000000.0, "Điện tử"));
        hangHoaList.add(new HangHoa("HH002", "Điện thoại iPhone", 5, "2025-01-02", 25000000.0, "Điện tử"));
        hangHoaList.add(new HangHoa("HH003", "Máy ảnh Canon", 3, "2025-01-03", 12000000.0, "Điện tử"));
        nextId = 4;
    }
    
    // Hiển thị danh sách hàng hóa
    @GetMapping({"", "/", "/list"})
    public String listHangHoa(Model model) {
        model.addAttribute("hangHoaList", hangHoaList);
        model.addAttribute("title", "Danh Sách Hàng Hóa");
        return "hanghoa/list";
    }
    
    // Hiển thị form thêm hàng hóa mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("hangHoa", new HangHoa());
        model.addAttribute("title", "Thêm Hàng Hóa Mới");
        model.addAttribute("action", "add");
        return "hanghoa/form";
    }
    
    // Xử lý thêm hàng hóa mới
    @PostMapping("/add")
    public String addHangHoa(@ModelAttribute HangHoa hangHoa, RedirectAttributes redirectAttributes) {
        try {
            // Tạo ID tự động
            hangHoa.setHanghoaID("HH" + String.format("%03d", nextId++));
            hangHoaList.add(hangHoa);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm hàng hóa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thêm hàng hóa: " + e.getMessage());
        }
        return "redirect:/hanghoa/list";
    }
    
    // Hiển thị form sửa hàng hóa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        Optional<HangHoa> hangHoa = hangHoaList.stream()
                .filter(h -> h.getHanghoaID().equals(id))
                .findFirst();
        
        if (hangHoa.isPresent()) {
            model.addAttribute("hangHoa", hangHoa.get());
            model.addAttribute("title", "Sửa Hàng Hóa");
            model.addAttribute("action", "edit");
            return "hanghoa/form";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy hàng hóa với ID: " + id);
            return "redirect:/hanghoa/list";
        }
    }
    
    // Xử lý cập nhật hàng hóa
    @PostMapping("/edit/{id}")
    public String updateHangHoa(@PathVariable String id, @ModelAttribute HangHoa hangHoa, RedirectAttributes redirectAttributes) {
        try {
            for (int i = 0; i < hangHoaList.size(); i++) {
                if (hangHoaList.get(i).getHanghoaID().equals(id)) {
                    hangHoa.setHanghoaID(id); // Giữ nguyên ID
                    hangHoaList.set(i, hangHoa);
                    redirectAttributes.addFlashAttribute("successMessage", "Cập nhật hàng hóa thành công!");
                    return "redirect:/hanghoa/list";
                }
            }
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy hàng hóa để cập nhật!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật hàng hóa: " + e.getMessage());
        }
        return "redirect:/hanghoa/list";
    }
    
    // Hiển thị chi tiết hàng hóa
    @GetMapping("/view/{id}")
    public String viewHangHoa(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        Optional<HangHoa> hangHoa = hangHoaList.stream()
                .filter(h -> h.getHanghoaID().equals(id))
                .findFirst();
        
        if (hangHoa.isPresent()) {
            model.addAttribute("hangHoa", hangHoa.get());
            model.addAttribute("title", "Chi Tiết Hàng Hóa");
            return "hanghoa/view";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy hàng hóa với ID: " + id);
            return "redirect:/hanghoa/list";
        }
    }
    
    // Xóa hàng hóa
    @GetMapping("/delete/{id}")
    public String deleteHangHoa(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            boolean removed = hangHoaList.removeIf(h -> h.getHanghoaID().equals(id));
            if (removed) {
                redirectAttributes.addFlashAttribute("successMessage", "Xóa hàng hóa thành công!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy hàng hóa để xóa!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa hàng hóa: " + e.getMessage());
        }
        return "redirect:/hanghoa/list";
    }
    
    // Tìm kiếm hàng hóa đơn giản
    @GetMapping("/search")
    public String searchHangHoa(@RequestParam(required = false) String keyword, Model model) {
        List<HangHoa> searchResults = hangHoaList;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            String searchKeyword = keyword.toLowerCase().trim();
            searchResults = hangHoaList.stream()
                    .filter(h -> h.getTenHangHoa().toLowerCase().contains(searchKeyword) ||
                               h.getHanghoaID().toLowerCase().contains(searchKeyword) ||
                               h.getLoaiHangHoa().toLowerCase().contains(searchKeyword))
                    .collect(Collectors.toList());
        }
        
        model.addAttribute("hangHoaList", searchResults);
        model.addAttribute("title", "Kết Quả Tìm Kiếm");
        model.addAttribute("keyword", keyword);
        return "hanghoa/list";
    }
    
    // Thống kê hàng hóa đơn giản
    @GetMapping("/statistics")
    public String statistics(Model model) {
        int totalProducts = hangHoaList.size();
        int totalQuantity = hangHoaList.stream().mapToInt(HangHoa::getSoLuongHangHoa).sum();
        double totalValue = hangHoaList.stream()
                .mapToDouble(h -> h.getGiaNhap() * h.getSoLuongHangHoa())
                .sum();
        
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalValue", totalValue);
        model.addAttribute("title", "Thống Kê Hàng Hóa");
        
        return "hanghoa/statistics";
    }
}
