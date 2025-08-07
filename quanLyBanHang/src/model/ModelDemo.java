package model;

import java.math.BigDecimal;

/**
 * Demo class để test các model
 */
public class ModelDemo {
    public static void main(String[] args) {
        System.out.println("=== DEMO SỬ DỤNG CÁC MODEL ===");
        
        // 1. Demo Product Model
        demoProduct();
        
        // 2. Demo Customer Model  
        demoCustomer();
        
        // 3. Demo User Model
        demoUser();
        
        // 4. Demo Order Model
        demoOrder();
        
        // 5. Demo Category Model
        demoCategory();
        
        // 6. Demo Supplier Model
        demoSupplier();
    }
    
    public static void demoProduct() {
        System.out.println("\n--- DEMO PRODUCT MODEL ---");
        
        // Tạo sản phẩm mới
        Product product = new Product();
        product.setProductId("PRD001");
        product.setProductName("iPhone 15 Pro Max");
        product.setCategoryId("CAT_PHONE");
        product.setCategoryName("Điện thoại");
        product.setBrand("Apple");
        product.setPrice(new BigDecimal("30000000"));
        product.setCostPrice(new BigDecimal("25000000"));
        product.setStockQuantity(50);
        product.setMinStockLevel(5);
        product.setUnit("chiếc");
        
        // Test các chức năng
        System.out.println("Sản phẩm: " + product.toString());
        System.out.println("Còn hàng: " + product.isInStock());
        System.out.println("Lợi nhuận: " + product.getProfitMargin());
        
        // Test giảm kho
        boolean sold = product.reduceStock(2);
        System.out.println("Bán 2 sản phẩm: " + sold);
        System.out.println("Tồn kho còn: " + product.getStockQuantity());
        
        // Test cảnh báo hết hàng
        if (product.isLowStock()) {
            System.out.println("⚠️ Cảnh báo: Sản phẩm sắp hết hàng!");
        }
    }
    
    public static void demoCustomer() {
        System.out.println("\n--- DEMO CUSTOMER MODEL ---");
        
        // Tạo khách hàng mới
        Customer customer = new Customer("CUS001", "Nguyễn Văn A", "0901234567");
        customer.setEmail("nguyenvana@email.com");
        customer.setAddress("123 Nguyễn Huệ, Q1, TP.HCM");
        
        System.out.println("Khách hàng: " + customer.toString());
        System.out.println("Loại khách hàng: " + customer.getCustomerType());
        
        // Mô phỏng mua hàng
        customer.addPurchase(5000000); // Mua 5 triệu
        customer.addPurchase(8000000); // Mua 8 triệu  
        customer.addPurchase(12000000); // Mua 12 triệu
        
        System.out.println("Sau khi mua hàng:");
        System.out.println("Tổng mua: " + customer.getTotalPurchased());
        System.out.println("Số đơn hàng: " + customer.getTotalOrders());
        System.out.println("Loại khách hàng: " + customer.getCustomerType());
        System.out.println("Giá trị trung bình/đơn: " + customer.getAverageOrderValue());
    }
    
    public static void demoUser() {
        System.out.println("\n--- DEMO USER MODEL ---");
        
        // Tạo user admin
        User admin = new User("USR001", "admin", "admin123", "Quản trị viên", "admin@company.com", "ADMIN");
        admin.setPhoneNumber("0901111111");
        
        System.out.println("User: " + admin.toString());
        System.out.println("Là Admin: " + admin.isAdmin());
        System.out.println("Đang hoạt động: " + admin.isActive());
        
        // Mô phỏng đăng nhập
        admin.updateLastLogin();
        System.out.println("Đăng nhập lần cuối: " + admin.getLastLogin());
    }
    
    public static void demoOrder() {
        System.out.println("\n--- DEMO ORDER MODEL ---");
        
        // Tạo đơn hàng
        Order order = new Order("ORD001", "CUS001", "USR001");
        order.setCustomerName("Nguyễn Văn A");
        order.setEmployeeName("Quản trị viên");
        
        // Thêm sản phẩm vào đơn
        Order.OrderItem item1 = new Order.OrderItem("PRD001", "iPhone 15", new BigDecimal("30000000"), 1);
        Order.OrderItem item2 = new Order.OrderItem("PRD002", "Case iPhone", new BigDecimal("500000"), 2);
        
        order.addOrderItem(item1);
        order.addOrderItem(item2);
        
        // Áp dụng giảm giá
        order.setDiscountAmount(new BigDecimal("1000000"));
        
        System.out.println("Đơn hàng: " + order.toString());
        System.out.println("Tổng tiền: " + order.getTotalAmount());
        System.out.println("Tiền cuối: " + order.getFinalAmount());
        System.out.println("Số sản phẩm: " + order.getTotalItems());
        
        // Xử lý đơn hàng
        order.confirm();
        System.out.println("Trạng thái sau khi xác nhận: " + order.getStatus());
        
        order.complete();
        System.out.println("Trạng thái sau khi hoàn thành: " + order.getStatus());
    }
    
    public static void demoCategory() {
        System.out.println("\n--- DEMO CATEGORY MODEL ---");
        
        // Tạo danh mục cha
        Category electronics = new Category("CAT001", "Điện tử", "Các sản phẩm điện tử");
        
        // Tạo danh mục con
        Category phones = new Category("CAT001_01", "Điện thoại", "Điện thoại thông minh", "CAT001");
        Category laptops = new Category("CAT001_02", "Laptop", "Máy tính xách tay", "CAT001");
        
        electronics.addSubCategory(phones);
        electronics.addSubCategory(laptops);
        
        // Thêm sản phẩm vào danh mục
        electronics.incrementProductCount();
        phones.incrementProductCount();
        phones.incrementProductCount();
        
        System.out.println("Danh mục cha: " + electronics.toString());
        System.out.println("Có danh mục con: " + electronics.hasSubCategories());
        System.out.println("Số danh mục con: " + electronics.getSubCategories().size());
        System.out.println("Đường dẫn danh mục phone: " + phones.getFullPath());
    }
    
    public static void demoSupplier() {
        System.out.println("\n--- DEMO SUPPLIER MODEL ---");
        
        // Tạo nhà cung cấp
        Supplier supplier = new Supplier("SUP001", "Công ty ABC", "Nguyễn Văn B", "0902222222");
        supplier.setEmail("abc@supplier.com");
        supplier.setAddress("456 Lê Lợi, Q3, TP.HCM");
        supplier.setCreditLimit(100000000.0);
        
        System.out.println("Nhà cung cấp: " + supplier.toString());
        System.out.println("Đang hoạt động: " + supplier.isActive());
        System.out.println("Trong nước: " + supplier.isDomestic());
        
        // Mô phỏng đặt hàng
        BigDecimal orderAmount = new BigDecimal("50000000");
        if (supplier.isWithinCreditLimit(orderAmount)) {
            supplier.addPurchaseOrder(orderAmount);
            System.out.println("Đặt hàng thành công: " + orderAmount);
        }
        
        System.out.println("Tổng đã mua: " + supplier.getTotalPurchased());
        System.out.println("Số đơn hàng: " + supplier.getTotalOrders());
        System.out.println("Giá trị trung bình/đơn: " + supplier.getAverageOrderValue());
    }
}
