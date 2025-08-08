package controller;

import model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {
    
    @GetMapping
    public String products(Model model, 
                          @RequestParam(required = false) String search,
                          @RequestParam(required = false) String category) {
        
        System.out.println("=== ProductController /products được gọi ===");
        System.out.println("Search: " + search + ", Category: " + category);
        
        List<Product> products = SampleDataProvider.getSampleProducts();
        System.out.println("Số sản phẩm từ SampleDataProvider: " + products.size());
        
        List<Category> categories = SampleDataProvider.getSampleCategories();
        
        // Filter by search keyword
        if (search != null && !search.trim().isEmpty()) {
            products = products.stream()
                    .filter(p -> p.getProductName().toLowerCase().contains(search.toLowerCase()) ||
                               (p.getBrand() != null && p.getBrand().toLowerCase().contains(search.toLowerCase())) ||
                               (p.getDescription() != null && p.getDescription().toLowerCase().contains(search.toLowerCase())))
                    .collect(Collectors.toList());
        }
        
        // Filter by category
        if (category != null && !category.trim().isEmpty()) {
            products = products.stream()
                    .filter(p -> category.equals(p.getCategoryName()) || category.equals(p.getCategoryId()))
                    .collect(Collectors.toList());
        }
        
        System.out.println("Số sản phẩm sau filter: " + products.size());
        if (!products.isEmpty()) {
            System.out.println("Sản phẩm đầu tiên: " + products.get(0).getProductName());
        }
        
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("currentSearch", search);
        model.addAttribute("currentCategory", category);
        model.addAttribute("title", "Quản Lý Sản Phẩm");
        
        return "products/list";
    }
    
    @GetMapping("/{id}")
    public String productDetail(@PathVariable String id, Model model) {
        List<Product> products = SampleDataProvider.getSampleProducts();
        Product product = products.stream()
                .filter(p -> p.getProductId().equals(id))
                .findFirst()
                .orElse(null);
        
        if (product == null) {
            return "redirect:/products";
        }
        
        // Get related products (same category)
        List<Product> relatedProducts = products.stream()
                .filter(p -> !p.getProductId().equals(id) && 
                           p.getCategoryId().equals(product.getCategoryId()))
                .limit(3)
                .collect(Collectors.toList());
        
        model.addAttribute("product", product);
        model.addAttribute("relatedProducts", relatedProducts);
        model.addAttribute("title", "Chi Tiết Sản Phẩm - " + product.getProductName());
        
        return "products/detail";
    }
    
    @GetMapping("/low-stock")
    public String lowStockProducts(Model model) {
        List<Product> products = SampleDataProvider.getSampleProducts();
        List<Product> lowStockProducts = products.stream()
                .filter(Product::isLowStock)
                .collect(Collectors.toList());
        
        model.addAttribute("products", lowStockProducts);
        model.addAttribute("categories", SampleDataProvider.getSampleCategories());
        model.addAttribute("title", "Sản Phẩm Sắp Hết Hàng");
        model.addAttribute("isLowStockView", true);
        
        return "products/list";
    }
}
