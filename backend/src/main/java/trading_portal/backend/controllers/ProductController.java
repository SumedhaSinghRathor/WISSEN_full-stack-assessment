package trading_portal.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import trading_portal.backend.entity.Product;
import trading_portal.backend.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {

       @Autowired
       private ProductRepository productRepository;
       // GET /products -> Get all products
       @GetMapping("")
       public List<Product> getAllProducts() {
              return productRepository.findAll();
       }

       @GetMapping("/{id}")
       public Product getProductById(@PathVariable Integer id) {
              return productRepository.findById(id).orElse(null);
       }

       @PostMapping("/")
       public Product createProduct(@RequestBody Product product) {
              return productRepository.save(product);
              
       }

       @PutMapping("/{id}")
       public String updateProduct(@PathVariable Integer id, @RequestBody Product productDetails) {
              Product product = productRepository.findById(id).orElse(null);
              if (product != null) {
                     product.setType(productDetails.getType());  
                     product.setTicker(productDetails.getTicker());
                     product.setName(productDetails.getName());
                     product.setCurrent_price(productDetails.getCurrent_price());
                     productRepository.save(product);
                     return "Product updated successfully";
              }
              return "Product not found with id: " + id;
       }

       @DeleteMapping("/{id}")
       public String deleteProduct(@PathVariable Integer id) {
              productRepository.deleteById(id);
              return "Product deleted successfully";
       }
}
