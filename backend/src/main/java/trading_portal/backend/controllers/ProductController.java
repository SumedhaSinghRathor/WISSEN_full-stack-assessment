package trading_portal.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import trading_portal.backend.entity.Product;
import trading_portal.backend.services.impl.ProductServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
    private ProductServiceImpl service;

    @GetMapping("")
    public List<Product> getAllProducts() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping("")
    public Product createProduct(@RequestBody Product product) {
        return service.create(product); // 🔥 IMPORTANT
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable int id, @RequestBody Product product) {
        service.update(id, product);
        return "Product updated successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id) {
        service.delete(id);
        return "Product deleted successfully";
    }
}