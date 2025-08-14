package controller;

import entity.Nhap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.NhapService;

import java.util.List;

@Controller
@RequestMapping("/nhap")
public class NhapController {
    
    @Autowired
    private NhapService nhapService;
    
    // Trang danh sách phiếu nhập
    @GetMapping
    public String listNhap(Model model) {
        List<Nhap> nhapList = nhapService.getAllNhap();
        model.addAttribute("nhapList", nhapList);
        model.addAttribute("newNhap", new Nhap());
        return "nhap/index";
    }
    
    // Trang thêm mới phiếu nhập
    @GetMapping("/new")
    public String newNhap(Model model) {
        model.addAttribute("nhap", new Nhap());
        return "nhap/new";
    }
    
    // Trang tìm kiếm phiếu nhập
    @GetMapping("/search")
    public String searchNhap(Model model) {
        List<Nhap> nhapList = nhapService.getAllNhap();
        model.addAttribute("nhapList", nhapList);
        return "nhap/search";
    }
    
    // Xem chi tiết phiếu nhập
    @GetMapping("/{id}")
    public String viewNhap(@PathVariable Long id, Model model) {
        Nhap nhap = nhapService.getNhapById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu nhập"));
        model.addAttribute("nhap", nhap);
        return "nhap/detail";
    }
    
    // Tạo mới phiếu nhập
    @PostMapping
    public String createNhap(@ModelAttribute Nhap nhap) {
        nhapService.createNhap(nhap);
        return "redirect:/nhap";
    }
    
    // Trang chỉnh sửa phiếu nhập
    @GetMapping("/{id}/edit")
    public String editNhap(@PathVariable Long id, Model model) {
        Nhap nhap = nhapService.getNhapById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu nhập"));
        model.addAttribute("nhap", nhap);
        return "nhap/edit";
    }
    
    // Cập nhật phiếu nhập
    @PostMapping("/{id}")
    public String updateNhap(@PathVariable Long id, @ModelAttribute Nhap nhap) {
        nhapService.updateNhap(id, nhap);
        return "redirect:/nhap";
    }
    
    // Xóa phiếu nhập
    @PostMapping("/{id}/delete")
    public String deleteNhap(@PathVariable Long id) {
        nhapService.deleteNhap(id);
        return "redirect:/nhap";
    }
    
    // API endpoints
    @GetMapping("/api")
    @ResponseBody
    public List<Nhap> getNhapAPI() {
        return nhapService.getAllNhap();
    }
    
    @PostMapping("/api")
    @ResponseBody
    public Nhap createNhapAPI(@RequestBody Nhap nhap) {
        return nhapService.createNhap(nhap);
    }
    
    @GetMapping("/api/{id}")
    @ResponseBody
    public Nhap getNhapAPI(@PathVariable Long id) {
        return nhapService.getNhapById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu nhập"));
    }
    
    @PutMapping("/api/{id}")
    @ResponseBody
    public Nhap updateNhapAPI(@PathVariable Long id, @RequestBody Nhap nhap) {
        return nhapService.updateNhap(id, nhap);
    }
    
    @DeleteMapping("/api/{id}")
    @ResponseBody
    public String deleteNhapAPI(@PathVariable Long id) {
        boolean deleted = nhapService.deleteNhap(id);
        return deleted ? "Đã xóa thành công" : "Không thể xóa";
    }
}
