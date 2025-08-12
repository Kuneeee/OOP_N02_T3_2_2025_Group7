package repository;

import model.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbc;

    public OrderRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Order> ORDER_MAPPER = new RowMapper<Order>() {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order o = new Order();
            o.setOrderId(rs.getString("order_id"));
            o.setCustomerId(rs.getString("customer_id"));
            o.setCustomerName(rs.getString("customer_name"));
            o.setEmployeeId(rs.getString("employee_id"));
            o.setEmployeeName(rs.getString("employee_name"));
            Timestamp ts = rs.getTimestamp("order_date");
            o.setOrderDate(ts != null ? ts.toLocalDateTime() : LocalDateTime.now());
            o.setStatus(rs.getString("status"));
            o.setPaymentMethod(rs.getString("payment_method"));
            o.setPaymentStatus(rs.getString("payment_status"));
            BigDecimal discount = rs.getBigDecimal("discount_amount");
            o.setDiscountAmount(discount == null ? BigDecimal.ZERO : discount);
            o.setNotes(rs.getString("notes"));
            o.setItems(new ArrayList<>());
            return o;
        }
    };

    private static final RowMapper<Order.OrderItem> ITEM_MAPPER = new RowMapper<Order.OrderItem>() {
        @Override
        public Order.OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order.OrderItem item = new Order.OrderItem();
            item.setProductId(rs.getString("product_id"));
            item.setProductName(rs.getString("product_name"));
            item.setCategoryId(rs.getString("category_id"));
            item.setUnitPrice(rs.getBigDecimal("unit_price"));
            item.setQuantity(rs.getInt("quantity"));
            return item;
        }
    };

    public List<Order> findAll() {
        String sql = "SELECT * FROM orders ORDER BY order_date DESC";
        List<Order> orders = jdbc.query(sql, ORDER_MAPPER);
        for (Order o : orders) {
            o.setItems(findItemsByOrderId(o.getOrderId()));
        }
        return orders;
    }

    public Order findById(String id) {
        List<Order> list = jdbc.query("SELECT * FROM orders WHERE order_id = ?", ORDER_MAPPER, id);
        if (list.isEmpty()) return null;
        Order o = list.get(0);
        o.setItems(findItemsByOrderId(id));
        return o;
    }

    public List<Order.OrderItem> findItemsByOrderId(String orderId) {
        return jdbc.query("SELECT * FROM order_items WHERE order_id = ?", ITEM_MAPPER, orderId);
    }
}
