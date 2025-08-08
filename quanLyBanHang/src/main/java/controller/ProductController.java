package controller;

import model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    
    // Hiển thị form thêm sản phẩm mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", SampleDataProvider.getSampleCategories());
        model.addAttribute("title", "Thêm Sản Phẩm Mới");
        model.addAttribute("action", "add");
        return "products/form";
    }
    
    // Xử lý thêm sản phẩm mới
    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product, Model model) {
        System.out.println("=== ProductController /add POST được gọi ===");
        System.out.println("Product name: " + product.getProductName());
        
        try {
            // Generate new ID
            List<Product> products = SampleDataProvider.getSampleProducts();
            int maxId = products.stream()
                    .mapToInt(p -> {
                        try {
                            return Integer.parseInt(p.getProductId().substring(3)); // PRD001 -> 001 -> 1
                        } catch (Exception e) {
                            return 0;
                        }
                    })
                    .max()
                    .orElse(0);
            product.setProductId("PRD" + String.format("%03d", maxId + 1));
            
            System.out.println("Generated ID: " + product.getProductId());
            
            // Set category name based on category ID
            List<Category> categories = SampleDataProvider.getSampleCategories();
            categories.stream()
                    .filter(c -> c.getCategoryId().equals(product.getCategoryId()))
                    .findFirst()
                    .ifPresent(c -> product.setCategoryName(c.getCategoryName()));
            
            // Set default values if not provided
            if (product.getMinStockLevel() == 0) {
                product.setMinStockLevel(10);
            }
            if (product.getStatus() == null || product.getStatus().isEmpty()) {
                product.setStatus("Active");
            }
            
            // Add to sample data (Note: This is temporary, in real app would save to database)
            products.add(product);
            
            System.out.println("Product added successfully. Total products: " + products.size());
            System.out.println("Returning to products list view...");
            
            // Instead of redirect, return to products list directly with success message
            List<Product> filteredProducts = products;
            long lowStockCount = products.stream().filter(p -> p.getStockQuantity() <= p.getMinStockLevel()).count();
            
            model.addAttribute("products", filteredProducts);
            model.addAttribute("categories", categories);
            model.addAttribute("lowStockCount", lowStockCount);
            model.addAttribute("totalProducts", filteredProducts.size());
            model.addAttribute("title", "Quản Lý Sản Phẩm");
            model.addAttribute("successMessage", "Thêm sản phẩm thành công!");
            
            return "products/list";
        } catch (Exception e) {
            System.out.println("Error adding product: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            model.addAttribute("product", product);
            model.addAttribute("categories", SampleDataProvider.getSampleCategories());
            model.addAttribute("title", "Thêm Sản Phẩm Mới");
            model.addAttribute("action", "add");
            return "products/form";
        }
    }
    
    // Hiển thị form chỉnh sửa sản phẩm
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        List<Product> products = SampleDataProvider.getSampleProducts();
        Product product = products.stream()
                .filter(p -> p.getProductId().equals(id))
                .findFirst()
                .orElse(null);
        
        if (product == null) {
            model.addAttribute("error", "Không tìm thấy sản phẩm!");
            return "redirect:/products";
        }
        
        model.addAttribute("product", product);
        model.addAttribute("categories", SampleDataProvider.getSampleCategories());
        model.addAttribute("title", "Chỉnh Sửa Sản Phẩm - " + product.getProductName());
        model.addAttribute("action", "edit");
        return "products/form";
    }
    
    // Xử lý cập nhật sản phẩm
    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable String id, @ModelAttribute Product product, Model model) {
        System.out.println("=== ProductController /edit POST được gọi ===");
        System.out.println("Product ID: " + id + ", Product name: " + product.getProductName());
        
        try {
            List<Product> products = SampleDataProvider.getSampleProducts();
            Product existingProduct = products.stream()
                    .filter(p -> p.getProductId().equals(id))
                    .findFirst()
                    .orElse(null);
            
            if (existingProduct == null) {
                model.addAttribute("error", "Không tìm thấy sản phẩm!");
                return "redirect:/products";
            }
            
            // Update product fields
            existingProduct.setProductName(product.getProductName());
            existingProduct.setBrand(product.getBrand());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setStockQuantity(product.getStockQuantity());
            existingProduct.setMinStockLevel(product.getMinStockLevel());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setStatus(product.getStatus());
            existingProduct.setCategoryId(product.getCategoryId());
            
            // Set category name based on category ID
            List<Category> categories = SampleDataProvider.getSampleCategories();
            categories.stream()
                    .filter(c -> c.getCategoryId().equals(product.getCategoryId()))
                    .findFirst()
                    .ifPresent(c -> existingProduct.setCategoryName(c.getCategoryName()));
            
            System.out.println("Product updated successfully. Total products: " + products.size());
            System.out.println("Returning to products list view...");
            
            // Return to products list directly with success message
            List<Product> filteredProducts = products;
            long lowStockCount = products.stream().filter(p -> p.getStockQuantity() <= p.getMinStockLevel()).count();
            
            model.addAttribute("products", filteredProducts);
            model.addAttribute("categories", categories);
            model.addAttribute("lowStockCount", lowStockCount);
            model.addAttribute("totalProducts", filteredProducts.size());
            model.addAttribute("title", "Quản Lý Sản Phẩm");
            model.addAttribute("successMessage", "Cập nhật sản phẩm thành công!");
            
            return "products/list";
        } catch (Exception e) {
            System.out.println("Error updating product: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            model.addAttribute("product", product);
            model.addAttribute("categories", SampleDataProvider.getSampleCategories());
            model.addAttribute("title", "Chỉnh Sửa Sản Phẩm");
            model.addAttribute("action", "edit");
            return "products/form";
        }
    }
    
    // Xóa sản phẩm
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id, Model model) {
        System.out.println("=== ProductController /delete POST được gọi ===");
        System.out.println("Product ID to delete: " + id);
        
        try {
            List<Product> products = SampleDataProvider.getSampleProducts();
            Product productToDelete = products.stream()
                    .filter(p -> p.getProductId().equals(id))
                    .findFirst()
                    .orElse(null);
            
            if (productToDelete == null) {
                model.addAttribute("error", "Không tìm thấy sản phẩm để xóa!");
            } else {
                products.remove(productToDelete);
                System.out.println("Product deleted successfully. Total products: " + products.size());
                model.addAttribute("successMessage", "Xóa sản phẩm thành công!");
            }
            
            // Return to products list directly
            List<Category> categories = SampleDataProvider.getSampleCategories();
            long lowStockCount = products.stream().filter(p -> p.getStockQuantity() <= p.getMinStockLevel()).count();
            
            model.addAttribute("products", products);
            model.addAttribute("categories", categories);
            model.addAttribute("lowStockCount", lowStockCount);
            model.addAttribute("totalProducts", products.size());
            model.addAttribute("title", "Quản Lý Sản Phẩm");
            
            return "products/list";
        } catch (Exception e) {
            System.out.println("Error deleting product: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/products";
        }
    }
}
