package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Class quản lý bán hàng tổng thể
 * Tích hợp tất cả các model để tạo thành hệ thống hoàn chỉnh
 */
public class SalesManagementSystem {
    private static List<User> users = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();
    private static User currentUser = null;
    
    // Khởi tạo dữ liệu mẫu
    static {
        initSampleData();
    }
    
    private static void initSampleData() {
        // Tạo users
        User admin = new User("USR001", "admin", "admin123", "Quản trị viên", "admin@store.com", "ADMIN");
        User manager = new User("USR002", "manager", "manager123", "Nguyễn Văn Quản lý", "manager@store.com", "MANAGER");
        User employee = new User("USR003", "employee", "emp123", "Trần Thị Nhân viên", "employee@store.com", "EMPLOYEE");
        
        users.add(admin);
        users.add(manager);
        users.add(employee);
        
        // Tạo customers
        Customer vip = new Customer("CUS001", "Nguyễn Văn VIP", "0901234567");
        vip.setEmail("vip@customer.com");
        vip.setCustomerType("VIP");
        vip.setTotalPurchased(80000000.0);
        vip.setTotalOrders(20);
        
        Customer regular = new Customer("CUS002", "Trần Thị Thường", "0912345678");
        regular.setEmail("regular@customer.com");
        regular.setCustomerType("REGULAR");
        regular.setTotalPurchased(15000000.0);
        regular.setTotalOrders(8);
        
        customers.add(vip);
        customers.add(regular);
    }
    
    // Authentication
    public static boolean login(String username, String password) {
        User user = users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
        
        if (user != null && user.isActive()) {
            currentUser = user;
            user.updateLastLogin();
            System.out.println("✅ Đăng nhập thành công! Chào mừng " + user.getFullName());
            return true;
        }
        
        System.out.println("❌ Đăng nhập thất bại!");
        return false;
    }
    
    public static void logout() {
        if (currentUser != null) {
            System.out.println("👋 Tạm biệt " + currentUser.getFullName());
            currentUser = null;
        }
    }
    
    public static User getCurrentUser() {
        return currentUser;
    }
    
    // Customer Management
    public static Customer findCustomerByPhone(String phoneNumber) {
        return customers.stream()
                .filter(c -> c.getPhoneNumber().equals(phoneNumber))
                .findFirst()
                .orElse(null);
    }
    
    public static Customer createNewCustomer(String name, String phone) {
        String customerId = "CUS" + String.format("%03d", customers.size() + 1);
        Customer customer = new Customer(customerId, name, phone);
        customers.add(customer);
        System.out.println("✅ Đã tạo khách hàng mới: " + name);
        return customer;
    }
    
    // Order Management
    public static Order createOrder(String customerPhone) {
        if (currentUser == null) {
            System.out.println("❌ Vui lòng đăng nhập trước!");
            return null;
        }
        
        Customer customer = findCustomerByPhone(customerPhone);
        if (customer == null) {
            System.out.println("❌ Không tìm thấy khách hàng với SĐT: " + customerPhone);
            return null;
        }
        
        String orderId = "ORD" + String.format("%04d", orders.size() + 1);
        Order order = new Order(orderId, customer.getCustomerId(), currentUser.getUserId());
        order.setCustomerName(customer.getCustomerName());
        order.setEmployeeName(currentUser.getFullName());
        
        orders.add(order);
        System.out.println("✅ Đã tạo đơn hàng: " + orderId);
        return order;
    }
    
    public static boolean addProductToOrder(String orderId, String productId, int quantity) {
        Order order = findOrderById(orderId);
        Product product = ProductService.findProductById(productId);
        
        if (order == null || product == null) {
            System.out.println("❌ Không tìm thấy đơn hàng hoặc sản phẩm");
            return false;
        }
        
        if (!product.isInStock() || product.getStockQuantity() < quantity) {
            System.out.println("❌ Không đủ hàng trong kho");
            return false;
        }
        
        Order.OrderItem item = new Order.OrderItem(productId, product.getProductName(), 
                                                  product.getPrice(), quantity);
        order.addOrderItem(item);
        System.out.println("✅ Đã thêm vào đơn hàng: " + quantity + " " + product.getProductName());
        return true;
    }
    
