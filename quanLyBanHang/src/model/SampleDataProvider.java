package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Cung cấp dữ liệu mẫu cho tất cả các model
 * Sử dụng để hiển thị trên web interface
 */
public class SampleDataProvider {
    
    // Sample Products - Dữ liệu mẫu đồ ăn vặt
    public static List<Product> getSampleProducts() {
        List<Product> products = new ArrayList<>();
        
        // Bánh tráng nướng
        Product banhTrang = new Product("PRD001", "Bánh tráng nướng", "CAT_BANH", new BigDecimal("15000"), 50);
        banhTrang.setCategoryName("Bánh kẹo");
        banhTrang.setBrand("Homemade");
        banhTrang.setCostPrice(new BigDecimal("8000"));
        banhTrang.setMinStockLevel(10);
        banhTrang.setUnit("cái");
        banhTrang.setDescription("Bánh tráng nướng giòn rụm với đầy đủ topping");
        
        // Trà sữa trân châu
        Product traSua = new Product("PRD002", "Trà sữa trân châu", "CAT_NUOC", new BigDecimal("25000"), 30);
        traSua.setCategoryName("Nước uống");
        traSua.setBrand("Tea House");
        traSua.setCostPrice(new BigDecimal("12000"));
        traSua.setMinStockLevel(5);
        traSua.setUnit("ly");
        traSua.setDescription("Trà sữa thơm ngon với trân châu dai dai");
        
        // Bánh mì nướng muối ớt
        Product banhMi = new Product("PRD003", "Bánh mì nướng muối ớt", "CAT_BANH", new BigDecimal("20000"), 25);
        banhMi.setCategoryName("Bánh kẹo");
        banhMi.setBrand("Saigon Bakery");
        banhMi.setCostPrice(new BigDecimal("10000"));
        banhMi.setMinStockLevel(5);
        banhMi.setUnit("cái");
        banhMi.setDescription("Bánh mì nướng giòn với muối ớt đặc biệt");
        
        // Chè thái
        Product cheThai = new Product("PRD004", "Chè thái", "CAT_CHE", new BigDecimal("18000"), 20);
        cheThai.setCategoryName("Chè");
        cheThai.setBrand("Chè Bà Ba");
        cheThai.setCostPrice(new BigDecimal("9000"));
        cheThai.setMinStockLevel(3);
        cheThai.setUnit("ly");
        cheThai.setDescription("Chè thái mát lạnh với nhiều topping");
        
        products.addAll(Arrays.asList(banhTrang, traSua, banhMi, cheThai));
        return products;
    }
    
    // Sample Customers - Dữ liệu khách hàng quán đồ ăn vặt
    public static List<Customer> getSampleCustomers() {
        List<Customer> customers = new ArrayList<>();
        
        // Khách VIP
        Customer vip = new Customer("CUS001", "Chị Lan Anh", "0901234567");
        vip.setEmail("lananh@gmail.com");
        vip.setAddress("123 Nguyễn Huệ, Quận 1, TP.HCM");
        vip.setCustomerType("VIP");
        vip.setTotalPurchased(500000.0);
        vip.setTotalOrders(25);
        
        // Khách thường
        Customer regular = new Customer("CUS002", "Anh Minh", "0912345678");
        regular.setEmail("anhminh@gmail.com");
        regular.setAddress("456 Lê Lợi, Quận 3, TP.HCM");
        regular.setCustomerType("Regular");
        regular.setTotalPurchased(150000.0);
        regular.setTotalOrders(8);
        
        // Khách mới
        Customer newCustomer = new Customer("CUS003", "Em Hương", "0923456789");
        newCustomer.setEmail("huong123@gmail.com");
        newCustomer.setAddress("789 Hai Bà Trưng, Quận 1, TP.HCM");
        newCustomer.setCustomerType("New");
        newCustomer.setTotalPurchased(0.0);
        newCustomer.setTotalOrders(0);
        
        customers.addAll(Arrays.asList(vip, regular, newCustomer));
        return customers;
    }
    
    // Sample Users - Dữ liệu nhân viên quán ăn vặt
    public static List<User> getSampleUsers() {
        List<User> users = new ArrayList<>();
        
        // Chủ quán
        User admin = new User("USR001", "admin", "admin123", "Chủ Quán", "chuquan@doanvat.vn", "ADMIN");
        admin.setPhoneNumber("0901111111");
        
        // Quản lý ca
        User manager = new User("USR002", "manager", "manager123", "Quản Lý Ca", "quanly@doanvat.vn", "MANAGER");
        manager.setPhoneNumber("0902222222");
        
        // Nhân viên
        User employee = new User("USR003", "employee", "emp123", "Nhân Viên Bán Hàng", "nhanvien@doanvat.vn", "EMPLOYEE");
        employee.setPhoneNumber("0903333333");
        
        users.addAll(Arrays.asList(admin, manager, employee));
        return users;
    }
    
    // Sample Categories - Danh mục đồ ăn vặt
    public static List<Category> getSampleCategories() {
        List<Category> categories = new ArrayList<>();
        
        Category banh = new Category("CAT_BANH", "Bánh kẹo", "Các loại bánh và kẹo");
        banh.setProductCount(2);
        
        Category nuoc = new Category("CAT_NUOC", "Nước uống", "Các loại nước uống");
        nuoc.setProductCount(1);
        
        Category che = new Category("CAT_CHE", "Chè", "Các loại chè và tráng miệng");
        che.setProductCount(1);
        
        categories.addAll(Arrays.asList(banh, nuoc, che));
        return categories;
    }
    
