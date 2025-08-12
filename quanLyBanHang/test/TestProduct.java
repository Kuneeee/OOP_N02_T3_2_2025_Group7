import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import model.Product;

public class TestProduct {

    @Test
    void isLowStock_whenStockEqualMin_shouldBeTrue() {
        Product p = new Product();
        p.setStockQuantity(5);
        p.setMinStockLevel(5);
        assertTrue(p.isLowStock(), "Stock == min should be considered low stock");
    }

    @Test
    void isLowStock_whenStockGreaterThanMin_shouldBeFalse() {
        Product p = new Product();
        p.setStockQuantity(6);
        p.setMinStockLevel(5);
        assertFalse(p.isLowStock(), "Stock > min should not be low stock");
    }

    @Test
    void profitMargin_validCostAndPrice_shouldComputeCorrectly() {
        Product p = new Product();
        p.setCostPrice(new BigDecimal("8000"));
        p.setPrice(new BigDecimal("12000"));
        double margin = p.getProfitMargin();
        assertEquals(0.5, margin, 1e-6, "(12000-8000)/8000 = 0.5");
    }

    @Test
    void profitMargin_missingOrZeroCost_shouldReturnZero() {
        Product p1 = new Product();
        p1.setPrice(new BigDecimal("10000"));
        assertEquals(0.0, p1.getProfitMargin(), 1e-6, "Missing cost -> 0.0");

        Product p2 = new Product();
        p2.setCostPrice(BigDecimal.ZERO);
        p2.setPrice(new BigDecimal("10000"));
        assertEquals(0.0, p2.getProfitMargin(), 1e-6, "Zero cost -> 0.0");
    }
}
