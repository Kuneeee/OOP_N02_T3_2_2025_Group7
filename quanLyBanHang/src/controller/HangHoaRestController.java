package controller;

import entity.HangHoa;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/hanghoa")
@CrossOrigin(origins = "*")
public class HangHoaRestController {
    
    // Sử dụng cùng danh sách với HangHoaController
    private static List<HangHoa> hangHoaList = new ArrayList<>();
    private static int nextId = 1;
    
    static {
        // Dữ liệu mẫu
        hangHoaList.add(new HangHoa("HH001", "Laptop Dell", 10, "2025-01-01", 15000000.0, "Điện tử"));
        hangHoaList.add(new HangHoa("HH002", "Điện thoại iPhone", 5, "2025-01-02", 25000000.0, "Điện tử"));
        hangHoaList.add(new HangHoa("HH003", "Máy ảnh Canon", 3, "2025-01-03", 12000000.0, "Điện tử"));
        nextId = 4;
    }
    
    // Lấy tất cả hàng hóa
    @GetMapping
    public ResponseEntity<List<HangHoa>> getAllHangHoa() {
        return ResponseEntity.ok(hangHoaList);
    }
    
    // Lấy hàng hóa theo ID
    @GetMapping("/{id}")
    public ResponseEntity<HangHoa> getHangHoaById(@PathVariable String id) {
        Optional<HangHoa> hangHoa = hangHoaList.stream()
                .filter(h -> h.getHanghoaID().equals(id))
                .findFirst();
        
        if (hangHoa.isPresent()) {
            return ResponseEntity.ok(hangHoa.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Thêm hàng hóa mới
    @PostMapping
    public ResponseEntity<Map<String, Object>> createHangHoa(@RequestBody HangHoa hangHoa) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Tạo ID tự động
            hangHoa.setHanghoaID("HH" + String.format("%03d", nextId++));
            hangHoaList.add(hangHoa);
            
            response.put("success", true);
            response.put("message", "Thêm hàng hóa thành công!");
            response.put("data", hangHoa);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi khi thêm hàng hóa: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    // Cập nhật hàng hóa
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateHangHoa(@PathVariable String id, @RequestBody HangHoa hangHoa) {
        Map<String, Object> response = new HashMap<>();
        try {
            for (int i = 0; i < hangHoaList.size(); i++) {
                if (hangHoaList.get(i).getHanghoaID().equals(id)) {
                    hangHoa.setHanghoaID(id); // Giữ nguyên ID
                    hangHoaList.set(i, hangHoa);
                    
                    response.put("success", true);
                    response.put("message", "Cập nhật hàng hóa thành công!");
                    response.put("data", hangHoa);
                    return ResponseEntity.ok(response);
                }
            }
            
            response.put("success", false);
            response.put("message", "Không tìm thấy hàng hóa với ID: " + id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi khi cập nhật hàng hóa: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    // Xóa hàng hóa
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteHangHoa(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean removed = hangHoaList.removeIf(h -> h.getHanghoaID().equals(id));
            if (removed) {
                response.put("success", true);
                response.put("message", "Xóa hàng hóa thành công!");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Không tìm thấy hàng hóa với ID: " + id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi khi xóa hàng hóa: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // Tìm kiếm hàng hóa
    @GetMapping("/search")
    public ResponseEntity<List<HangHoa>> searchHangHoa(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false) String loai) {
        List<HangHoa> searchResults = new ArrayList<>();
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            String searchKeyword = keyword.toLowerCase().trim();
            searchResults = hangHoaList.stream()
                    .filter(h -> h.getTenHangHoa().toLowerCase().contains(searchKeyword) ||
                               h.getHanghoaID().toLowerCase().contains(searchKeyword))
                    .toList();
        } else if (loai != null && !loai.trim().isEmpty()) {
            searchResults = hangHoaList.stream()
                    .filter(h -> h.getLoaiHangHoa().equalsIgnoreCase(loai.trim()))
                    .toList();
        } else {
            searchResults = hangHoaList;
        }
        
        return ResponseEntity.ok(searchResults);
    }
    
    // Thống kê hàng hóa
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        int totalProducts = hangHoaList.size();
        int totalQuantity = hangHoaList.stream().mapToInt(HangHoa::getSoLuongHangHoa).sum();
        double totalValue = hangHoaList.stream()
                .mapToDouble(h -> h.getGiaNhap() * h.getSoLuongHangHoa())
                .sum();
        
        // Thống kê theo loại
        var categoryStats = hangHoaList.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                    HangHoa::getLoaiHangHoa,
                    java.util.stream.Collectors.counting()
                ));
        
        stats.put("totalProducts", totalProducts);
        stats.put("totalQuantity", totalQuantity);
        stats.put("totalValue", totalValue);
        stats.put("categoryStats", categoryStats);
        
        return ResponseEntity.ok(stats);
    }
    
    // Lấy danh sách loại hàng hóa
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = hangHoaList.stream()
                .map(HangHoa::getLoaiHangHoa)
                .distinct()
                .sorted()
                .toList();
        return ResponseEntity.ok(categories);
    }
}