    // Sample Suppliers - Nhà cung cấp đồ ăn vặt
    public static List<Supplier> getSampleSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        
        Supplier saiGonFood = new Supplier("SUP001", "Sài Gòn Food", "Anh Tuấn", "0281234567");
        saiGonFood.setEmail("tuannguyen@saigonfood.vn");
        saiGonFood.setAddress("123 Chợ Lớn, Quận 5, TP.HCM");
        saiGonFood.setSupplierType("LOCAL");
        
        Supplier mienTayFood = new Supplier("SUP002", "Miền Tây Food", "Chị Mai", "0287654321");
        mienTayFood.setEmail("mai@mientayfood.vn");
        mienTayFood.setAddress("456 An Dương Vương, Quận 5, TP.HCM");
        mienTayFood.setSupplierType("LOCAL");
        
        suppliers.addAll(Arrays.asList(saiGonFood, mienTayFood));
        return suppliers;
    }
    
    // Sample Orders - Đơn hàng đồ ăn vặt
    public static List<Order> getSampleOrders() {
        List<Order> orders = new ArrayList<>();
        
        // Đơn hàng hoàn thành
        Order order1 = new Order("ORD001", "CUS001", "USR003");
        order1.setCustomerName("Chị Lan Anh");
        order1.setEmployeeName("Nhân Viên");
        order1.setOrderDate(LocalDateTime.now().minusDays(2));
        order1.setStatus("COMPLETED");
        order1.setPaymentMethod("CASH");
        order1.setPaymentStatus("PAID");
        
        Order.OrderItem item1 = new Order.OrderItem("PRD001", "Bánh tráng nướng", new BigDecimal("15000"), 2);
        Order.OrderItem item2 = new Order.OrderItem("PRD002", "Trà sữa trân châu", new BigDecimal("25000"), 1);
        order1.addOrderItem(item1);
        order1.addOrderItem(item2);
        order1.setNotes("Khách VIP - giao tận bàn");
        
        // Đơn hàng đang xử lý
        Order order2 = new Order("ORD002", "CUS002", "USR003");
        order2.setCustomerName("Anh Minh");
        order2.setEmployeeName("Nhân Viên");
        order2.setOrderDate(LocalDateTime.now().minusHours(2));
        order2.setStatus("PROCESSING");
        order2.setPaymentMethod("CARD");
        order2.setPaymentStatus("PAID");
        
        Order.OrderItem item3 = new Order.OrderItem("PRD004", "Chè thái", new BigDecimal("18000"), 1);
        order2.addOrderItem(item3);
        order2.setNotes("Đang chuẩn bị");
        
        orders.addAll(Arrays.asList(order1, order2));
        return orders;
    }
    
    // Method để lấy tổng quan thống kê
    public static SummaryStats getSummaryStats() {
        List<Product> products = getSampleProducts();
        List<Customer> customers = getSampleCustomers();
        List<Order> orders = getSampleOrders();
        
        // Tính toán các thống kê
        int totalProducts = products.size();
        int lowStockProducts = (int) products.stream().filter(Product::isLowStock).count();
        int totalCustomers = customers.size();
        int vipCustomers = (int) customers.stream().filter(Customer::isVipCustomer).count();
        int totalOrders = orders.size();
        int completedOrders = (int) orders.stream().filter(Order::isCompleted).count();
        
        BigDecimal totalRevenue = orders.stream()
                .filter(Order::isCompleted)
                .map(Order::getFinalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalInventoryValue = products.stream()
                .map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getStockQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return new SummaryStats(totalProducts, lowStockProducts, totalCustomers, vipCustomers, 
                              totalOrders, completedOrders, totalRevenue, totalInventoryValue);
    }
    
    // Inner class cho thống kê tổng quan
    public static class SummaryStats {
        private int totalProducts;
        private int lowStockProducts;
        private int totalCustomers;
        private int vipCustomers;
        private int totalOrders;
        private int completedOrders;
        private BigDecimal totalRevenue;
        private BigDecimal totalInventoryValue;
        
        public SummaryStats(int totalProducts, int lowStockProducts, int totalCustomers, int vipCustomers,
                           int totalOrders, int completedOrders, BigDecimal totalRevenue, BigDecimal totalInventoryValue) {
            this.totalProducts = totalProducts;
            this.lowStockProducts = lowStockProducts;
            this.totalCustomers = totalCustomers;
            this.vipCustomers = vipCustomers;
            this.totalOrders = totalOrders;
            this.completedOrders = completedOrders;
            this.totalRevenue = totalRevenue;
            this.totalInventoryValue = totalInventoryValue;
        }
        
        // Getters
        public int getTotalProducts() { return totalProducts; }
        public int getLowStockProducts() { return lowStockProducts; }
        public int getTotalCustomers() { return totalCustomers; }
        public int getVipCustomers() { return vipCustomers; }
        public int getTotalOrders() { return totalOrders; }
        public int getCompletedOrders() { return completedOrders; }
        public BigDecimal getTotalRevenue() { return totalRevenue; }
        public BigDecimal getTotalInventoryValue() { return totalInventoryValue; }
    }
}
