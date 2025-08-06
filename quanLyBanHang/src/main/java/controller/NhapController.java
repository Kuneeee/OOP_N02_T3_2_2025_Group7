package controller;

import entity.Nhap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/nhap")
public class NhapController {

    private static List<Nhap> nhapList = new ArrayList<>();
    private static int nextId = 1;

    static {
        // Dữ liệu mẫu
        nhapList.add(new Nhap("HH001", "Chuột Logitech", 50, 250000, "2024-01-10"));
        nhapList.add(new Nhap("HH002", "Bàn phím Cơ", 30, 700000, "2024-01-11"));
        nhapList.add(new Nhap("HH003", "Tai nghe Sony", 20, 1500000, "2024-01-12"));
        nextId = 4;
    }

    // Hiển thị danh sách nhập hàng
    @GetMapping({"", "/", "/list"})
    public String listNhap(Model model) {
        double totalCost = nhapList.stream()
                .mapToDouble(n -> n.getGiaNhap() * n.getSoLuongNhap())
                .sum();

        model.addAttribute("nhapList", nhapList);
        model.addAttribute("totalCost", totalCost);
        model.addAttribute("title", "Danh Sách Nhập Hàng");
        return "nhap/list";
    }

    // Hiển thị form thêm
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("nhap", new Nhap());
        model.addAttribute("title", "Thêm Nhập Hàng");
        model.addAttribute("action", "add");
        return "nhap/form";
    }

    // Xử lý thêm
    @PostMapping("/add")
    public String addNhap(@ModelAttribute Nhap nhap, RedirectAttributes redirectAttributes) {
        try {
            if (nhap.getHanghoaID() == null || nhap.getHanghoaID().isEmpty()) {
                nhap.setHanghoaID("HH" + String.format("%03d", nextId++));
            }
            nhapList.add(nhap);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm nhập hàng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/nhap/list";
    }

    // Form sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Nhap> nhap = nhapList.stream()
                .filter(n -> n.getHanghoaID().equals(id))
                .findFirst();

        if (nhap.isPresent()) {
            model.addAttribute("nhap", nhap.get());
            model.addAttribute("title", "Sửa Nhập Hàng");
            model.addAttribute("action", "edit");
            return "nhap/form";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy hàng hóa với ID: " + id);
            return "redirect:/nhap/list";
        }
    }

    // Xử lý cập nhật
    @PostMapping("/edit/{id}")
    public String updateNhap(@PathVariable String id, @ModelAttribute Nhap nhap, RedirectAttributes redirectAttributes) {
        for (int i = 0; i < nhapList.size(); i++) {
            if (nhapList.get(i).getHanghoaID().equals(id)) {
                nhap.setHanghoaID(id);
                nhapList.set(i, nhap);
                redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");
                return "redirect:/nhap/list";
            }
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy hàng hóa để cập nhật!");
        return "redirect:/nhap/list";
    }

    // Xem chi tiết
    @GetMapping("/view/{id}")
    public String viewNhap(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Nhap> nhap = nhapList.stream()
                .filter(n -> n.getHanghoaID().equals(id))
                .findFirst();

        if (nhap.isPresent()) {
            model.addAttribute("nhap", nhap.get());
            model.addAttribute("title", "Chi Tiết Nhập Hàng");
            return "nhap/view";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy hàng hóa với ID: " + id);
            return "redirect:/nhap/list";
        }
    }

    // Xóa
    @GetMapping("/delete/{id}")
    public String deleteNhap(@PathVariable String id, RedirectAttributes redirectAttributes) {
        boolean removed = nhapList.removeIf(n -> n.getHanghoaID().equals(id));
        if (removed) {
            redirectAttributes.addFlashAttribute("successMessage", "Xóa thành công!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy để xóa!");
        }
        return "redirect:/nhap/list";
    }

    // Tìm kiếm
    @GetMapping("/search")
    public String searchNhap(@RequestParam(required = false) String keyword, Model model) {
        List<Nhap> results = nhapList;
        if (keyword != null && !keyword.trim().isEmpty()) {
            String kw = keyword.trim().toLowerCase();
            results = nhapList.stream()
                    .filter(n -> n.getTenHangHoa().toLowerCase().contains(kw) ||
                                 n.getHanghoaID().toLowerCase().contains(kw))
                    .collect(Collectors.toList());
        }
        model.addAttribute("nhapList", results);
        model.addAttribute("title", "Kết Quả Tìm Kiếm");
        model.addAttribute("keyword", keyword);
        return "nhap/list";
    }

    // Thống kê
    @GetMapping("/statistics")
    public String statistics(Model model) {
        int totalNhap = nhapList.size();
        int totalQuantity = nhapList.stream().mapToInt(Nhap::getSoLuongNhap).sum();
        double totalCost = nhapList.stream().mapToDouble(n -> n.getGiaNhap() * n.getSoLuongNhap()).sum();
        double averagePrice = nhapList.stream().mapToDouble(Nhap::getGiaNhap).average().orElse(0.0);

        model.addAttribute("totalNhap", totalNhap);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalCost", totalCost);
        model.addAttribute("averagePrice", averagePrice);
        model.addAttribute("title", "Thống Kê Nhập Hàng");
        return "nhap/statistics";
    }
}