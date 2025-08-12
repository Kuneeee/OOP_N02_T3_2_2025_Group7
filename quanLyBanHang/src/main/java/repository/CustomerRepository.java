package repository;

import model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class CustomerRepository {

    private final JdbcTemplate jdbc;

    public CustomerRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Customer> CUSTOMER_MAPPER = new RowMapper<Customer>() {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer c = new Customer();
            c.setCustomerId(rs.getString("customer_id"));
            c.setCustomerName(rs.getString("customer_name"));
            c.setPhoneNumber(rs.getString("phone_number"));
            c.setEmail(rs.getString("email"));
            c.setAddress(rs.getString("address"));
            c.setCustomerType(rs.getString("customer_type"));
            c.setTotalPurchased(rs.getBigDecimal("total_purchased") == null ? 0.0 : rs.getBigDecimal("total_purchased").doubleValue());
            c.setTotalOrders(rs.getInt("total_orders"));
            c.setTotalSpent(rs.getBigDecimal("total_spent") == null ? 0.0 : rs.getBigDecimal("total_spent").doubleValue());
            c.setLoyaltyPoints(rs.getInt("loyalty_points"));
            Date join = rs.getDate("join_date");
            c.setJoinDate(join != null ? join.toLocalDate() : LocalDate.now());
            return c;
        }
    };

    public List<Customer> findAll() {
        return jdbc.query("SELECT * FROM customers ORDER BY customer_name", CUSTOMER_MAPPER);
    }

    public Customer findById(String id) {
        List<Customer> list = jdbc.query("SELECT * FROM customers WHERE customer_id = ?", CUSTOMER_MAPPER, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public String nextCustomerId() {
        Integer max = jdbc.queryForObject("SELECT COALESCE(MAX(CAST(SUBSTRING(customer_id, 4) AS UNSIGNED)), 0) FROM customers WHERE customer_id LIKE 'CUS%'", Integer.class);
        int next = (max == null ? 0 : max) + 1;
        return "CUS" + String.format("%03d", next);
    }

    public void insert(Customer c) {
        jdbc.update("INSERT INTO customers (customer_id, customer_name, phone_number, email, address, customer_type, total_purchased, total_orders, total_spent, loyalty_points, join_date) VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                c.getCustomerId(), c.getCustomerName(), c.getPhoneNumber(), c.getEmail(), c.getAddress(), c.getCustomerType(),
                c.getTotalPurchased(), c.getTotalOrders(), c.getTotalSpent(), c.getLoyaltyPoints(), c.getJoinDate());
    }

    public void update(Customer c) {
        jdbc.update("UPDATE customers SET customer_name=?, phone_number=?, email=?, address=?, customer_type=?, total_purchased=?, total_orders=?, total_spent=?, loyalty_points=?, join_date=? WHERE customer_id=?",
                c.getCustomerName(), c.getPhoneNumber(), c.getEmail(), c.getAddress(), c.getCustomerType(), c.getTotalPurchased(),
                c.getTotalOrders(), c.getTotalSpent(), c.getLoyaltyPoints(), c.getJoinDate(), c.getCustomerId());
    }

    public void deleteById(String id) {
        jdbc.update("DELETE FROM customers WHERE customer_id = ?", id);
    }
}