    public static Order findOrderById(String orderId) {
        return orders.stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .findFirst()
                .orElse(null);
    }
    
    public static boolean completeOrder(String orderId) {
        Order order = findOrderById(orderId);
        if (order == null) {
            System.out.println("❌ Không tìm thấy đơn hàng");
            return false;
        }
        
        // Trừ kho cho tất cả sản phẩm trong đơn
        for (Order.OrderItem item : order.getOrderItems()) {
            Product product = ProductService.findProductById(item.getProductId());
            if (product != null) {
                product.reduceStock(item.getQuantity());
            }
        }
        
        // Áp dụng discount cho VIP
        Customer customer = findCustomerById(order.getCustomerId());
        if (customer != null && customer.isVipCustomer()) {
            BigDecimal discount = order.getTotalAmount().multiply(new BigDecimal("0.1"));
            order.setDiscountAmount(discount);
            System.out.println("🎁 Áp dụng giảm giá VIP 10%: " + discount.longValue());
        }
        
        order.complete();
        
        // Cập nhật thống kê khách hàng
        if (customer != null) {
            customer.addPurchase(order.getFinalAmount().doubleValue());
        }
        
        System.out.println("✅ Hoàn thành đơn hàng: " + orderId);
        System.out.println("💰 Tổng tiền: " + order.getFinalAmount().longValue());
        return true;
    }
    
    public static Customer findCustomerById(String customerId) {
        return customers.stream()
                .filter(c -> c.getCustomerId().equals(customerId))
                .findFirst()
                .orElse(null);
    }
    
    // Reports
    public static void displayDashboard() {
        System.out.println("\n📊 === DASHBOARD ===");
        System.out.println("👤 Đang đăng nhập: " + (currentUser != null ? currentUser.getFullName() : "Chưa đăng nhập"));
        System.out.println("🛍️ Tổng số đơn hàng: " + orders.size());
        System.out.println("👥 Tổng số khách hàng: " + customers.size());
        
        BigDecimal totalRevenue = orders.stream()
                .filter(Order::isCompleted)
                .map(Order::getFinalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("💰 Tổng doanh thu: " + totalRevenue.longValue());
        
        // Cảnh báo hết hàng
        ProductService.displayLowStockAlert();
    }
    
    // Demo quy trình bán hàng hoàn chỉnh
    public static void demoSalesProcess() {
        System.out.println("=== DEMO QUY TRÌNH BÁN HÀNG ===");
        
        // 1. Đăng nhập
        login("employee", "emp123");
        
        // 2. Hiển thị dashboard
        displayDashboard();
        
        // 3. Tạo đơn hàng cho khách hàng
        Order order = createOrder("0901234567");
        
        if (order != null) {
            // 4. Thêm sản phẩm vào đơn
            addProductToOrder(order.getOrderId(), "PRD001", 1); // iPhone
            addProductToOrder(order.getOrderId(), "PRD002", 1); // Samsung
            
            // 5. Hiển thị thông tin đơn hàng
            System.out.println("\n📋 Thông tin đơn hàng:");
            System.out.println(order.toString());
            System.out.println("Số sản phẩm: " + order.getTotalItems());
            System.out.println("Tổng tiền tạm: " + order.getTotalAmount().longValue());
            
            // 6. Hoàn thành đơn hàng
            completeOrder(order.getOrderId());
        }
        
        // 7. Hiển thị dashboard sau bán hàng
        displayDashboard();
        
        // 8. Đăng xuất
        logout();
    }
    
    public static void main(String[] args) {
        // Demo ProductService
        ProductService.demo();
        
        System.out.println("\n" + "=".repeat(50));
        
        // Demo Sales Management System
        demoSalesProcess();
    }
}
