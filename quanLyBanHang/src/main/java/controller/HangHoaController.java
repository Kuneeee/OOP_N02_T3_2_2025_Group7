package controller;

import entity.HangHoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.HangHoaService;

import java.util.List;

@Controller
@RequestMapping("/hanghoa")
public class HangHoaController {
    
    @Autowired
    private HangHoaService hangHoaService;
    
    // Trang danh sách hàng hóa
    @GetMapping
    public String listHangHoa(Model model) {
        List<HangHoa> hangHoaList = hangHoaService.getAllHangHoa();
        model.addAttribute("hangHoaList", hangHoaList);
        model.addAttribute("newHangHoa", new HangHoa());
        return "hanghoa/index";
    }
    
    // Trang thêm mới hàng hóa
    @GetMapping("/new")
    public String newHangHoa(Model model) {
        model.addAttribute("hangHoa", new HangHoa());
        return "hanghoa/new";
    }
    
    // Trang tìm kiếm hàng hóa
    @GetMapping("/search")
    public String searchHangHoa(Model model) {
        List<HangHoa> hangHoaList = hangHoaService.getAllHangHoa();
        model.addAttribute("hangHoaList", hangHoaList);
        return "hanghoa/search";
    }
    
    // Xem chi tiết hàng hóa
    @GetMapping("/{id}")
    public String viewHangHoa(@PathVariable String id, Model model) {
        HangHoa hangHoa = hangHoaService.getHangHoaById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hàng hóa"));
        model.addAttribute("hangHoa", hangHoa);
        return "hanghoa/detail";
    }
    
    // Tạo mới hàng hóa
    @PostMapping
    public String createHangHoa(@ModelAttribute HangHoa hangHoa) {
        hangHoaService.createHangHoa(hangHoa);
        return "redirect:/hanghoa";
    }
    
    // Trang chỉnh sửa hàng hóa
    @GetMapping("/{id}/edit")
    public String editHangHoa(@PathVariable String id, Model model) {
        HangHoa hangHoa = hangHoaService.getHangHoaById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hàng hóa"));
        model.addAttribute("hangHoa", hangHoa);
        return "hanghoa/edit";
    }
    
    // Cập nhật hàng hóa
    @PostMapping("/{id}")
    public String updateHangHoa(@PathVariable String id, @ModelAttribute HangHoa hangHoa) {
        hangHoaService.updateHangHoa(id, hangHoa);
        return "redirect:/hanghoa/" + id;
    }
    
    // Xóa hàng hóa
    @PostMapping("/{id}/delete")
    public String deleteHangHoa(@PathVariable String id) {
        hangHoaService.deleteHangHoa(id);
        return "redirect:/hanghoa";
    }
    
    // API endpoints cho AJAX
    @GetMapping("/api")
    @ResponseBody
    public List<HangHoa> getHangHoaAPI() {
        return hangHoaService.getAllHangHoa();
    }
    
    @PostMapping("/api")
    @ResponseBody
    public HangHoa createHangHoaAPI(@RequestBody HangHoa hangHoa) {
        return hangHoaService.createHangHoa(hangHoa);
    }
    
    @GetMapping("/api/{id}")
    @ResponseBody
    public HangHoa getHangHoaAPI(@PathVariable String id) {
        return hangHoaService.getHangHoaById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hàng hóa"));
    }
    
    @PutMapping("/api/{id}")
    @ResponseBody
    public HangHoa updateHangHoaAPI(@PathVariable String id, @RequestBody HangHoa hangHoa) {
        return hangHoaService.updateHangHoa(id, hangHoa);
    }
    
    @DeleteMapping("/api/{id}")
    @ResponseBody
    public String deleteHangHoaAPI(@PathVariable String id) {
        boolean deleted = hangHoaService.deleteHangHoa(id);
        return deleted ? "Đã xóa thành công" : "Không thể xóa";
    }
}
