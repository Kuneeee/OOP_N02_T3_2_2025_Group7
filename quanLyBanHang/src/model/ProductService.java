package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class để quản lý sản phẩm
 * Mô phỏng các chức năng CRUD và business logic
 */
public class ProductService {
    private static List<Product> products = new ArrayList<>();
    
    // Thêm một số sản phẩm mẫu
    static {
        initSampleData();
    }
    
    private static void initSampleData() {
        // iPhone
        Product iphone = new Product("PRD001", "iPhone 15 Pro Max", "CAT_PHONE", new BigDecimal("30000000"), 25);
        iphone.setCategoryName("Điện thoại");
        iphone.setBrand("Apple");
        iphone.setCostPrice(new BigDecimal("25000000"));
        iphone.setMinStockLevel(5);
        iphone.setUnit("chiếc");
        iphone.setDescription("Điện thoại thông minh cao cấp");
        
        // Samsung
        Product samsung = new Product("PRD002", "Samsung Galaxy S24", "CAT_PHONE", new BigDecimal("22000000"), 15);
        samsung.setCategoryName("Điện thoại");
        samsung.setBrand("Samsung");
        samsung.setCostPrice(new BigDecimal("18000000"));
        samsung.setMinStockLevel(5);
        samsung.setUnit("chiếc");
        samsung.setDescription("Flagship Android phone");
        
        // Laptop
        Product laptop = new Product("PRD003", "MacBook Pro M3", "CAT_LAPTOP", new BigDecimal("45000000"), 10);
        laptop.setCategoryName("Laptop");
        laptop.setBrand("Apple");
        laptop.setCostPrice(new BigDecimal("38000000"));
        laptop.setMinStockLevel(3);
        laptop.setUnit("chiếc");
        laptop.setDescription("Laptop chuyên nghiệp");
        
        products.add(iphone);
        products.add(samsung);
        products.add(laptop);
    }
    
    // CRUD Operations
    public static void addProduct(Product product) {
        products.add(product);
        System.out.println("✅ Đã thêm sản phẩm: " + product.getProductName());
    }
    
    public static Product findProductById(String productId) {
        return products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }
    
    public static List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }
    
    public static List<Product> findProductsByCategory(String categoryId) {
        return products.stream()
                .filter(p -> p.getCategoryId().equals(categoryId))
                .collect(Collectors.toList());
    }
    
    public static List<Product> findProductsByBrand(String brand) {
        return products.stream()
                .filter(p -> brand.equals(p.getBrand()))
                .collect(Collectors.toList());
    }
    
    public static List<Product> findLowStockProducts() {
        return products.stream()
                .filter(Product::isLowStock)
                .collect(Collectors.toList());
    }
    
    public static List<Product> searchProducts(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return products.stream()
                .filter(p -> p.getProductName().toLowerCase().contains(lowerKeyword) ||
                           (p.getBrand() != null && p.getBrand().toLowerCase().contains(lowerKeyword)))
                .collect(Collectors.toList());
    }
    
    // Business Logic
    public static boolean sellProduct(String productId, int quantity) {
        Product product = findProductById(productId);
        if (product != null && product.isInStock() && product.getStockQuantity() >= quantity) {
            boolean success = product.reduceStock(quantity);
            if (success) {
                System.out.println("✅ Đã bán " + quantity + " " + product.getProductName());
                if (product.isLowStock()) {
                    System.out.println("⚠️ Cảnh báo: " + product.getProductName() + " sắp hết hàng!");
                }
            }
            return success;
        }
        System.out.println("❌ Không thể bán sản phẩm: không đủ hàng hoặc không tìm thấy");
        return false;
    }
    
    public static void restockProduct(String productId, int quantity) {
        Product product = findProductById(productId);
        if (product != null) {
            product.addStock(quantity);
            System.out.println("✅ Đã nhập " + quantity + " " + product.getProductName());
        }
    }
    
    public static void displayAllProducts() {
        System.out.println("\n=== DANH SÁCH SẢN PHẨM ===");
        products.forEach(p -> {
            System.out.printf("ID: %s | Tên: %s | Giá: %,d | Tồn: %d | Trạng thái: %s%n",
                    p.getProductId(), p.getProductName(), p.getPrice().longValue(), 
                    p.getStockQuantity(), p.getStatus());
        });
    }
    
    public static void displayLowStockAlert() {
        List<Product> lowStock = findLowStockProducts();
        if (!lowStock.isEmpty()) {
            System.out.println("\n⚠️ === CẢNH BÁO HẾT HÀNG ===");
            lowStock.forEach(p -> {
                System.out.printf("- %s: còn %d (tối thiểu: %d)%n", 
                        p.getProductName(), p.getStockQuantity(), p.getMinStockLevel());
            });
        }
    }
    
    // Demo sử dụng
    public static void demo() {
        System.out.println("=== DEMO PRODUCT SERVICE ===");
        
        // Hiển thị tất cả sản phẩm
        displayAllProducts();
        
        // Tìm kiếm sản phẩm
        System.out.println("\n--- Tìm kiếm 'iPhone' ---");
        List<Product> searchResults = searchProducts("iPhone");
        searchResults.forEach(p -> System.out.println("Tìm thấy: " + p.getProductName()));
        
        // Bán sản phẩm
        System.out.println("\n--- Bán hàng ---");
        sellProduct("PRD001", 3);
        sellProduct("PRD002", 1);
        
        // Nhập hàng
        System.out.println("\n--- Nhập hàng ---");
        restockProduct("PRD001", 10);
        
        // Kiểm tra cảnh báo hết hàng
        displayLowStockAlert();
        
        // Hiển thị sản phẩm theo thương hiệu
        System.out.println("\n--- Sản phẩm Apple ---");
        List<Product> appleProducts = findProductsByBrand("Apple");
        appleProducts.forEach(p -> System.out.println("- " + p.getProductName()));
    }
}
