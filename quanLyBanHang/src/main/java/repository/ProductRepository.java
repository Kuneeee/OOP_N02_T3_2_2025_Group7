package repository;

import model.Product;
import model.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbc;

    public ProductRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Product> PRODUCT_MAPPER = new RowMapper<Product>() {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product p = new Product();
            p.setProductId(rs.getString("product_id"));
            p.setProductName(rs.getString("product_name"));
            p.setCategoryId(rs.getString("category_id"));
            p.setCategoryName(rs.getString("category_name"));
            BigDecimal price = rs.getBigDecimal("price");
            BigDecimal cost = rs.getBigDecimal("cost_price");
            p.setPrice(price);
            p.setCostPrice(cost);
            p.setStockQuantity(Optional.ofNullable((Integer) rs.getObject("stock_quantity")).orElse(0));
            p.setMinStockLevel(Optional.ofNullable((Integer) rs.getObject("min_stock_level")).orElse(0));
            p.setUnit(rs.getString("unit"));
            p.setBrand(rs.getString("brand"));
            p.setDescription(rs.getString("description"));
            p.setImageUrl(rs.getString("image_url"));
            p.setSupplierId(rs.getString("supplier_id"));
            p.setStatus(rs.getString("status"));
            return p;
        }
    };

    private static final RowMapper<Category> CATEGORY_MAPPER = new RowMapper<Category>() {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category c = new Category();
            c.setCategoryId(rs.getString("category_id"));
            c.setCategoryName(rs.getString("category_name"));
            c.setDescription(rs.getString("description"));
            c.setParentCategoryId(rs.getString("parent_category_id"));
            c.setDisplayOrder(Optional.ofNullable((Integer) rs.getObject("display_order")).orElse(0));
            return c;
        }
    };

    public List<Product> findAll() {
        String sql = "SELECT * FROM products";
        return jdbc.query(sql, PRODUCT_MAPPER);
    }

    public Product findById(String id) {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        List<Product> list = jdbc.query(sql, PRODUCT_MAPPER, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Product> findByCategoryExcluding(String categoryId, String excludeProductId, int limit) {
        String sql = "SELECT * FROM products WHERE category_id = ? AND product_id <> ? LIMIT ?";
        return jdbc.query(sql, PRODUCT_MAPPER, categoryId, excludeProductId, limit);
    }

    public List<Category> findAllCategories() {
        String sql = "SELECT * FROM categories ORDER BY display_order, category_name";
        return jdbc.query(sql, CATEGORY_MAPPER);
    }

    public String nextProductId() {
        Integer max = jdbc.queryForObject(
                "SELECT COALESCE(MAX(CAST(SUBSTRING(product_id, 4) AS UNSIGNED)), 0) FROM products WHERE product_id LIKE 'PRD%'",
                Integer.class);
        int next = (max == null ? 0 : max) + 1;
        return "PRD" + String.format("%03d", next);
    }

    public void insert(Product p) {
        String sql = "INSERT INTO products (product_id, product_name, category_id, category_name, price, cost_price, stock_quantity, min_stock_level, unit, brand, description, image_url, supplier_id, status) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbc.update(sql,
                p.getProductId(), p.getProductName(), p.getCategoryId(), p.getCategoryName(),
                p.getPrice(), p.getCostPrice(), p.getStockQuantity(), p.getMinStockLevel(), p.getUnit(),
                p.getBrand(), p.getDescription(), p.getImageUrl(), p.getSupplierId(), p.getStatus());
    }

    public void update(Product p) {
        String sql = "UPDATE products SET product_name=?, category_id=?, category_name=?, price=?, cost_price=?, stock_quantity=?, min_stock_level=?, unit=?, brand=?, description=?, image_url=?, supplier_id=?, status=? WHERE product_id=?";
        jdbc.update(sql,
                p.getProductName(), p.getCategoryId(), p.getCategoryName(), p.getPrice(), p.getCostPrice(),
                p.getStockQuantity(), p.getMinStockLevel(), p.getUnit(), p.getBrand(), p.getDescription(),
                p.getImageUrl(), p.getSupplierId(), p.getStatus(), p.getProductId());
    }

    public void deleteById(String id) {
        jdbc.update("DELETE FROM products WHERE product_id = ?", id);
    }
}
